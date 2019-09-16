# Java 8 Stream

## streams分类
1.顺序流（sequential streams）
2.并发stream(Parallel streams)


Laziness（延迟加载）是中间操作（intermediate operations）的一个重要特性。
如下面这个例子：中间操作（terminal operation）缺失，当执行这个代码片段的时候，并不会在控制台打印相应的内容，这是因为只有最终操作（terminal operation）存在的时候，中间操作（intermediate operations）才会执行。
~~~
Stream.of("d2", "a2", "b1", "b3", "c")
    .filter(s -> {
        System.out.println("filter: " + s);
        return true;
    });
    
Stream.of("d2", "a2", "b1", "b3", "c")
    .filter(s -> {
        System.out.println("filter: " + s);
        return true;
    })
    .forEach(s -> System.out.println("forEach: " + s));

结果:中间的方法被执行
~~~

