package com.cherry.socket.netty.common;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.netty.client.SocketClient;

/**
 * session管理类，以及session定时检测
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年7月12日 下午7:41:09
 */
public final class ChannelMapping {
	private static final Logger logger = LoggerFactory.getLogger(ChannelMapping.class);
	private static final ConcurrentMap<Integer, SocketClient> clients = new ConcurrentHashMap<Integer, SocketClient>();
	private static final ConcurrentMap<Integer, Channel> clientChannels = new ConcurrentHashMap<Integer, Channel>();
	private static int checkInterval = 20; // session检测间隔时间，秒
	
	static{
		 Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new CheckChannel(), checkInterval, checkInterval, TimeUnit.SECONDS);
	}
	
	
	public static boolean isContainChannel(int appNumber){
		return clientChannels.containsKey(appNumber);
	}
	
	public static Channel getChannel(int appNumber) {
		if (!isContainChannel(appNumber))
			return null;
		return clientChannels.get(appNumber);
	}
	
	public synchronized static boolean addSocketClient(SocketClient client){
		if(clients.containsKey(client.getClientAppNumber())){
			if(client.getChannel() != null && client.getChannel().isActive()){
				clients.put(client.getClientAppNumber(), client);
				//立即建立连接
				new Thread(new CheckChannel()).start();
				return true;
			}else{
				return false;
			}
		}else{
			//立即建立连接
			clients.put(client.getClientAppNumber(), client);
			new Thread(new CheckChannel()).start();
			return true;
		}
	}
	
	private static void addClientChannel(int appNumber, Channel session) {
		Channel old = clientChannels.get(appNumber);
		if (old != null && old.isActive()) {
			old.close();
		}
		clientChannels.put(appNumber, session);
	}
	/**
	 * session检测线程
	 * @author:Cherry
	 * @date:2014-6-28
	 * @version:1.0
	 */
	private static   class CheckChannel extends TimerTask {
		@Override
		public void run() {
			try {
				for (Map.Entry<Integer, SocketClient> entry : clients.entrySet()) {
					Channel channel = getChannel(entry.getKey());
					if (channel == null || !channel.isActive()) {
						logger.error("客户端:"+entry.getKey()+"不在线，建立链接");
						Channel newChannel = entry.getValue().connect();
						if (newChannel != null && newChannel.isActive()){
							logger.info("客户端:"+entry.getKey()+"建立链接成功");
							addClientChannel(entry.getKey(), newChannel);
						}
					}
				}
			} catch (Exception e) {
				logger.error("CheckChannel", e);
			}
		}
	}

}
