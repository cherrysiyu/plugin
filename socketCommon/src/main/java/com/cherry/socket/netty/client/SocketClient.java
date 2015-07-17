package com.cherry.socket.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.netty.common.ChannelMapping;
import com.cherry.socket.netty.handler.ClientInitializer;

/**
 * 
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年9月20日 下午9:40:03
 */
public final class SocketClient {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String serverIP;
	private int serverPort;
	private int clientAppNumber; // 每一个session长连接对应的标识，全局唯一
	private Bootstrap bootstrap;
	private Channel channel;
	private InetSocketAddress socketAddress;
	private ChannelInboundHandler clientMessageHandler;
	private Map<String, ChannelHandler> filters;
	public SocketClient(String serverIP, int serverPort,int clientAppNumber, ChannelInboundHandler clientMessageHandler) {
		this(serverIP, serverPort, clientAppNumber, clientMessageHandler, null);
	}
	public SocketClient(String serverIP, int serverPort,int clientAppNumber, ChannelInboundHandler clientMessageHandler,Map<String, ChannelHandler> filters) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.clientMessageHandler = clientMessageHandler;
		init();
	}

	/**
	 * 初始化
	 * @throws IOException
	 */
	private void init() {
		 if(ChannelMapping.isContainChannel(clientAppNumber))
				throw new IllegalArgumentException("clientAppNumber is exist,please init other clientAppNumber,exist clientAppNumber= "+clientAppNumber);
		 socketAddress = new InetSocketAddress(serverIP, serverPort);
		 bootstrap = new Bootstrap(); 
		 bootstrap.group(new NioEventLoopGroup(1));
		 bootstrap.channel(NioSocketChannel.class);   
		 bootstrap.option(ChannelOption.TCP_NODELAY, true);  
		 bootstrap.option(ChannelOption.SO_RCVBUF, 256*1024);
		 bootstrap.option(ChannelOption.SO_SNDBUF, 256*1024);  
		 bootstrap.remoteAddress(socketAddress);
		 bootstrap.handler(new ClientInitializer(clientMessageHandler,filters));
		 
		ChannelMapping.addSocketClient(this);
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized Channel connect() {
		if (channel == null || !channel.isActive()) {
			logger.info("start connect:" + socketAddress.toString());
			try {
				ChannelFuture channelFuture = bootstrap.connect().sync().awaitUninterruptibly();
				if (channelFuture.isDone() && channelFuture.isSuccess()) {
					channel = channelFuture.channel();
					logger.info("connect server success!address:" + socketAddress.toString());
				}else{
					logger.error("Failed to connect: " + channelFuture.cause());
				}
			} catch (InterruptedException e) {
				logger.error("Failed to connect: " + e);
			}
		}
		return channel;
	}
	
	public void close(){
		if (null != channel) {
			bootstrap.group().shutdownGracefully();
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
	}

	public int getClientAppNumber() {
		return clientAppNumber;
	}

	public Channel getChannel() {
		return channel;
	}

	public Bootstrap getBootstrap() {
		return bootstrap;
	}
	
	
}
