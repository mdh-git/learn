package com.mdh.interview.subject.volatileCode;

public class SingletonDemo {

    private static volatile SingletonDemo singleton = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 构造方法");
    }

    // DCL (Double check lock 双端检锁机制)
    public static SingletonDemo getInstance(){
        if(singleton == null){
            synchronized (SingletonDemo.class){
                if(singleton == null){
                    singleton = new SingletonDemo();
                }
            }
        }
        return singleton;
    }

    /**
     * instance = new SingletonDemo();
     *
     *  可以分解为以下三个步骤
     * 1 memory=allocate(); 分配内存 相当于c的malloc
     * 2 ctorInstanc(memory) 初始化对象
     * 3 s=memory 设置s指向刚分配的地址
     *
     *  上述三个步骤可能会被重排序为 1-3-2，也就是：
     * 1 memory=allocate(); 分配内存 相当于c的malloc
     * 3 s=memory 设置s指向刚分配的地址
     * 2 ctorInstanc(memory) 初始化对象
     *
     * 而一旦假设发生了这样的重排序，比如线程A在第16行执行了步骤1和步骤3，但是步骤2还没有执行完。这个时候线程A执行到了第13行，它会判定instance不为空，然后直接返回了一个未初始化完成的instance！
     *
     * 详细的解释文档： https://www.imooc.com/article/276841
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
