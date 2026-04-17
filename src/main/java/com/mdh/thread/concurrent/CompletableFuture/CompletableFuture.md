# CompletableFuture

## 核心用法
~~~
它实现了 Future 和 CompletionStage 两个接口，不仅保留了获取异步结果的能力，更通过 CompletionStage 提供了强大的链式编排和事件驱动特性。
~~~

### 1. 创建异步任务
~~~
CompletableFuture 提供了多种静态工厂方法来创建异步任务，默认使用 ForkJoinPool.commonPool() 作为线程池。
    supplyAsync(Supplier<U> supplier)：用于有返回值的异步任务。
    runAsync(Runnable runnable)：用于没有返回值的异步任务。
    
// 异步执行一个有返回值的任务
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // 模拟耗时操作
    try { Thread.sleep(1000); } catch (Exception e) {}
    return "Hello, CompletableFuture!";
});
~~~

### 2. 链式回调处理
~~~
这是 CompletableFuture 最核心的特性，通过 then 系列方法，可以将多个异步任务像流水线一样串联起来。
~~~
| 方法 | 说明 | 适用场景 |
| :--- | :--- | :--- |
| `thenApply` | 对上一个任务的结果进行转换，有返回值。 | 数据转换，如 `A -> B` |
| `thenAccept` | 消费上一个任务的结果，无返回值。 | 处理最终结果，如打印、保存 |
| `thenRun` | 不关心上一个任务的结果，仅执行后续动作，无返回值。 | 任务完成后的通知、日志记录 |
~~~
CompletableFuture.supplyAsync(() -> 10)
    .thenApply(i -> i * 2)      // 结果为 20
    .thenApply(i -> i + 5)      // 结果为 25
    .thenAccept(System.out::println); // 打印 25
~~~

### 3.任务组合
~~~
CompletableFuture 提供了强大的方法来组合多个独立的异步任务。
~~~

#### 3.1 thenCombine
~~~
thenCombine 等待两个任务都完成，然后将它们的结果合并。

CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);
CompletableFuture<Integer> result = future1.thenCombine(future2, (a, b) -> a + b);
// result.join() 的结果为 30
~~~

#### 3.2 allOf
~~~
allOf：等待所有传入的 CompletableFuture 都完成。常用于并行执行多个独立任务，然后统一处理。

CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);
allFutures.thenRun(() -> System.out.println("所有任务都已完成！"));
~~~

#### 3.2 anyOf
~~~
anyOf：任意一个传入的 CompletableFuture 完成后就返回结果。常用于“竞速”场景，取最快的结果。

CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2);
anyFuture.thenAccept(result -> System.out.println("最快返回的结果是: " + result));
~~~

### 4.异常处理
~~~
CompletableFuture 提供了多种优雅的方式来处理异步任务中的异常。
~~~

#### 4.1 exceptionally
~~~
exceptionally：当任务执行出现异常时，提供一个备选返回值。

CompletableFuture.supplyAsync(() -> {
    if (true) throw new RuntimeException("出错了！");
    return 100;
}).exceptionally(ex -> {
    System.out.println("捕获到异常: " + ex.getMessage());
    return 0; // 返回默认值
});
~~~

#### 4.2 handle
~~~
handle：无论任务成功还是失败都会执行，可以同时处理结果和异常。

CompletableFuture.supplyAsync(() -> 100)
    .handle((result, ex) -> {
        if (ex != null) {
            return "发生错误: " + ex.getMessage();
        }
        return "成功: " + result;
    });
~~~


## 生产环境最佳实践

### 1. 务必使用自定义线程池
~~~
默认的 ForkJoinPool.commonPool() 核心线程数等于 CPU 核心数。  与并行流list.parallelStream() 共用一个线程池
如果你的异步任务是 IO 密集型（如数据库查询、HTTP 请求），很容易耗尽公共池中的线程，导致整个应用的异步任务被阻塞。


强烈建议为不同的业务场景创建独立的 ExecutorService。

// 创建一个专用的 IO 密集型线程池
ExecutorService ioExecutor = Executors.newFixedThreadPool(20);

CompletableFuture.supplyAsync(() -> {
    // 执行 IO 操作
    return fetchDataFromDb();
}, ioExecutor);
~~~

### 2. 正确处理阻塞与超时
~~~
join() vs get()：join() 方法更简洁，它会将受检异常包装成 CompletionException 抛出，避免了繁琐的 try-catch。

超时控制：Java 9 引入了 orTimeout 和 completeOnTimeout 方法来方便地处理超时。在 Java 8 中，可以使用 get(time, unit) 或在外部使用 ScheduledExecutorService 来实现。
~~~

### 3. 始终处理异常
~~~
不要忽略异常处理。未处理的异常可能会导致任务链中断，且难以排查。使用 exceptionally 或 handle 确保你的异步流程具有健壮性。
~~~