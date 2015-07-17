package com.cherry.socket.mina.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;

import com.cherry.socket.common.listener.MessageListener;
import com.cherry.socket.common.listener.ThreadPoolFactory;
import com.cherry.socket.mina.codec.CodecFactory;
import com.cherry.socket.mina.codec.MessageDecoder;
import com.cherry.socket.mina.codec.MessageEncoder;
import com.cherry.socket.mina.handler.ServerMessageIoHandler;
import com.cherry.socket.mina.heartbeat.HeartBeatKeepAliveFilter;

public class MinaTcpServer {
	
	private ThreadPoolExecutor threadPoolFactory ;
	private final static int cpuSize = Runtime.getRuntime().availableProcessors();
	private int serverPort;// 服务器端口
	private int heartBeatInterval = 20; // 心跳间隔，默认20秒
	private int heartBeatTimeOut = 30; // 心跳超时，默认30秒
	private boolean isNeedheartBeat = true;
	private Map<String, IoFilter> filters = new HashMap<String, IoFilter>();
	private MessageListener serverMessageListener;
	public  MinaTcpServer(int serverPort,MessageListener serverMessageListener,boolean isNeedheartBeat){
		 this(serverPort,cpuSize+1,cpuSize*4,2000,serverMessageListener,isNeedheartBeat);
	}
	public MinaTcpServer(int serverPort,MessageListener serverMessageListener, int heartBeatInterval, int heartBeatTimeOut) {
		this(serverPort,serverMessageListener,true);
		this.heartBeatInterval = heartBeatInterval;
		 this.heartBeatTimeOut = heartBeatTimeOut;
	}
	
	public MinaTcpServer(int serverPort,ThreadPoolExecutor threadPoolFactory,MessageListener serverMessageListener,boolean isNeedheartBeat) {
		super();
		if(threadPoolFactory == null)
			throw new NullPointerException("ThreadPoolExecutor is null!");
		this.threadPoolFactory = threadPoolFactory;
		this.serverPort = serverPort;
		this.isNeedheartBeat = isNeedheartBeat;
		if(serverMessageListener == null)
			throw new NullPointerException("serverMessageListener is null!");
		this.serverMessageListener = serverMessageListener;
	}
	public  MinaTcpServer(int serverPort,int corePoolSize, int maximumPoolSize,int queueSize,MessageListener serverMessageListener,boolean isNeedheartBeat){
		 threadPoolFactory = new ThreadPoolFactory(corePoolSize, maximumPoolSize, 1, TimeUnit.SECONDS, queueSize);
		 this.serverPort = serverPort;
		 this.isNeedheartBeat = isNeedheartBeat;
		 if(serverMessageListener == null)
				throw new NullPointerException("serverMessageListener is null!");
		 this.serverMessageListener = serverMessageListener;
	}
	
	
	

	public  MinaTcpServer(int serverPort,int corePoolSize, int maximumPoolSize,int queueSize,MessageListener serverMessageListener,int heartBeatInterval,int heartBeatTimeOut){
		 this(serverPort,corePoolSize,maximumPoolSize,queueSize,serverMessageListener,true);
		 this.heartBeatInterval = heartBeatInterval;
		 this.heartBeatTimeOut = heartBeatTimeOut;
	}
	
	public MinaTcpServer startTCPServer() throws IOException{
		ProtocolCodecFactory codecFactory = new CodecFactory(new MessageEncoder(), new MessageDecoder());
		IoHandler handler = new ServerMessageIoHandler(threadPoolFactory,serverMessageListener);
		if(isNeedheartBeat){
			filters.put("keep-alive-heartBeat", new HeartBeatKeepAliveFilter(false,heartBeatInterval,heartBeatTimeOut));
		}
		new SocketServer(serverPort, handler, filters,codecFactory).connect();
		return this;
	}

	public void addFilter(String key, IoFilter filter) {
		this.filters.put(key, filter);
	}
	public void setHeartBeatInterval(int heartBeatInterval) {
		this.heartBeatInterval = heartBeatInterval;
	}
	public void setHeartBeatTimeOut(int heartBeatTimeOut) {
		this.heartBeatTimeOut = heartBeatTimeOut;
	}
	
	

}
