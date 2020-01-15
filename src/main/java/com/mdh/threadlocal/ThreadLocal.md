# 1.ThreadLocal的使用场景

## 1.1 场景1

每个线程需要一个独享对象（通常是工具类，典型需要使用的类有SimpleDateFormat和Random）

每个Thread内有自己的实例副本，不共享

比喻：教材只有一本，一起做笔记有线程安全问题。复印后没有问题，使用ThradLocal相当于复印了教材。

## 1.2 场景2

每个线程内需要保存全局变量（例如在拦截器中获取用户信息），可以让不同方法直接使用，避免参数传递的麻烦

~~~
https://mp.weixin.qq.com/s/LrtaTTz25NIV7EAr9q3BHQ
~~~