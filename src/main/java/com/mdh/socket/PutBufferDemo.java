package com.mdh.socket;

import java.nio.ByteBuffer;

/**
 * 添加缓冲区
 * @Author: madonghao
 * @Date: 2022/9/6 10:21 上午
 */
public class PutBufferDemo {

	public static void main(String[] args) {

		//1.创建一个指定长度的缓冲区, 以ByteBuffer为例
		ByteBuffer buffer = ByteBuffer.allocate(10);

		//0 获取当前索引所在位置
		System.out.println(buffer.position());
		//10 最多能操作到哪个索引
		System.out.println(buffer.limit());
		//10 返回缓冲区总长度
		System.out.println(buffer.capacity());
		//10 还有多少个能操作
		System.out.println(buffer.remaining());


		// 修改当前索引位置
		// buffer.position(1);
		// 修改最多能操作到那个索引位置
		// buffer.limit(9);
        // 1 获取当前索引所在位置
        // System.out.println(buffer.position());
        // 9 最多能操作到那个索引
		// System.out.println(buffer.limit());
		// 10 返回缓冲区总长度
		// System.out.println(buffer.capacity());
		// 8 还有多少个能操作
		//System.out.println(buffer.remaining());

		// 添加一个字节
		buffer.put((byte) 97);
		// 1 获取当前索引所在位置
		System.out.println(buffer.position());
		// 10 最多能操作到那个索引
		System.out.println(buffer.limit());
		// 10 返回缓冲区总长度
		System.out.println(buffer.capacity());
		// 9 还有多少个能操作
		System.out.println(buffer.remaining());

		// 添加一个字节数组
		buffer.put("abc".getBytes());
		// 4 获取当前索引所在位置
		System.out.println(buffer.position());
		// 10 最多能操作到那个索引
		System.out.println(buffer.limit());
		// 10 返回缓冲区总长度
		System.out.println(buffer.capacity());
		// 6 还有多少个能操作
		System.out.println(buffer.remaining());

		//当添加超过缓冲区的长度时会报错
		buffer.put("012345".getBytes());
		// 10 获取当前索引所在位置
		System.out.println(buffer.position());
		// 10 最多能操作到那个索引
		System.out.println(buffer.limit());
		// 10 返回缓冲区总长度
		System.out.println(buffer.capacity());
		// 0 还有多少个能操作
		System.out.println(buffer.remaining());
		// false 是否还能有操作的数组
		System.out.println(buffer.hasRemaining());

		// 如果缓存区存满后, 可以调整position位置可以重复写,这样会覆盖之前存入索引的对应的值
		buffer.position(0);
		buffer.put("012345".getBytes());
		// 4 还有多少个能操作
		System.out.println(buffer.remaining());
	}

}
