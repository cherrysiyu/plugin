package com.cherry.socket.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.netty.handler.ServerInitializer;

public final class SocketServer {
	private static final int cpuSize = Runtime.getRuntime().availableProcessors();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private int serverPort;// 服务器端口
	//处理业务逻辑类
	private ChannelInboundHandler serverMessageHandler;
	
	private boolean isWindows = false;
	public SocketServer(int serverPort,ChannelInboundHandler serverMessageHandler) {
		super();
		if (serverPort < 1000)
			throw new IllegalArgumentException("serverPort must >1000 !!!");
		this.serverPort = serverPort;
		if (serverMessageHandler == null)
			throw new IllegalArgumentException("serverMessageHandler is null !!!");
		this.serverMessageHandler = serverMessageHandler;
		if(System.getProperty("os.name").startsWith("win")||System.getProperty("os.name").startsWith("Win")){
			isWindows = true;
		}
	}

	public void connect() throws Exception {
		// Configure the server.
		int threadSize = cpuSize+1;
		if(isWindows){
			threadSize = cpuSize;
		}
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(threadSize);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
					.option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ServerInitializer(serverMessageHandler));
			// Start the server.
			ChannelFuture f = b.bind(serverPort).syncUninterruptibly();
			logger.info("netty server start success,serverPort:"+serverPort);
			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
