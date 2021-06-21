# synchronized

## Java对象头的组成
~~~
Java对象的对象头由 mark word 和  klass pointer 两部分组成，
mark word存储了同步状态、标识、hashcode、GC状态等等。
klass pointer存储对象的类型指针，该指针指向它的类元数据
值得注意的是，如果应用的对象过多，使用64位的指针将浪费大量内存。64位的JVM比32位的JVM多耗费50%的内存。
我们现在使用的64位 JVM会默认使用选项 +UseCompressedOops 开启指针压缩，将指针压缩至32位。
~~~

   
    