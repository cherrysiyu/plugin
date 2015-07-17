package com.cherry.socket.mina.echo;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.manager.MessageResponse;
import com.cherry.socket.mina.client.MinaTcpClient;

public class EchoClientApplication {
	private static final Logger logger = LoggerFactory.getLogger(EchoClientApplication.class);
	
	public static void main(String[] args) throws InterruptedException {
		MinaTcpClient minaTcpClient = new MinaTcpClient(1433, "127.0.0.1", EchoServerApplication.PORT,3000,60,80).startClient();
		while(true){
			MessageResponse sendMessage = minaTcpClient.sendMessage(new String("hello,你好").getBytes());
			logger.info("客户端收到回应:"+sendMessage.getStatus()+"消息:"+new String(sendMessage.getMsgBody()));
			TimeUnit.SECONDS.sleep(85);
		}
	}

}
