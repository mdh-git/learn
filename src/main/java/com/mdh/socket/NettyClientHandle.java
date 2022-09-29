package com.mdh.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: madonghao
 * @Date: 2022/9/8 9:45 上午
 */
public class NettyClientHandle implements ChannelInboundHandler {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

	}

	/**
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("你好呀,我是Netty客户端", CharsetUtil.UTF_8));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg; System.out.println("服务端发来消息:" + byteBuf.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

	}
}
