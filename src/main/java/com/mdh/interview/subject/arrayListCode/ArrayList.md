# ArrayList
~~~
    ArrayList
    初始化时一个大小为10的数组
    线程不安全是因为add方法没有加锁

java.util.ConcurrentModificationException
ArrayList线程不安全常见的异常

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

1.故障现象
       java.util.ConcurrentModificationException
2.导致原因
        并发操作修改,导致多线程间并发修改异常
3.解决方案
        new Vector<>(); (方法都加synchronized,线程安全,性能低)
        Collections.synchronizedList(new ArrayList<>()); (使用工具类里面的安全方法)
        new CopyOnWriteArrayList<>(); (JUC包下 写时复制,读写分离的思想)
4.优化建议
        写时复制
~~~

## CopyOnWriteArrayList
~~~
    写时复制,读写分离的思想
CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候,不直接往当前容器object[]添加,而是先将当前容器object[]
进行copy,复制出一个新的容器object[] newElements,然后新的容器object[] newElements里添加元素,添加之后,
再将原容器的引用指向新的容器 setArray(newElements); 这样的做的好处是可以对CopyOnWrite容器进行并发的读,
而不需要加锁,因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想,读和写不同的容器
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
~~~