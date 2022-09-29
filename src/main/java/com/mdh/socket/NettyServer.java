package com.mdh.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 服务端
 *
 * 服务端实现步骤:
 * 1. 创建bossGroup线程组: 处理网络事件--连接事件
 * 2. 创建workerGroup线程组: 处理网络事件--读写事件
 * 3. 创建服务端启动助手
 * 4. 设置bossGroup线程组和workerGroup线程组
 * 5. 设置服务端通道实现为NIO
 * 6. 参数设置
 * 7. 创建一个通道初始化对象
 * 8. 向pipeline中添加自定义业务处理handler
 * 9. 启动服务端并绑定端口,同时将异步改为同步
 * 10. 关闭通道和关闭连接池
 * 
 * @Author: madonghao
 * @Date:  2022/9/7 11:25 上午
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

	    // 1.创建bossGroup线程组: 处理网络事件--连接事件 线程数默认为: 2 * 处理器线程数
	    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

	    // 2.创建workerGroup线程组: 处理网络事件--读写事件 2 * 处理器线程数
	    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

	    // 3.创建服务端启动助手
	    ServerBootstrap bootstrap = new ServerBootstrap();

	    //
	    bootstrap.group(bossGroup, workerGroup)
			    //5.设置服务端通道实现;
			    .channel(NioServerSocketChannel.class)
			    //6.参数设置-设置线程队列中等待 连接个数
			    .option(ChannelOption.SO_BACKLOG, 128)
			    //7.参数设 置-设置活跃状态,child是设置workerGroup
			    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
			    .childHandler(new ChannelInitializer<SocketChannel>() {

				    //8.创建一 个通道初始化对象
				    @Override
				    protected void initChannel(SocketChannel ch) throws Exception {
					    //9.向pipeline中添加自定义业务处理handler
					    ch.pipeline().addLast(new NettyServerHandle());
				    }
			    });

	    //10.启动服务端并绑定端口,同时将异步改为同步
	    ChannelFuture future = bootstrap.bind(9999).sync();
	    System.out.println("服务器启动成功....");

	    //11.关闭通道(并不是真正意义上的关闭,而是监听通道关闭状态)和关闭连接池
	    future.channel().closeFuture().sync();
	    bossGroup.shutdownGracefully();
	    workerGroup.shutdownGracefully();
    }
}
