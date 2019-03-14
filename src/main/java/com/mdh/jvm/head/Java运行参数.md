# Java运行参数

> JVM的堆的内存， 是通过下面面两个参数控制的 
>
> 
>
> -Xms 最小堆的大小， 也就是当你的虚拟机启动后， 就会分配这么大的堆内存给你 
> -Xmx 是最大堆的大小 
>
> 
>
> 当最小堆占满后，会尝试进行GC，如果GC之后还不能得到足够的内存(GC未必会收集到所有当前可用内存)，分配新的对象，那么就会扩展堆，如果-Xmx设置的太小，扩展堆就会失败，导致OutOfMemoryError错误提示。


> -XX:+UseParallelGC：选择垃圾收集器为并行收集器。此配置仅对年轻代有效。可以同时并行多个垃圾收集线程，但此时用户线程必须停止。
> -XX:+UseParNewGC:设置年轻代为多线程收集。可与CMS收集同时使用。在serial基础上实现的多线程收集器。
> -XX:+UseParallelGC 
  指 定在 New Generation 使用 parallel collector, 并行收集 , 暂停 app threads, 同时启动多个垃圾回收 thread, 不能和 CMS gc 一起使用 . 系统吨吐量优先 , 但是会有较长长时间的 app pause, 后台系统任务可以使用此 gc。UseParallelGC是jdk1.7选择parallel 回收器默认开启的。
>-XX:+UseParNewGC 
 指定在 New Generation 使用 parallel collector, 是 UseParallelGC 的 gc 的升级版本 , 有更好的性能或者优点 , 可以和 CMS gc 一起使用。UseParNewGC需要用户自己手动开启。


https://blog.csdn.net/a503921892/article/details/39048889