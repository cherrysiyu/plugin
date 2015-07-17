package com.cherry.socket.mina;
import java.io.IOException;

import com.cherry.socket.mina.server.MinaTcpServer;


public class ServerApplication {
	public static final int serverPort = 8888;
	public static void main(String[] args) throws IOException {
		new MinaTcpServer(serverPort,2,100,20000,new ServerMessageListener(),60,80).startTCPServer();
	}

}
