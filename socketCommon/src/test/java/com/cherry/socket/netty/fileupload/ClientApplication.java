package com.cherry.socket.netty.fileupload;

import io.netty.channel.Channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.StatusEnum;
import com.cherry.socket.common.manager.MessageResponse;
import com.cherry.socket.netty.client.NettyTcpClient;
import com.cherry.socket.utils.ByteBufferWriter;

public class ClientApplication {
	private static final Logger logger = LoggerFactory
			.getLogger(ClientApplication.class);
	public static final AtomicLong count = new AtomicLong();
	public static final AtomicLong timeoutCount = new AtomicLong();
	public static final NettyTcpClient tcpClient1;
	public static final String filePath = "D:/workspace/";
	public static final String destFilePath = "C:/temp";
	public static List<byte[]> fileList = new ArrayList<byte[]>();
	static {
		tcpClient1 = new NettyTcpClient(1411, "127.0.0.1", ServerApplication.serverPort,3000,60).startClient();
		try {
			TimeUnit.SECONDS.sleep(30);
			Channel channel = tcpClient1.getChannel();
			int count = 0;
			while ((channel == null || !channel.isActive()) && count < 5) {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
			}
			if (channel != null) {
				File file = new File(filePath);
				if (file.exists()) {
					addFile(file);
				} else {
					System.out.println("文件不存在");
				}

			} else {
				System.out.println("初始化连接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		TimeUnit.MINUTES.sleep(1);
		System.out.println(timeoutCount);
		System.out.println(count);
	}

	private static void addFile(File file) throws FileNotFoundException,
			IOException {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFile(f);
			}
		} else {
			String filePath = destFilePath + "/" + file.getName();
			byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
			ByteBufferWriter bbw = new ByteBufferWriter(byteArray.length + filePath.length()+4);
			bbw.writeIntLengthString(filePath);
			bbw.writeBytes(byteArray);
			MessageResponse sendMessage = tcpClient1.sendMessage(bbw.toByteArray());
			if (sendMessage.getStatus() == StatusEnum.TIMEOUT) {
				logger.error("超时了 ------" + timeoutCount.incrementAndGet()+file);
			} else if (sendMessage.getStatus() == StatusEnum.CORRECT) {
				logger.info(new String(sendMessage.getMsgBody()) + "-------"
						+ count.incrementAndGet());
			}
			
		}
	}

}
