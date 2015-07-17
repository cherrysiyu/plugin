package com.cherry.socket.netty.echo;

import com.cherry.socket.mina.echo.EchoMessageListener;
import com.cherry.socket.netty.server.NettyTcpServer;

public class EchoServerApplication {
	public static int PORT= 9999;
	public static void main(String[] args) throws Exception {
		new NettyTcpServer(PORT,new EchoMessageListener()).startTCPServer();
	}
}
