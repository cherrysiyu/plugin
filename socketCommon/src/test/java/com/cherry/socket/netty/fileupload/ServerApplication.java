package com.cherry.socket.netty.fileupload;
import com.cherry.socket.netty.server.NettyTcpServer;


public class ServerApplication {
	public static final int serverPort = 8888;
	public static void main(String[] args) throws Exception {
		new NettyTcpServer(serverPort,new FileUploadMessageListener()).startTCPServer();
	}

}
