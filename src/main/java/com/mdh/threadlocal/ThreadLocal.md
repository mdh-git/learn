# 1.ThreadLocal的使用场景

## 1.1 场景1

每个线程需要一个独享对象（通常是工具类，典型需要使用的类有SimpleDateFormat和Random）

每个Thread内有自己的实例副本，不共享

比喻：教材只有一本，一起做笔记有线程安全问题。复印后没有问题，使用ThradLocal相当于复印了教材。

## 1.2 场景2

每个线程内需要保存全局变量（例如在拦截器中获取用户信息），可以让不同方法直接使用，避免参数传递的麻烦


ThreadLocal 
默认大小为 INITIAL_CAPACITY = 16
hash 算法 为 哈希取余法 (int i = key.threadLocalHashCode & (len-1);)
解决冲突为为在寻址法: e = tab[i = nextIndex(i, len)])

public void set(T value) {
    //获取当前请求的线程
    Thread t = Thread.currentThread();
    //取出 Thread 类内部的 threadLocals 变量(哈希表结构)
    ThreadLocalMap map = getMap(t);
    if (map != null)
        // 将需要存储的值放入到这个哈希表中
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

内存泄露问题： 
    key的内存泄漏：key为弱引用，不会产生泄露
    value的内存泄露： value为强引用，在调用 set()、get()、remove() 方法的时候，会清理掉 key 为 null 的记录。
                    使用完 ThreadLocal方法后最好手动调用remove()方法

扩容机制： set(ThreadLocal<?> key, Object value) 方法中

if (!cleanSomeSlots(i, sz) && sz >= threshold)
    rehash();

cleanSomeSlots(i, sz): 清除key为null的数据
threshold = len * 2 / 3:  判断当前大小,是否大于等于总长度的2/3  

rehash() 方法中
if (size >= threshold - threshold / 4)
resize();
在判断 size是不是大于 3/4,在进行扩容

resize()方法
int newLen = oldLen * 2;
Entry[] newTab = new Entry[newLen];
1.new 一个新的数组,为原来的两倍
2.老数组元素重新散列到新数组
3.table引用指向新的数组

~~~
https://mp.weixin.qq.com/s/LrtaTTz25NIV7EAr9q3BHQ
~~~