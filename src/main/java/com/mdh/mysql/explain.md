##  explain用法
~~~

列名	                                      描述
id	                       表关联的序号，每个 SELECT 对应一个序号（MySQL将多个语句改写为 join 除外）
select_type	                                SELECT 查询的类型
table	                                     查询的表名
type	                               join类型，表示是如何找到表中的记录的
possible_keys	                           本次查询中可能用选用的索引
key	                                    本次查询中用到的索引，NULL 的话说明没有用索引
key_len	                             所选择的索引长度有多少字节（只使用了联合索引中的部分时显示）
ref	                                       哪些列或常量被用于查找索引列上的值
rows	                                 本次查询中一共扫描了多少行数据（预估值）
Extra	                                       关于本次查询额外的信息



possible key ：当前sql可能会使用到的索引

key ：当前sql实际命中的索引
key len ：索引占用的大小
通过它们两个查看是否可能会命中索引

type这条sql的链接类型，性能由好到差为NULL、system、const、eg ref、ref、range、index、all.
system:查询系统中的表
const:根据主键查询
eq_ref:主键索引查询或唯一索引查询
ref:索引查询
range:范围查询 （优化到最少是这个性能的）
index:索引树扫描
all:全盘扫描


Extra 额外的优化建议
Using where; Using Index      查找使用了索引，需要的数据都在索引列中能找到，不需要回表查询数据
Using index condition         查找使用了索引，但是需要回表查询数据

~~~
