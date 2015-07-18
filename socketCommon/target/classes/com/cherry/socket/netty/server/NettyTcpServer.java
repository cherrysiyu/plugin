package com.cherry.socket.netty.server;

import io.netty.channel.ChannelInboundHandler;

import com.cherry.socket.common.listener.MessageListener;
import com.cherry.socket.netty.handler.ServerMessageHandler;

public final class NettyTcpServer {
	private int serverPort;// 服务器端口
	private MessageListener serverMessageListener;
	private ChannelInboundHandler serverMessageHandler;
	
	public NettyTcpServer(int serverPort,MessageListener serverMessageListener) {
		super();
		this.serverPort = serverPort;
		if(serverMessageListener == null)
			throw new NullPointerException("serverMessageListener is null!");
		this.serverMessageListener = serverMessageListener;
	}
	
	public NettyTcpServer startTCPServer() throws Exception{
		if(this.serverMessageHandler == null)
			this.serverMessageHandler = new ServerMessageHandler(serverMessageListener);
		new SocketServer(serverPort, this.serverMessageHandler).connect();
		return this;
	}
	
	public void setServerMessageHandler(ChannelInboundHandler serverMessageHandler) {
		this.serverMessageHandler = serverMessageHandler;
	}
	
	
	
}
