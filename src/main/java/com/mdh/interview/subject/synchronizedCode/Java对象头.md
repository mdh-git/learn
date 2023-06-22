# synchronized

## 保证代码的原子性
~~~
1.修饰实例方法
    作用于当前对象实例加锁，进入同步代码前要获取当前对象实例的锁。
    public synchronized void method(){
        // 方法体
    }
2.修饰静态方法
    给当前类加锁，在同一时间内，只能有一个线程持有该对象的Class对象的锁，
    其他线程需要等待锁的释放才能继续执行该静态方法。
    public static synchronized void staticMethod(){
        // 方法体
    }
3.修饰同步代码块
    指定一个同步对象，这个对象可以是具体的Object或者类.class。在同一时间内，
    只能有一个线程持有该同步锁对象的锁，其他线程需要等待锁的释放才能继续执行该代码块。
    public void method1(){
        synchronized (this){
            // 代码块
        }
    }
~~~

## 原理
~~~
1.synchronize修饰代码块时，JVM采用monitorenter 、monitorexit 两个指令来实现同步
    其中，monitorexit指令出现了两次。
    monitorenter指向同步代码块的开始位置，monitorexit指明同步代码块的结束位置
    monitorenter指令。
    每个对象都是一个监视器锁（monitor）（不加 synchronized 的对象不会关联监视器），在虛拟机执行到monitorenter指令时，首先要尝试获取对象的锁，获取monitor的所有权：
    （1）如果monitor的进入数为0，表示这个对象没有被锁定，则该线程进入monitor，然后将进入数设置为1，该线程即为monitor的所有者；
    （2）如果线程已经占有该monitor，说明当前线程已经拥有了这个对象的锁，只是重新进入，则进入monitor的进入数加1；
    （3）如果其他线程已经占用了monitor，则获取monitor的所有权失败，该线程进入阻塞状态等待，直到monitor的进入数为0，再重新尝试获取monitor的所有权；
     执行monitorexit指令的线程必须是对象锁所对应的monitor的所有者。指令执行时，monitor的进入数减1，如果减1后进入数为0，那线程退出monitor，不再是这个monitor的所有者锁，就被释放了其他被这个monitor阻塞的线程可以尝试去获取这个 monitor 的所有权。
     monitorexit指令出现了两次，第1次为同步正常退出释放锁，第2次为发生异步退出释放锁，也就是说获得锁的线程可以通过正常控制路径退出，或者在同步代码块中抛出异常来释放锁。
2.synchronize修饰同步方法时，JVM采用ACC_SYNCHRONIZED标记符来实现同步，这个标识指明了该方法是一个同步方法。
    “ACC_SYNCHRONIZED”标志用来区分一个方法是否是同步方法。当方法调用时，调用指令将会检查方法的ACC_SYNCHRONIZED是否被设置，如果被设置，当前线程将会获取monitor，获取成功后才执行方法体，最后不管方法是否正常完成都会释放monitor。
    在方法执行期间，其他任何线程都无法再获得同一个monitor对象。
    
Synchronized是基于对象实现的。
实例对象结构里面有对象头，对象头里面有一块结构MarkWord， MarkWord指针指向了monitor。
所谓的Monitor其实是一种同步机制，可以称为内部锁或者monitor锁。
monitorenter 、monitorexit 或者 ACC_SYNCHRONIZED 都是基于Monitor实现的。
				
				堆内存

new Object()    对象头         
					MarkWord（对象的锁信息都是存储在MarkWord中）
					ClassPoint
				实例数据
				对象填充

可以在pom文件中添加 jol-core 的包，打印MarkWord信息

展开MarkWork 标记着四种锁的信息：无锁、偏向锁、轻量级锁、重量级锁
锁状态         25bit        31bit                        1bit       4bit            1bit(偏向锁位)       2bit(锁标志位)
无锁态(new)    unused       hsahcode(如果有调用)          unused     分代年龄            0                  0       1

锁状态                    54bit           2bit           1bit       4bit            1bit(偏向锁位)       2bit(锁标志位)
无锁态(new)    当前线程指针 javaThread*     Epoch         unused     分代年龄             1                 0       1

锁状态                                    62bit                                                         2bit(锁标志位)
轻量级锁（自旋）               指向线程中Lock Record 的指针                                                  0         0
重量级锁                      指向互斥信息（重量级锁）的指针                                                  1         0
GC标记信息                     CMS过程用到的标记信息                                                        1         1


反编译class文件方法：
javap -c -s -v -| ***.class
~~~


## Java对象头的组成
~~~
Java对象的对象头由 mark word 和  klass pointer 两部分组成，
mark word存储了同步状态、标识、hashcode、GC状态等等。
klass pointer存储对象的类型指针，该指针指向它的类元数据
值得注意的是，如果应用的对象过多，使用64位的指针将浪费大量内存。64位的JVM比32位的JVM多耗费50%的内存。
我们现在使用的64位 JVM会默认使用选项 +UseCompressedOops 开启指针压缩，将指针压缩至32位。
~~~

## Synchronized的可见性，有序性，可重入性实现
~~~
Synchronized保证可见性：
    线程加锁前，会清空工作内存中共享变量的值，从而使用共享变量是需要从主内存中重新读取最新的值
    线程加锁后，其它线程无法获取主内存中的共享变量。
    线程解锁前，必须把共享变量的最新值刷到主内存中。
    
Synchronized保证有序性：
    Synchronized同步的代码块，具有排他性，一次只能被一个线程拥有，所以Synchronized保证同一时刻，代码是单线程执行的。
    因为as-if-serial语义的存在，单线程的程序能保证最终结果是有序的，但是不保证不会指令重排。
    所以Synchronized保证的有序是执行结果的有序性，而不是防止指令重排的有序性。
    
Synchronized实现可重入：
    Synchronized是可重入锁，允许一个线程二次请求自己持有对象的锁临界资源，这种情况被称为可重入。
    因为Synchronized锁对象有一个计数器，当一个线程请求成功后，JVM会记下持有锁的线程，并将计数器计为1。此时其他请求线程请求锁，则必须等待。
    而该持有锁的线程如果再次请求这个锁，就可以再次拿到这个锁，同事计数器会递增。
    当线程执行完毕后，计数器会递减，知道计算器为0才释放该锁。

~~~

## 1.6 Synchronized 优化
~~~
锁消除：在sync修饰的代码中，如果不存在操作临界资源的情况，会触发锁消除，即使写了sync，也不会触发 （JIT会做优化）
	public sync void method(){
		// 没有操作临界资源
	}
锁膨胀：如果在一个循环中，频繁的获取和释放锁资源，这样带来的消耗很大，锁膨胀就是将锁的范围扩大，避免频繁的竞争和获取锁资源带来不必要的消耗
	public void method(){
		for(int i = 0; i< 99999; i++){
			sync(对象){

			}
		}
		// 这里上面的代码会触发锁膨胀
		sync(对象){
			for(int i = 0; i< 99999; i++){

			}
		}
	}
锁升级：
	无锁、匿名偏向：当前对象没有作为锁存在
	偏向锁：如果当前锁资源，只有一个线程在频繁的获取和释放，那么这个线程过来，只需要判断，当前指向的线程是否是当前线程
		如果是：直接拿着锁资源
		如果当前线程不是我，基于CAS的方式，尝试将偏向锁指向当前的线程，如果获取不到，触发锁升级，升级为轻量级锁（偏向锁状态出现了锁竞争的情况）
	轻量级锁：会采用自旋锁的方式去频繁的以CAS的形式获取锁资源（采用的自适应自旋锁）
		如果成功，拿走锁资源
		如果自旋一定次数，没拿到锁资源，锁升级
	重量级锁：传统的sync的方式，拿不到锁资源，就挂起当前线程（用户态&内核态）（重量级锁是基于操作系统层面实现的）
~~~



## Lock 和 Synchronized
~~~
1.功能： 都是Java中去解决线程安全的一个工具
2.特性：Synchronized 是java中提供的同步关键字
			实例方法，JVM 会尝试获取实例对象的锁。
			静态方法，JVM 会尝试获取当前 class 的锁。
			修饰方法层面 底层：ACC_SYNCHRONIZED 标识。JVM 通过该 ACC_SYNCHRONIZED 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。
			修饰同步代码块 底层：monitorenter monitorexit 指令
	   	Lock是JUC包下提供的接口，接口有很多实现类，ReentrantLock重入锁的实现
	   		提供lock（） 和 unLock（） 两个方法间的代码是线程安全的，锁的作用域取决于lock实例的生命周期
	    Lock 更加灵活（自主的决定加锁和解锁），提供了非阻塞的竞争锁的方法tryLock ，这个方法返回true/false来告诉当前线程已经有其他线程正在使用锁
		Synchronized无法实现非阻塞竞争锁的方法，锁释放是被动的，同步代码块执行结束以后或者代码出现异常的时候才会释放
		Lock 提供了公平或者非公平（都会尝试加锁）
		sync 只能是非公平的
3.性能：相差不大
		Synchronized 引入了偏向锁，轻量级锁，重量级锁 以及锁升级的机制来实现了锁的优化
		lock 是通过cas自旋方式去实现锁的优化
~~~


# synchronized 和 Lock的区别?
~~~
1. 原始构造
    synchronized是关键字属于JVM层面
        MONITORENTER(monitorenter  通过查看字节码文件) 底层是通过monitor对象来完成,其实wait/notify等方法也依赖于monitor对象,只有在同步块或方法中才能调wait/notify等方法
        monitorexit
        (字节码文件 一个monitorenter  两个monitorexit   synchronized通过一次正常退出,一次异常退出,保证不会出现死锁 )

    Lock是具体类(java.util.concurrent.locks.Lock)是api层面的锁
 
2.使用方法
    synchronized不需要用户去手动释放锁,当synchronized代码执行完成后系统会自动让线程释放对锁的占用

    ReentrantLock则需要用户去手动释放锁,没有主动释放锁,就有可能导致出现死锁现象。
        需要lock()和unlock()方法配合try/finally语句块完成

3.等待是否可中断
    synchronized不能中断,除非抛出异常或者正常进行完成

    ReentrantLock 可中断   1.设置超时方法 tryLock(long time, TimeUnit unit)
                          2. lockInterruptibly放代码块中,调用interrupt()方法可中断

public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

4.加锁是否公平
    synchronized非公平锁

    ReentrantLock两者都可以,默认采用非公平锁, 构造方法可以传入boolean值,true为公平锁

public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

public ReentrantLock() {
        sync = new NonfairSync();
    }

5.锁绑定多个条件Condition
    synchronized没有

    ReentrantLock用来实现分组唤醒需要唤醒的线程们,可以精确唤醒,而不是像synchronized要么随机唤醒一个要么唤醒全部线程。
~~~
