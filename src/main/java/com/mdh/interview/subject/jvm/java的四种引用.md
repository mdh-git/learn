# JVM的四种引用
~~~
(1) 强引用:只有所有GC Roots对象都不通过强引用引用该对象，该对象才能被垃圾回收
    不会被垃圾回收
    强引用是造成Java内存泄露的主要原因之一
    
    User user = new User();
    
(2) 软引用：仅有软引用引用该对象时，在垃圾回收后，内存扔不足时会再次触发垃圾回收
    内存充足时   不会被回收
    内存不足时    会被回收
    通常用在对内存敏感的程序中,比如高速缓存
    
    User user = new User();
    SoftReference softReference = new SoftReference(user);
    
(3) 弱引用:仅有弱引用引用该对象时，在垃圾回收后，无论内存是否充足，都会回收弱引用
    WeakReference
    内存不足时    会被回收
    
    User user = new User();
    WeakReference weakReference = new WeakReference(user);
    
    
    在ThreadLocal中存在内存泄漏问题， key是弱引用，value是强引用
    
(4) 虚引用：必须配合引用队列使用，被引用对象回收时，会将虚引用入队，由Reference Handler线程调用虚引用相关方法释放直接内存
    PhantomReference
    与其他几种引用都不同,虚引用并不会决定对象的生命周期
    任何时候都可能被垃圾回收器回收,它不能单独使用也不能访问对象,虚引用必须和引用队列(ReferenceQueue)联合使用

    主要是跟踪对象垃圾回收的状态,做垃圾回收之前的清理操作
    
    User user = new User();
    ReferenceQueue referenceQueue = new ReferenceQueue<>();
    PhantomReference weakReference = new PhantomReference<>(user, referenceQueue);
    
~~~
##
