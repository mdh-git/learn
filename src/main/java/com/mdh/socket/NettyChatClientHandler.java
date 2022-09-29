package com.mdh.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 聊天室处理类
 * @Author: madonghao
 * @Date: 2022/9/13 5:13 下午
 */
public class NettyChatClientHandler extends SimpleChannelInboundHandler<String> {


	/**
	 * 通道读取就绪事件
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
	}
}
