package com.mdh.cache.cacheOfAnno;

/**
 *
 * @author madonghao
 * @date 2018/11/6
 */
public class MyAccountService {

    private MyCacheManager<Account> cacheManager;

    /** 构造一个缓存管理器 */
    public MyAccountService() {
        cacheManager = new MyCacheManager<>();
    }

    public Account getAccountByName(String acctName) {
        Account result = cacheManager.getValue(acctName);
        if (result != null) {
            // 如果在缓存中，则直接返回缓存的结果
            System.out.println("get from cache..."+acctName);
            return result;
        }
        // 否则到数据库中查询
        result = getFromDB(acctName);
        if(result != null) {
            // 将数据库查询的结果更新到缓存中
            cacheManager.addOrUpdateCache(acctName, result);
        }
        return result;
    }

    // 清空缓存
    public void reload() {
        cacheManager.evictCache();
    }

    private Account getFromDB(String acctName) {
        System.out.println("real querying db..."+acctName);
        Account account = new Account(acctName);
        return account;
    }

    public static void main(String[] args) {
        MyAccountService service = new MyAccountService();
        // 开始查询账号
        // 第一次查询，应该是数据库查询
        service.getAccountByName("somebody");
        // 第二次查询，应该直接从缓存返回
        service.getAccountByName("somebody");

        // 重置缓存
        service.reload();
        System.out.println("after reload...");

        // 应该是数据库查询
        service.getAccountByName("somebody");
        // 第二次查询，应该直接从缓存返回
        service.getAccountByName("somebody");

        service.getAccountByName("abc");
        service.getAccountByName("def");


        service.cacheManager.evictCache("abc");

        service.cacheManager.all();
    }
}
