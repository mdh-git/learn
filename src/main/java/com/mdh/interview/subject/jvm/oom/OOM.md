# OOM
## 
~~~
java.lang.StackOverflowError
    递归调用,栈空间不足
java.lang.OutOfMemoryError: Java heap space
    对象创建太多或者大对象,导致堆内存不足
java.lang.OutOfMemoryError: GC overhead limit exceeded
    GC回收时间过长
java.lang.OutOfMemoryError: Direct buffer memory
    堆外内存满了
java.lang.OutOfMemoryError: unable to create new native thread
    一个应用进程创线程数超过系统承载极限
java.lang.OutOfMemoryError: Metaspace
    元空间溢出(java -XX:+PrintFlagsInitial)
~~~