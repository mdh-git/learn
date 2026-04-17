# InheritableThreadLocal
~~~
InheritableThreadLocal 是 Java 中用于解决父子线程间数据传递问题的一个类。它是 ThreadLocal 的子类，能够让子线程自动继承父线程中设置的变量值。

普通的 ThreadLocal 变量是线程隔离的。每个线程都有自己独立的变量副本，互不干扰。这意味着，如果你在父线程（如主线程）中设置了一个 ThreadLocal 的值，那么在它创建的子线程中是无法获取到这个值的，会得到 null。

这在很多场景下是符合预期的，但在需要传递上下文信息时就成了问题，例如：
    分布式追踪：传递 traceId 或 spanId。
    用户信息传递：传递当前登录用户的 ID 或权限信息。
    数据库事务：传递事务 ID。

InheritableThreadLocal 就是为了解决这个问题而生的。
~~~

## 基础用法
~~~
import java.lang.InheritableThreadLocal;

public class InheritableThreadLocalTest {
    // 使用普通的 ThreadLocal
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // 使用 InheritableThreadLocal
    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("父线程的ThreadLocal值");
        inheritableThreadLocal.set("父线程的InheritableThreadLocal值");

        new Thread(() -> {
            System.out.println("子线程获取ThreadLocal: " + threadLocal.get()); // 输出: null
            System.out.println("子线程获取InheritableThreadLocal: " + inheritableThreadLocal.get()); // 输出: 父线程的InheritableThreadLocal值
        }).start();
    }
}
~~~

## 实现原理
~~~
在子线程创建时，将父线程的 inheritableThreadLocals 映射表进行浅拷贝。

1.独立的存储结构
    Java 的 Thread 类内部维护了两个 ThreadLocalMap：
        threadLocals：存储普通 ThreadLocal 的变量。
        inheritableThreadLocals：专门存储 InheritableThreadLocal 的变量。
        InheritableThreadLocal 通过重写 getMap() 和 createMap() 方法，使其所有操作都指向 inheritableThreadLocals。

2.创建时的“遗传”过程
    当父线程通过 new Thread() 创建子线程时，会在 Thread 的 init() 方法中执行关键逻辑：
        1.检查父线程的 inheritableThreadLocals 是否为 null。
        2.如果不为 null，则调用 createInheritedMap(parentMap) 方法，为子线程创建一个新的 ThreadLocalMap。
        3.这个新方法会遍历父线程 inheritableThreadLocals 中的所有条目，并通过调用 childValue(parentValue) 方法（默认直接返回父值）来获取子线程的初始值，然后存入子线程自己的 inheritableThreadLocals 中。

这个过程是一次性的浅拷贝，发生在子线程初始化的瞬间。
~~~

## 缺点
~~~
1. 线程池中的“数据污染”问题
    原因：线程池中的线程是复用的。当一个任务执行完毕，线程并不会销毁，而是回到池中等待下一个任务。
    问题：InheritableThreadLocal 的值是在线程创建时继承的。线程池中的线程早已创建好，因此后续提交的任务无法继承到提交任务时父线程的最新值。更严重的是，如果上一个任务设置了值但忘记清理，下一个复用到该线程的任务就会获取到“脏数据”。

解决方案：
    1.手动清理：在使用完 InheritableThreadLocal 后，务必在 finally 代码块中调用 remove() 方法。
    InheritableThreadLocal<String> context = new InheritableThreadLocal<>();
        try {
            context.set("someValue");
            // 执行业务逻辑
        } finally {
            context.remove(); // 必须清理！
        }
    
    2.使用更高级的库：阿里巴巴开源的 TransmittableThreadLocal (TTL) 是解决此问题的标准方案。它通过包装线程池或 Runnable/Callable，在任务提交时捕获父线程的上下文，并在任务执行时传递给工作线程，完美解决了线程池场景下的上下文传递问题。

2.浅拷贝与引用共享
    继承过程是浅拷贝，只复制了对象的引用。如果 InheritableThreadLocal 中存放的是一个可变对象，父子线程持有的是同一个对象的引用。修改对象内部的属性，对两个线程都是可见的，这可能会引发并发安全问题。

3. 内存泄漏风险
    和 ThreadLocal 一样，InheritableThreadLocal 也存在内存泄漏的风险。如果线程生命周期很长（如线程池中的核心线程），而 InheritableThreadLocal 的值一直不被清理，就会导致该值无法被垃圾回收。因此，remove() 方法是必须养成的良好编程习惯。
~~~