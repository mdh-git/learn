# CompletableFuture
~~~
Java 8 引入的异步编程
用“声明式”的方式描述异步任务之间的关系，而不是用阻塞的 get() 去等待结果。
~~~

## 使用场景

### 1.多数据源并行查询(加速接口响应)
~~~
场景描述：
假设你需要组装一个用户详情页，数据来自三个不同的服务：
用户基本信息 (耗时 200ms)
用户订单列表 (耗时 300ms)
用户积分信息 (耗时 150ms)
传统做法（串行）： 总耗时 = 200 + 300 + 150 = 650ms。
CompletableFuture 做法（并行）： 总耗时 = Max(200, 300, 150) = 300ms。性能直接提升 2 倍以上。
~~~
~~~
public UserProfile getUserProfile(Long userId) {
    // 1. 开启三个并行任务
    CompletableFuture<UserInfo> userFuture = CompletableFuture.supplyAsync(() -> 
        userService.getUserInfo(userId), threadPool
    );
    CompletableFuture<List<Order>> ordersFuture = CompletableFuture.supplyAsync(() -> 
        orderService.getUserOrders(userId), threadPool
    );
    CompletableFuture<PointsInfo> pointsFuture = CompletableFuture.supplyAsync(() -> 
        pointsService.getUserPoints(userId), threadPool
    );

    // 2. 等待所有任务完成
    CompletableFuture<Void> allFutures = CompletableFuture.allOf(userFuture, ordersFuture, pointsFuture);

    // 3. 组合结果
    return allFutures.thenApply(v -> {
        UserProfile profile = new UserProfile();
        profile.setUserInfo(userFuture.join()); // 此时join不会阻塞，因为已经确定完成了
        profile.setOrders(ordersFuture.join());
        profile.setPoints(pointsFuture.join());
        return profile;
    }).join();
}
~~~


### 2，任务编排与依赖处理（链式调用）
~~~
场景描述：
任务 B 依赖任务 A 的结果。例如：先根据 ID 获取用户信息，再根据用户信息获取其对应的订单详情。

核心方法： thenCompose
它的作用类似于 flatMap，用于连接两个异步任务，避免“回调地狱”。
~~~
~~~
CompletableFuture.supplyAsync(() -> {
    // 步骤1: 获取用户ID
    return "userId_123";
}, threadPool).thenCompose(userId -> {
    // 步骤2: 依赖上一步的结果，发起新的异步任务
    return CompletableFuture.supplyAsync(() -> orderService.getOrdersByUserId(userId), threadPool);
}).thenAccept(orders -> {
    // 步骤3: 处理最终结果
    System.out.println("订单数量: " + orders.size());
});
~~~


### 3.多结果合并（并行计算后汇总）
~~~
场景描述：
两个任务没有依赖关系，但最后的结果需要两者共同参与。例如：同时获取商品价格和库存，最后计算总价。

核心方法： thenCombine
~~~
~~~
CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(() -> 99.9, threadPool);
CompletableFuture<Integer> stockFuture = CompletableFuture.supplyAsync(() -> 100, threadPool);

// 等待两个任务都完成，然后合并结果
CompletableFuture<ProductInfo> result = priceFuture.thenCombine(stockFuture, (price, stock) -> {
    return new ProductInfo(price, stock);
});
~~~

### 4.兜底与超时控制（提升系统健壮性）
~~~
场景描述：
调用第三方接口时，如果超时或报错，不能让整个系统卡死，需要返回一个默认值（降级）。

核心方法： orTimeout, exceptionally, completeOnTimeout
~~~
~~~
CompletableFuture.supplyAsync(() -> {
    // 模拟耗时操作或RPC调用
    Thread.sleep(3000);
    return "正常数据";
}, threadPool)
// 设置超时：2秒后未完成则触发异常
.orTimeout(2, TimeUnit.SECONDS) 
// 异常处理/兜底
.exceptionally(ex -> {
    log.error("调用失败", ex);
    return "默认降级数据"; 
})
.join();
~~~

## CompletableFuture  为什么必须要手动创建线程池
~~~
supplyAsync 不传递线程池的时候默认使用的是 ForkJoinPool.commonPool()

问题1 ：ForkJoinPool.commonPool()的核心线程数为 CPU的核数-1
    与 List.parallelStream() 公用一个线程池
问题2 ：wt.setDaemon(true); 
    JVM 只有当"非守护线程"结束时，JVM才会退出   不会等待守护线程完成
问题3： 如果不调用 get() 或者 join() ,也不用exceptionally处理异常，任务里的异常会被直接吞掉

解决： 
    1.传入自定义的线程池（资源的隔离与防雪崩）
    2.代码结束前要执行线程池的关闭 
    3.异常处理，必须使用exceptionally 或者handle 进行异常信息监控
~~~