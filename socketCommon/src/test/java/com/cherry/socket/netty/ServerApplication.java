package com.cherry.socket.netty;
import com.cherry.socket.mina.ServerMessageListener;
import com.cherry.socket.netty.server.NettyTcpServer;


public class ServerApplication {
	public static final int serverPort = 8888;
	public static void main(String[] args) throws Exception {
		new NettyTcpServer(serverPort,new ServerMessageListener()).startTCPServer();
	}

}
