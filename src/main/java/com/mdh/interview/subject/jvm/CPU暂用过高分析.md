# CPUz占用过高定位分析
~~~
1. 先用top命令查找出CPU占比最高的
2. ps -ef 或者 jps 进一步定位
    jps -l 查看程序和进程号
    ps -ef|grep java|grep -v grep(5101  4063 36 08:25 pts/2   00:11:25 java com.mdh.test.JavaDeemo)
3. 定位到具体线程或者代码
    ps -mp 进程编号 -o THREAD,tid,time
        -m 显示所有的线程
        -p pid 进程使用cpu的时间
        -o 改参数后是用户自定义格式
4. 将需要的线程ID转化为16进制格式(英文小写格式)
    print "%x\n" 有问题的线程ID
5. jstack 进程ID|grep tid(16进制线程ID小写英文) -A60
    打印出前60行线程的信息

~~~

# JDK自带的工具
~~~
jps(虚拟机进程状态工具)

jinfo(java配置信息工具)

jmap(内存映象工具)
    1. 映射堆快照 
        jmap -heap 进程ID
    2. 抓堆内存快照 
        生成hprof文件并下载到本地
        MAT分析插件工具

jstat(统计信息监控工具)
    类加载统计
    编译统计
    垃圾回收统计

jstack(堆栈异常跟踪工具)

jvisualvm

jconsole
~~~