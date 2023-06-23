# IO

## BIO NIO AIO
~~~
BIO (Blocking I/O) 同步阻塞

NIO(Non-blocking IO) 同步非阻塞

AIO 异步非阻塞
~~~

## NIO的原理，包括哪几个组件？
~~~
NIO(Java Non-blocking I/O)是一种I/O技术，其核心原理是基于事件驱动的方式进行操作的。

NIO的工作原理：
    基于缓冲区、通道和选择器的组合，通过高效地利用系统资源，以支持高并发和高吞吐量的数据处理。
    相比传统的I/O编程方式，java NIO提供了更灵活和高效的编程模式。
    
NIO三大核心组件：
    Channel(通道):   类似于传统I/O的Stream, 用于实现数据传输的组件。在NIO中，有很多类型的Channel可以使用，例如：FileChannel、
        SocketChannel、DatagramChannel等，可用于文件操作、网络传输等不同场景。
    Buffer(缓冲区):    用于存储数据的容器，可以理解为暂存需要传输的数据的地方。在NIO中，存在多种类型的缓冲区，比如：ByteBuffer、
        CharBuff、IntBuffer等。
    Selector(选择器):  用于注册Channel并监听其I/O事件。当Channel准备好读或者写数据时，会得到通知。Selector可以高效地轮询多个
        Channel，并且避免了使用多线程或多进程对多个Channel进行轮询的情况，从而减少了系统资源开销。
    
     
                     -- channel -- buffer --   程序
Thread  -- Selector  -- channel -- buffer --   程序
                     -- channel -- buffer --   程序
                     
NIO是可以做到用一个线程来处理多个操作。
~~~