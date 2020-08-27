# volatile
~~~
volatile是java虚拟机提供的轻量级的同步机制

三个特性：
    1.保证可见性(主内存修改，每个线程都可见)
    2.不保证原子性
    3.禁止指令重排
~~~

## JMM（java内存模型）
~~~
JMM(java内存模型Java Memory Model，简称JMM)本身是一种抽象的概念并不真实存在，它描述的是一组规范，
通过这组规范定义了程序中各个变量(包括实例字段，静态字段和构成数组对象的元素)的访问方式。

JMM的特性：
1.可见性
2.原子性
3.有序性

JMM关于同步的规定：
1.线程解锁前，必须把共享变量的值刷新回主内存
2.线程加锁前，必须读取主内存的最新值到自己的工作内存
3.加锁解锁是同一把锁

由于JVM运行程序的实体是线程，而每个线程创建时JVM都会为其创建一个工作内存(有些地方称为栈空间)，工作内存是每个线程
的私有数据区域，而Java内存模型中规定所有变量都存储在主内存，主内存是共享内存区域，所有线程都可以访问，但线程对变量
的操作(读取赋值等)必须在工作内存中进行，首先要将变量从主内存拷贝到自己的工作内存空间，然后对变量进行操作，操作完成后
在将变量写会主内存，不能直接操作主内存中的变量，各个线程中的工作内存中存储着主内存中的变量副本拷贝，因此不同的线程间
无法访问对方的工作内存，线程间的通信(传值)必须通过主内存来完成，简要的访问过程如下图：

        线程A                线程B
          |                   |
          |                   |
      本地内存A             本地内存B
     共享变量副本           共享变量副本
          |                   |
          |--------JMM控制-----|
          |                   |

                主内存
   共享变量      共享变量      共享变量
~~~

## a++的字节码文件
~~~
先编译java文件成class文件，在执行javap命令

字节码文档： https://www.jianshu.com/p/247e2475fc3a

"C:\Program Files\Java\jdk1.8.0_211\bin\javap.exe" -c com.mdh.interview.subject.volatileCode.Add
Compiled from "Add.java"
public class com.mdh.interview.subject.volatileCode.Add {
  volatile int num;

  java.util.concurrent.atomic.AtomicInteger atomicInteger;

  public com.mdh.interview.subject.volatileCode.Add();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: iconst_0
       6: putfield      #2                  // Field num:I
       9: aload_0
      10: new           #3                  // class java/util/concurrent/atomic/AtomicInteger
      13: dup
      14: invokespecial #4                  // Method java/util/concurrent/atomic/AtomicInteger."<init>":()V
      17: putfield      #5                  // Field atomicInteger:Ljava/util/concurrent/atomic/AtomicInteger;
      20: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: iconst_1
       4: invokevirtual #7                  // Method java/io/PrintStream.println:(I)V
       7: return

  public void add();
    Code:
       0: aload_0
       1: dup
       2: getfield      #2                  // Field num:I
       5: iconst_1
       6: iadd
       7: putfield      #2                  // Field num:I
      10: return

  public void atomicAdd();
    Code:
       0: aload_0
       1: getfield      #5                  // Field atomicInteger:Ljava/util/concurrent/atomic/AtomicInteger;
       4: invokevirtual #8                  // Method java/util/concurrent/atomic/AtomicInteger.incrementAndGet:()I
       7: pop
       8: return
}

Process finished with exit code 0


~~~

## 指令重排

~~~
计算机在执行程序时，为了提高性能，编译器和处理器会对指令做重排

源代码  ->  编译器优化的重排  -> 指令并行的重排  -> 内存系统的重排  -> 最终执行的指令

单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致

处理器在进行重排序时必须考虑指令之间的**数据依赖性**

多线程环境中线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确认的，结果无法预知
~~~

## Java对象实例化的过程
~~~
1.先为对象分配空间，并按属性类型默认初始化   ps：八种基本数据类型，按照默认方式初始化，其他数据类型默认为null   
2.父类属性的初始化（包括代码块，和属性按照代码顺序进行初始化）   
3.父类构造函数初始化   
4.子类属性的初始化（同父类一样）   
5.子类构造函数的初始化  

~~~