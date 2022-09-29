package com.mdh.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 聊天室服务端
 *
 * @Author: madonghao
 * @Date: 2022/9/13 4:04 下午
 */
public class NettyChatServer {

	/**
	 * 端口号
	 */
	private int port;

	public NettyChatServer(int port){
		this.port = port;
	}


	private void run() {

		// 1. 创建bossGroup线程组: 处理网络事件--连接事件
		EventLoopGroup bossGroup = null;

		// 2. 创建workerGroup线程组: 处理网络事件--读写事件 2*处理器线程数
		EventLoopGroup workGroup = null;

		try{
			bossGroup = new NioEventLoopGroup(1);
			workGroup = new NioEventLoopGroup();

			//3. 创建服务端启动助手
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			//4. 设置bossGroup线程组和workerGroup线程组
			serverBootstrap.group(bossGroup, workGroup)
					//5. 设置服务端通道 实现为NIO
					.channel(NioServerSocketChannel.class)
					//6. 参数设置
					.option(ChannelOption.SO_BACKLOG, 128)
					//6. 参数设置
					.childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
					//7. 创建一个通道初始化对象
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//8. 向pipeline中添加自定义业务处理handler
							// 添加编解码器
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new StringEncoder());
							// todo
							ch.pipeline().addLast(new NettyChatServerHandler());
						}
					});

		} finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

    public static void main(String[] args) {
	  new NettyChatServer(9998).run();
    }
}
