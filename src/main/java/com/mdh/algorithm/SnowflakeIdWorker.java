package com.mdh.algorithm;

/**
 * 分布式自增ID算法---雪花算法
 * 一般情况，实现全局唯一ID，有三种方案，分别是通过中间件方式、UUID、雪花算法。
 *
 * 　　方案一，通过中间件方式，可以是把数据库或者redis缓存作为媒介，从中间件获取ID。
 * 这种呢，优点是可以体现全局的递增趋势（优点只能想到这个），
 * 缺点呢，倒是一大堆，比如，依赖中间件，假如中间件挂了，就不能提供服务了；依赖中间件的写入和事务，会影响效率；数据量大了的话，你还得考虑部署集群，考虑走代理。
 * 这样的话，感觉问题复杂化了
 *
 * 　　方案二，通过UUID的方式，java.util.UUID就提供了获取UUID的方法，使用UUID来实现全局唯一ID，
 * 优点是操作简单，也能实现全局唯一的效果，
 * 缺点呢，就是不能体现全局视野的递增趋势；
 * 太长了，UUID是32位，有点浪费；最重要的，是插入的效率低，
 * 因为呢，我们使用mysql的话，一般都是B+tree的结构来存储索引，假如是数据库自带的那种主键自增，节点满了，会裂变出新的节点，新节点满了，再去裂变新的节点，这样利用率和效率都很高。
 * 而UUID是无序的，会造成中间节点的分裂，也会造成不饱和的节点，插入的效率自然就比较低下了。
 *
 * 　　方案三，基于redis生成全局id策略，因为Redis是单线的天生保证原子性，可以使用原子性操作INCR和INCRBY来实现，
 * 注意在Redis集群情况下，同MySQL一样需要设置不同的增长步长，同时key一定要设置有效期，可以使用Redis集群来获取更高的吞吐量
 *
 *
 *     方案四，通过snowflake算法
 *
 *     0  -   00000000  00000000  00000000  00000000  00000000  0 - 00000000  00 - 00000000  0000
 *     |                            |                                    |                |
 *   1bit                       41bit-时间戳                         10bit工作机器id    12bit-序列号
 *
 * 1位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0
 *
 * 41位，用来记录时间戳（毫秒）。
 *      41位可以表示$2^{41}-1$个数字，
 *      如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 $2^{41}-1$，减1是因为可表示的数值范围是从0开始算的，而不是1。
 *      也就是说41位可以表示$2^{41}-1$个毫秒的值，转化成单位年则是$(2^{41}-1) / (1000 * 60 * 60 * 24 * 365) = 69$年
 *
 * 10位，用来记录工作机器id。
 *      可以部署在$2^{10} = 1024$个节点，包括5位datacenterId和5位workerId
 *      5位（bit）可以表示的最大正整数是$2^{5}-1 = 31$，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
 *
 * 12位，序列号，用来记录同毫秒内产生的不同id。
 *      12位（bit）可以表示的最大正整数是$2^{12}-1 = 4095$，即可以用0、1、2、3、....4094这4095个数字，来表示同一机器同一时间截（毫秒)内产生的4095个ID序号
 * 　　由于在Java中64bit的整数是long类型，所以在Java中SnowFlake算法生成的id就是long来存储的。
 *
 * 　　SnowFlake可以保证：
 *
 * 所有生成的id按时间趋势递增
 * 整个分布式系统内不会产生重复id（因为有datacenterId和workerId来做区分）
 *
 * SnowFlake的结构如下(每部分用-分开):
 *   0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *   1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 *   41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 *        8  * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 *   10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId
 *   12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号
 *   加起来刚好64位，为一个Long型。
 *   SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * @author madonghao
 * @create 2020-09-08 19:26
 **/
public class SnowflakeIdWorker {
// ==============================Fields===========================================
    /** 开始时间截 (2015-01-01) */
    private final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}
