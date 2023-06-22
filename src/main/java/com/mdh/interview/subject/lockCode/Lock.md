# Lock

## 公平锁和非公平锁
~~~
Java语言中有许多原生线程安全的数据结构，比如ArrayBlockingQueue、CopyOnWriteArrayList、LinkedBlockingQueue，
它们线程安全的实现方式并非通过synchronized(非公平)关键字，而是通过java.util.concurrent.locks.ReentrantLock来实现。 

“可重入锁实现原理”。
ReentrantLock的实现是基于其内部类FairSync(公平锁)和NonFairSync(非公平锁)实现的。
其可重入性是基于Thread.currentThread()实现的: 如果当前线程已经获得了执行序列中的锁，那执行序列之后的所有方法都可以获得这个锁。

公平锁：(多个线程按照申请锁的顺序来获取锁)
公平和非公平锁的队列都基于锁内部维护的一个双向链表，表结点Node的值就是每一个请求当前锁的线程。
公平锁则在于每次都是依次从队首取值。
锁的实现方式是基于如下几点：
表结点Node和状态state的volatile关键字。
sum.misc.Unsafe.compareAndSet的原子操作(见附录)。

非公平锁：(多个线程获取锁的顺序并不一定是按照申请锁的顺序)(优势在于吞吐量比公平锁高)
在等待锁的过程中， 如果有任意新的线程妄图获取锁，都是有很大的几率直接获取到锁的。
~~~

## 可重入锁(又名递归锁)
~~~
可重入锁，指的是以线程为单位，当一个线程获取对象锁之后，这个线程可以再次获取本对象上的锁，而其他的线程是不可以的。
synchronized 和   ReentrantLock 都是可重入锁。
可重入锁的意义之一在于防止死锁。

实现原理实现是通过为每个锁关联一个请求计数器和一个占有它的线程。当计数为0时，认为锁是未被占有的；线程请求一个未被占有的锁时，
JVM将记录锁的占有者，并且将请求计数器置为1 。
如果同一个线程再次请求这个锁，计数器将递增；
每次占用线程退出同步块，计数器值将递减。直到计数器为0,锁被释放。
~~~

## 自旋锁
~~~
指尝试获取锁的线程不会立即阻塞,而是采用循环的方式去尝试获取锁,这样的好处是减少线程上下文切换的消耗,缺点是循环会消耗CPU资源
~~~

## 独占锁(写锁)/共享锁(读锁)/互斥锁
~~~
独占锁: 指该锁一次只能被一个线程所持有, 
    ReentrantLock和synchronized都是独占锁

共享锁: 指该锁可以被多个线程持有
    ReentrantReadWriteLock其读锁是共享锁,写锁是独占锁

读锁的共享锁可保证并发读是非常高效的,读写,写读,写写的过程是互斥的。
~~~

## ReentrantLock
~~~
https://blog.csdn.net/fuyuwei2015/article/details/83719444

ReentrantLock主要利用CAS+AQS队列来实现

ReentrantLock是一种可重入的排它锁，主要用来解决多线程对共享资源竞争的问题。
提供了比Synchronized关键字更加灵活的锁机制。

基本结构：
    ReentrantLock内部维护了一个Sync对象(AbstractQueuedSynchronizer类的子类)，Sync持有锁、等待队列等状态信息，
    实际上ReentrantLock的大部分功能都是由Sync来实现的。
加锁过程：
    当一个线程调用ReentrantLock的lock方法是，会先尝试CAS操作获取锁，如果成功则返回；否则，线程会被放入等待队列中，等待唤醒重新尝试获取锁。
    如果一个线程已经持有了锁，那么它可以重入这个锁，继续获取该锁而不会被阻塞。
    ReentrantLock通过维护一个计数器来实现重入锁功能，每次重入计数器+1，每次释放锁计数器-1，当计数器为0时，锁被释放。
解锁过程：
    当一个线程调用ReentrantLock的unlock()方法时，会将计数器-1，如果计数器变为了0，则锁被完全释放。
    如果计数器还大于0，则表示有其他线程正在等待该锁，此时会唤醒等待队列中的一个线程来获取锁。
   
   总之，ReentrantLock的实现原理主要是基于CAS操作和等待队列来实现，它通过sync对象来维护锁的状态，支持重入锁和公平锁等特性。
   提供了比Synchronized更加灵活的锁机制，是Java并发编程常用的同步工具之一。
~~~

## ReentrantLock 公平锁和非公平锁
~~~
ReentrantLock默认非公平锁,通过 new ReentrantLock(true)实现公平锁

非公平锁在调用lock之后，首先就会调用CAS进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回。
非公平锁在CAS失败后，和公平锁一样都会进入到tryAcquire方法，在tryAcquire方法中，如果发现锁这个时候被释放了（state == 0），非公平锁会直接CAS
抢锁，但公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，去队列排队。

static final class NonfairSync extends Sync {
    private static final long serialVersionUID = 7316153563782823691L;

    /**
     * Performs lock.  Try immediate barge, backing up to normal
     * acquire on failure.
     */
    final void lock() {
        if (compareAndSetState(0, 1)) // 会先CAS抢占一次锁
            setExclusiveOwnerThread(Thread.currentThread());
        else
            acquire(1);
    }

    // 尝试获取锁，调用非公平锁的获取逻辑
    protected final boolean tryAcquire(int acquires) {
        return nonfairTryAcquire(acquires);
    }
}
    
// 非公平锁的获取逻辑
final boolean nonfairTryAcquire(int acquires) {
    // 获取当前线程对象和当前锁状态
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) { // 如果锁状态为0
        // CAS操作尝试将锁状态从0变成acquires
        // 如果成功，将当前线程标记为持有该锁的线程，返回 true表示获取锁成功
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) { // 如果锁状态不为0且当前线程是锁持有者
        // 将锁状态改为当前线程已经持有的计数和acquires之和，并返回true，表示获取锁成功
        int nextc = c + acquires;
        if (nextc < 0) // overflow 检查计数是否越界
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    } 
    return false; // 获取锁失败
}    

~~~