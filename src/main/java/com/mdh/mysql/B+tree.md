# B+tree
~~~
SHOW GLOBAL STATUS like 'Innodb_page_size';
16384 = 16k

非叶子节点不存储data,只存储索引(冗余),可以放更多的索引
叶子节点包含所有索引字段
叶子节点用指针连接,提高区间访问的性能


索引8bit 地址指针6bit

范围查找(< 或 >)使用B+Tree(叶子节点data里面有的双向指针)更简单, 而B-Tree不容易实现
~~~

## MyISAM(非聚集)
~~~
MyISAM索引文件和数据文件是分离的(非聚集)
.frm 表结构的信息
.MYD 表数据行的记录
.MYI 表的索引字段

MyISAM的索引的叶子节点data存放的是实际行的地址
~~~

## InnoDB(聚集)
~~~
表数据文件本身就是按照B+Tree组织的一个索引结构文件
聚集索引-叶子节点包含了完整的数据记录
InnoDB表必须要有主键,并且推荐使用整型的自增主键
非主键索引结构叶子节点存放的是主键值(一致性和节约存储空间)
.frm 表结构的信息
.idb 数据文件和索引
~~~