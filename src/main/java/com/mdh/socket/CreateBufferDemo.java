package com.mdh.socket;

import java.nio.ByteBuffer;

/**
 * 创建缓冲区
 *
 * @Author: madonghao
 * @Date: 2022/9/6 10:14 上午
 */
public class CreateBufferDemo {

	public static void main(String[] args) {
		//1.创建一个指定长度的缓冲区, 以ByteBuffer为例
		ByteBuffer byteBuffer = ByteBuffer.allocate(5);
	    for (int i = 0; i< 5; i++) {
		    System.out.println(byteBuffer.get());
	    }

		//在此调用会报错--后续再读缓冲区时着重讲解
		// System.out.println(byteBuffer.get());
	    //2.创建一个有内容的缓冲区
		ByteBuffer wrap = ByteBuffer.wrap("lagou".getBytes());
		for (int i = 0; i < 5; i++) {
			System.out.println(wrap.get());
		}
	}
}
