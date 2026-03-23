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