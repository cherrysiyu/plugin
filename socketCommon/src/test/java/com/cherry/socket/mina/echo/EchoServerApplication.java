package com.cherry.socket.mina.echo;

import java.io.IOException;

import com.cherry.socket.mina.server.MinaTcpServer;

public class EchoServerApplication {
	public static int PORT= 9999;
	public static void main(String[] args) throws IOException {
		new MinaTcpServer(PORT,2,50,2000,new EchoMessageListener(),60,80).startTCPServer();
	}

}
