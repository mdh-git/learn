# ZGC

~~~
https://blog.csdn.net/CrankZ/article/details/86009279
~~~

## ZGC 的设计目标
~~~
TB 级别的堆内存管理；
最大 GC Pause 不高于 10ms；
最大的吞吐率（Throughput）损耗不高于 15%；

关键点：GC Pause 不会随着 堆大小的增加 而增大。
~~~


## ZGC 中关键技术
~~~
加载屏障（Load barriers）技术；
有色对象指针（Colored pointers）；
单一分代内存管理（这一点很有意思）；
基于区域的内存管理；
部分内存压缩；
即时内存复用。
~~~

## 并行化处理阶段
~~~
标记(Marking);
重定位（Relocation）/压缩（Compaction）；
重新分配集的选择（Relocation set selection）；
引用处理（Reference processing）；
弱引用的清理（WeakRefs Cleaning）;
字符串常量池（String Table）和符号表（Symbol Table）的清理；
类卸载（Class unloading）。
~~~
