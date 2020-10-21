# LongAdder分段锁机制
~~~
https://blog.csdn.net/rammus7/article/details/106603840
https://blog.csdn.net/eluanshi12/article/details/84871879
https://blog.csdn.net/codingtu/article/details/89047291
~~~

~~~
内部实现了自动分段迁移的机制，也就是如果某个Cell的value执行CAS失败了，那么就会自动去找另外一个Cell分段内的value值进行CAS操作。
~~~