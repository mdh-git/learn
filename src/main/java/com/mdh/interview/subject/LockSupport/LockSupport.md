# LockSupport

## 官方文档
~~~
https://www.apiref.com/java11-zh/java.base/java/util/concurrent/locks/LockSupport.html
~~~

## 介绍
~~~
LockSupport是用于创建锁和其他同步类的基本线程阻塞原语。
LockSupport类使用了一个名为Perrmit(许可)的概念来做到阻塞和唤醒的功能,每个线程都是一个许可证(Perrmit),
Perrmit只有两个值1和0, 默认是0
可以把许可证看成是一中(0,1)信号量(Semaphore),但与Semaphore不用的是,许可的累加上限是1。


static void	park()	除非许可证可用，否则禁用当前线程以进行线程调度。
public static void park() {
        UNSAFE.park(false, 0L);
    }
Perrmit默认值是0,所以一开始调用park()方法,当前线程就会阻塞,直到别的线程将当前线程的Perrmit设置为1时,park方法会被唤醒,
然后会将Perrmit再次设置为0并返回。


static void	unpark(Thread thread)	如果给定线程尚不可用，则为其提供许可。
public static void unpark(Thread thread) {
        if (thread != null)
            UNSAFE.unpark(thread);
    }
调用unpark(thread)方法后,就会将thread线程的许可Perrmit设置成1(注意多次调用unpark方法,不会累加,Perrmit值还是1)会自动唤醒thread线程,
即之前阻塞中的LockSupport.park()方法会立即返回。


LockSupport是一个线程阻塞工具类,所有的方法都是静态方法,可以让线程在任意位置阻塞,阻塞之后也有对应的唤醒方法。
归根结底,LockSupport调用的Unsafe中的native代码。

LockSupport提供park()和unpark()方法实现阻塞线程和解除线程阻塞的过程
LockSupport和每个使用它的线程都要一个许可(permit)关联。permit相当于1,0的开关,默认是0,
调用一次unpark就加1变成1,
调用一次park会消费permit,也就是将1变成0,同时park立即返回。
如果再次调用park会变成阻塞(因为permit为0了会阻塞在这里,一直到permit变为1),这时调用unpark会把permit置为1。
每个线程都有一个相关的permit,permit最多只有一个,重复调用unpark也不会积累凭证。

形象的理解
线程阻塞需要消耗凭证(permit),这个凭证最多只有1个。
当调用park方法时
    如果有凭证,则会直接消耗掉这个凭证然后正常退出
    如果无凭证,就必须阻塞等待凭证可用
而unpark则相反,它会增加一个凭证,但凭证最多只能有1个,累加无效。
~~~


## 面试题目
~~~
为什么可以先唤醒线程后阻塞线程?
    因为unpark获得了一个凭证,之后再调用park方法,就可以名正言顺的凭证消费,故不会阻塞。

为什么唤醒两次阻塞两次,但最终结果还是阻塞线程?
    因为凭证的数量最多为1,连续调用两次unpark和调用一次unpark效果一样,只会增加一个凭证;
    而调用两次park却需要消费两个凭证,证不够,导致阻塞。
~~~