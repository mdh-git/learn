package com.mdh.interview.questions;



import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.zip.Adler32;

/**
 * 20秒内完成两个库各100万条数据比对操作
 *
 * 问题: 需要将两个数据库（A和B）中的各100万条数据进行查询、比对和结果输出，并要求在20秒内完成
 * 难点: 数据量大(200万数据)、时间限制严格(20秒)、跨库操作、网络延迟、I/O瓶颈等问题
 *
 * 考虑点:
 * 1.内存控制: 通过分块处理避免大数据量内存溢出
 *          分批处理:分批数据读取，避免OOM
 *          多字段组合hash值:不需要存储所有字段，可以将字段组合成hash值存储，节省内存
 *
 * 2.并行处理: 兼备并行编程能力来提高处理速度
 *          并行数据加载:开启2个线程读取不同的数据库
 *          并行流比对:通过parallelStream并行流比对提高处理性能
 * 3.分层处理: 将存在性校验、哈希比对、内容比对分为不同层次
 */
public class DataComparison {


    // 根据内存调整，静态常数，定义批量大小为20000
    private static final int BATCH_SIZE = 20000;

    // 静态常数，定义平行度为cpu的核心*2
    private static final int PARALLELISM = Runtime.getRuntime().availableProcessors() * 2;

    // 静态常数，定义采样大小为1024字节
    private static final int SAMPLE_SIZE = 1024;


    // 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://10.231.139.74:9030/ssb";
    private static final String USER = "root";
    private static final String PASS = "";


    // 高效的哈希计算器
    private static class FastHash{
        private final ThreadLocal<Adler32> adler32 = ThreadLocal.withInitial(Adler32::new);

        // 用于计算数组hash值
        public long computeKeyHash(Object... fields){
            // 获取当前线程的adler32
            Adler32 hash = adler32.get();
            // 重置hash值
            hash.reset();
            for (Object field : fields) {
                if(field instanceof byte[]){
                    byte[] data = (byte[]) field;
                    hash.update(data, 0, Math.min(data.length, SAMPLE_SIZE));
                } else {
                    hash.update(field.toString().getBytes());
                }
            }
            // 返回计算得到的hash值
            return hash.getValue();
        }

    }

    // 分块数据加载器
    private static class BathLoader implements Callable<Map<String, Long>> {

        private final String dbUrl;
        private final String tableName;
        private final FastHash fastHash;

        public BathLoader(String dbUrl, String tableName, FastHash fastHash) {
            this.dbUrl = dbUrl;
            this.tableName = tableName;
            this.fastHash = fastHash;
        }


        @Override
        public Map<String, Long> call() throws Exception {
            Map<String, Long> hashes = new ConcurrentHashMap<>(BATCH_SIZE * 2);
            ExecutorService executor = Executors.newWorkStealingPool(PARALLELISM);

            try(Connection conn = DriverManager.getConnection(dbUrl, USER, PASS)){
                // int total = getRecordCount(conn, tableName);
                int total = 0;
                int batches = (int) Math.ceil((double)total/BATCH_SIZE);

                List<Future<?>> futures = new ArrayList<>(batches);
                for (int i = 0; i < batches; i++) {
                    final int offset = i * BATCH_SIZE;
                    futures.add(executor.submit(() -> processBath(conn, offset, hashes)));
                }

                for (Future<?> future : futures) {
                    future.get();
                }
            }
            executor.shutdown();
            return hashes;
        }

        private void processBath(Connection conn, int offset, Map<String, Long> hashes) {
            String sql =String.format("select id,product_name,product_price FROM %S LIMIT ? OFFSET ? ", tableName);
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, BATCH_SIZE);
                stmt.setInt(2, offset);
                ResultSet res = stmt.executeQuery();

                while(res.next()){
                    String id = res.getString("id");
                    String name = res.getString("product_name");
                    BigDecimal price = res.getBigDecimal("product_price");

                    // 计算组合hash（关键字段+内容抽样）
                    long hash = fastHash.computeKeyHash(id, name, price);
                    hashes.put(id, hash);
                }


            } catch (SQLException e){
                throw new RuntimeException("Batch processing failed", e);
            }
        }
    }

    // 差异分析器
    private static class DiffAnalyser {
        public static DiffResult analyse(Map<String, Long> db1, Map<String, Long> db2) {
            DiffResult result = new DiffResult();
            Set<String> allKeys = new ConcurrentSkipListSet<>();
            allKeys.addAll(db1.keySet());
            allKeys.addAll(db2.keySet());

            allKeys.parallelStream().forEach(key -> {
                Long h1 = db1.get(key);
                Long h2 = db2.get(key);

                if(h1 == null){
                    result.addOnlyInDB2(key);
                } else if(h2 == null){
                    result.addOnlyInDB1(key);
                } else if(!h1.equals(h2)){
                    result.addDifferent(key, h1, h2);
                }
            });

            return result;
        }
    }

    // 比较结果容器

    private static class DiffResult {
        private final Set<String> onlyInDB1 = ConcurrentHashMap.newKeySet();
        private final Set<String> onlyInDB2 = ConcurrentHashMap.newKeySet();
        private final Map<String, String> differences = new ConcurrentHashMap<>();


        public void addOnlyInDB2(String key) {
            onlyInDB2.add(key);
        }

        public void addOnlyInDB1(String key) {
            onlyInDB1.add(key);
        }

        public void addDifferent(String key, Long h1, Long h2) {
            differences.put(key, h1.toString() + h2.toString());
        }
    }
}
