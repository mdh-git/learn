String类型是二进制安全的
字符串最多512M

赋值  set key value
> set test 123
OK


取值 get key
> get test
"123"


取值并赋值 getset key value
>getset test 321
"123"
>getset test
"321"


设置获取多个键值 mset key value [key value...] mget key[key...]
对应的 mget  k1 k2 k3 查看多个key
>mset k1 v1 k2 v2 k3 v3
OK
>mget k1 k2
1) "v1"
2) "v2"



删除 del key
> del test
(integer) 1

数值增减
·递增数字 当存储的字符串是整数时，Redis提供了一个实用的命令INCR,其作用是当前值递增,返回递增后的值。
 语法: incr key
> set num 1
OK
> incr num
(integer) 2
> incr num
(integer) 3
> incr num
(integer) 4
·增加指定的整数 incrby key increment
> incrby num 2
(integer) 6
> incrby num 2
(integer) 8
> incrby num 2
(integer) 10
·递减数值 decr key
> decr num
(integer) 9
> decr num
(integer) 8
·减少指定的数值 decrby key increment
> decrby num 2
(integer) 6
> decrby num 2
(integer) 4


向尾部追加APPEND的作用是向键值的尾部加value。如果键不存在则将该键的值设置为value，即相当于SET　key value。返回值是追加后的
字符串的总长度。语法: append key value
> set str hello
OK
> append str "world"
(interger) 10
> get str
"helloworld"


获取字符串长度STRLEN命令返回键值的长度，如果键值不存在则返回0。
语法: strlen key
> strlen str
(integer) 10


getrange key1 0 3
获取key字符串的第0位到第3位

setrange key 0 XXX
从第0位修改key字符串的值


setex key 10 v4
设置key的值为v4，并设置存活时间为10秒


setnx（set if not exist） key v11
当key的值不存在的时候，才设置值v11

msetnx k1 v1 k2 v2
当其中有一个k存在保存就失败，否则成功


应用:自增主键 商品编号、订单号采用string的递增数字特性生成

## String底层设计
~~~
底层实现是简单动态字符串sds(simple dynamic string)，是可以修改的字符串。
redis版本在3.2之前 embstr是39个字节  之后改成44个字节

即3.2之后
<= 44  使用 embstr
> 44   使用 raw
整数    使用 int

当设置hello时

已用长度6
可用长度8
数据  h   e   l   l   o   \0     空    空

如果传统字符串保存图片，视频等二进制文件，中间可能出现'\0'，如果按照原来的逻辑，会造成数据丢失。所以可以用已用长度来表示是否字符数组已结束。

String类型的删除并不是直接回收内存，而是修改字符，让其为空字符，这其实是惰性释放，等待将来使用。
~~~