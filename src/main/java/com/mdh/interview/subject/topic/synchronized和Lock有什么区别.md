# synchronized和Lock有什么区别
~~~
语法层面
    synchronized是关键字，源码在jvm中，用c++语言实现
    Lock是接口，源码由jdk提供，用java语言实现
    使用synchronized 时，退出同步代码块锁会自动释放，而使用Lock 时，需要手动调用unlock方法释放锁
    
功能层面
    二者均属于悲观锁、都具备基本的互斥、同步、锁重入功能
    Lock提供了许多synchronized不具备的功能，例如公平锁、可打断、可超时、多条件变量Lock 有适合不同场景的实现，如ReentrantLock，ReentrantReadWriteLock(读写锁)
    
性能层面
    在没有竞争时，synchronized做了很多优化，如偏向锁、轻量级锁，性能不赖
    在竞争激烈时，Lock的实现通常会提供更好的性能
~~~

~~~ 区别
synchronized 是java的关键字，是有JVM底层去实现锁的获取与释放，不需要手动管理
synchronized进程失败会直接阻塞、无法直接退出
不支持中断响应
仅支持非公平锁（可能出现插队现象）
是被动释放锁，代码执行完成或者异常，自动释放锁
性能：通过锁升级优化使用场景

Lock是JUC包的接口，比如ReentrantLock是常用的实现，基于代码层面去实现的，需要手动调用 lock() 获取锁、 unlock()释放锁
lock支持非阻塞获取锁，比如tryLock(),获取失败不会阻塞,可做降级处理
可以通过lockInterruptibly()来相应中断，避免线程无限等待
lock可以通过构造参数指定公平锁或非公平锁
lock需要手动释放锁，否则可能会导致锁的泄漏
性能：通过自旋锁来优化短时间的锁竞争
~~~