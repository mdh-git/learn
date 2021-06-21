package com.mdh.interview.subject.synchronizedCode;

import org.openjdk.jol.info.ClassLayout;


/**
 * https://www.cnblogs.com/LemonFive/p/11246086.html
 *
 * @Author: madonghao
 * @Date: 2021/3/19 11:19 上午
 */
public class ObjectHeaderCode {
    public static void main(String[] args) throws InterruptedException {

        //noLock();
        biasLock();
    }

    /**
     * 使用jol工具类输出A对象的对象头
     * 输出的第一行内容和锁状态内容对应
     * unused:1 | age:4 | biased_lock:1 | lock:2 0 0000 0 01
     * 代表对象正处于无锁状态
     *
     * 第三行中表示的是被指针压缩为32位的klass pointer
     * 第四行则是我们创建的A对象属性信息 1字节的boolean值
     * 第五行则代表了对象的对齐字段 为了凑齐64位的对象，对齐字段占用了3个字节，24bit
     *
     */
    static void noLock() {
        TestObject object = new TestObject();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }

    /**
     * 偏向锁
     */
    static void biasLock() throws InterruptedException {
        Thread.sleep(5000);
        TestObject object = new TestObject();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }

}
