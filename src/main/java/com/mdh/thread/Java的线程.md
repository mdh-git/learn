# Java的线程
~~~
在Linux操作系统中，通常有两种类型的线程：用户级线程（User-Level Threads, ULTs）和内核级线程（Kernel-Level Threads, KLTs）。
Java线程的实现通常依赖于这两种类型的线程。

1. 用户级线程（User-Level Threads, ULTs）
用户级线程是由用户空间的库（如Java的JVM）管理的线程。
在Java的早期版本中，JVM通常使用用户级线程来实现Java的线程。
，所有的Java线程都被映射到单个内核线程上，这意味着所有的Java线程都在同一个CPU核心上运行，这限制了并发性。

2. 内核级线程（Kernel-Level Threads, KLTs）
内核级线程是由操作系统内核直接管理的线程。
在这种模式下，每个Java线程可以独立地被调度到不同的CPU核心上运行，从而提高了并发性和性能。
现代操作系统如Linux通常支持内核级线程，这使得它们能够更好地支持多任务处理。

Java的线程实现
从Java 1.2开始，Java采用了所谓的“绿色线程”（Green Threads）模型，这是一种混合模型，结合了用户级线程和内核级线程的优点。
在这种模型中，Java虚拟机（JVM）内部使用一种称为“轻量级进程”（Lightweight Processes, LWP）的机制来管理线程。

轻量级进程（LWP）
在Linux环境中，当JVM创建一个新的Java线程时，JVM实际上是在操作系统层面创建一个新的LWP（轻量级进程）。
每个LWP可以被操作系统内核调度到不同的CPU核心上运行。
这种机制使得每个Java线程都可以被视为一个独立的实体，从而提高了并发性和性能。


top命令或ps命令结合-L选项来查看Java进程中的LWP：
ps -eLf | grep java
htop

在htop中，你可以看到每个进程和它的LWP（轻量级进程），以及它们被分配到的CPU核心。

总结
因此，虽然我们通常说“Java的线程是LWP”，这是因为Java的线程在Linux上是通过LWP来具体实现的。
这些LWP是由JVM创建的，并被操作系统内核调度，以实现高效的并发执行。这种实现方式使得Java能够提供比早期的用户级线程模型更好的并发性能

~~~

# 为什么使用线程池？
~~~
并发编程中，对于常见的操作系统，线程都是执行任务的基本单元，如果每次执行任务时都创建新的线程，任务执行完毕又进行销毁，会出现以下的问题：


1.资源开销：比如在Linux系统中，频繁的创建和销毁线程，一个是频繁的进行一个系统调用，另外是一些内存和CPU资源调度的占用。
    虽然有一些写时复制的策略防止lwp的创建时的内存占用，但是实际写入还是会申请系统内存的，何况一些页表等本身就有内存占用。
2.性能瓶颈：线程的创建需要系统调用，如果只是简单的计算任务，可能耗时还没创建的rt高，这里反而降低了系统的吞吐量。
3.缺乏资源管理：无限制的创建线程会导致内存溢出，java.lang.OutOfMemoryError: unable to create native thread，
    这里主要因为Java的线程其实Linux中是lwp线程，需要通过JNI进行系统调用创建，每个线程默认需要1MB的栈空间，很容易导致无休止的创建线程导致内存溢出，
    另外就是频繁的系统调用，导致的上下文切换，占用了过多的CPU，反而起到了相反的作用。
4.功能受限：手动管理线程难以实现更高级的功能，如定时任务、周期任务、任务管理、并发任务数的控制等。

通过上面的问题，我们其实可以清晰的感知到这些问题都是归拢到资源没有得到合理的分配和控制导致的，线程池出现的核心宗旨其实就是对资源的合理分配和控制。
除了线程池，其实更多的也接触过数据库连接池、netty的对象池等池化技术，这些池化思想其实都是为了更好的降低资源的消耗以及更好的进行资源管理。
~~~

# JDK21 虚拟线程
~~~
在之前的JDK版本中Java的线程模型比较简单，每一个Java线程对应一个操作系统中的轻量级进程，
这种线程模型中的线程创建、析构及同步等动作，都需要进行系统调用。
而系统调用则需要在用户态(User Mode)和内核态(KerneMode)中来回切换，所以性能开销还是很大的。

虚拟线程，是JDK 实现的轻量级线程，可以避免上下文切换带来的的额外耗费。
实现原理其实是JDK不再是每一个线程都一对一的对应一个操作系统的线程了，而是会将多个虚拟线程映射到少量操作系统线程中，
通过有效的调度来避免那些上下文切换。
~~~


# 虚拟线程和普通线程的区别
~~~
1.虚拟线程总是守护线程。setDaemon(false)方法不能将虚拟线程更改为非守护线程。
    所以，需要注意的是，当所有启动的非守护线程都终止时，JVM将终止。这意味着JVM不会等待虚拟线程完成后才退出。
2.即使使用setPriority()方法，虚拟线程始终具有normal的优先级，且不能更改优先级。
    在虚拟线程上调用此方法没有效果。
3.虚拟线程是不支持stop()、suspend()或resume()等方法。
    这些方法在虚拟线程上调用时会抛出UnsupportedOperationException异常。
~~~


# 创建虚拟线程的方式
~~~
1.通过Thread.startVirtualThread方式
Thread.startVirtualThread(() -> {
    System.out.println("hello world I am a VirtualThread");
});

2.通过Thread.Builder.OfVirtual方式
Thread.Builder.OfVirtual myVirtualThread = Thread.ofVirtual().name("my-virtual-thread");
myVirtualThread.start(() -> {
    System.out.println("hello world I am a VirtualThread from Thread.Builder.OfVirtual");
});

3.线程池也支持虚拟线程了，也可以通过Executors.newVirtualThreadPerTaskExecutor()来创建虚拟线程
try(var executors = Executors.newVirtualThreadPerTaskExecutor()){
    IntStream.range(0,100).forEach(i -> executors.execute(() -> {
        System.out.println("hello world I am a VirtualThread from Executors.newVirtualThreadPerTaskExecutor(),"+i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }));
}
官方并不建议虚拟线程和线程池一起用，主要就是不想让虚拟线程进行池化，
因为像所有资源池一样、线程池旨在共享昂贵的资源，但虚拟线程并不昂贵，因此永远不需要将它们池化。
~~~