package com.cherry.socket.mina.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.bean.StatusEnum;
import com.cherry.socket.common.manager.DataManager;
import com.cherry.socket.common.manager.MessageResponse;
import com.cherry.socket.common.manager.Response;
import com.cherry.socket.mina.codec.CodecFactory;
import com.cherry.socket.mina.codec.MessageDecoder;
import com.cherry.socket.mina.codec.MessageEncoder;
import com.cherry.socket.mina.handler.ClientMessageIoHandler;
import com.cherry.socket.mina.heartbeat.HeartBeatKeepAliveFilter;


public class MinaTcpClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String serverIP;
	private int serverPort;
	private int appNumber; // 每一个session长连接对应的编号，全局唯一 
	private long  timeout = 3000;
	private Map<String, IoFilter> filters = new HashMap<String, IoFilter>();
	private int heartBeatInterval = 20; // 心跳间隔，默认20秒
	private int heartBeatTimeOut = 30; // 心跳超时，默认30秒
	private boolean isNeedheartBeat = true;
	
	public MinaTcpClient(int appNumber, String serverIP, int serverPort,boolean isNeedheartBeat) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.appNumber = appNumber;
		this.isNeedheartBeat = isNeedheartBeat;
	}
	public MinaTcpClient(int appNumber, String serverIP, int serverPort,long  timeout,boolean isNeedheartBeat) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.appNumber = appNumber;
		this.isNeedheartBeat = isNeedheartBeat;
		this.timeout = timeout;
	}

	public MinaTcpClient(int appNumber,String serverIP, int serverPort,int heartBeatInterval, int heartBeatTimeOut) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.appNumber = appNumber;
		this.heartBeatInterval = heartBeatInterval;
		this.heartBeatTimeOut = heartBeatTimeOut;
	}
	public MinaTcpClient(int appNumber,String serverIP, int serverPort,long  timeout,int heartBeatInterval, int heartBeatTimeOut) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.appNumber = appNumber;
		this.heartBeatInterval = heartBeatInterval;
		this.heartBeatTimeOut = heartBeatTimeOut;
		this.timeout = timeout;
	}
	/**
	 * 发送消息
	 * @param frameBody
	 * @return
	 */
	public  MessageResponse sendMessage(byte[] frameBody){
		long currentTimeMillis = System.currentTimeMillis();
		IoSession ioSession = IoSessionMapping.getSession(appNumber);
		MessageResponse message = new MessageResponse();
		if(ioSession == null){
			logger.error(appNumber+" 对应的客户端没有连上服务端");
			message.setStatus(StatusEnum.NOTONLINE);
			return message;
		}
		SocketFrame frame = new SocketFrame(appNumber,frameBody);
		frame.buildFrameBytes();
		Response response = new Response(timeout);
		DataManager.addMessage(frame.getFrameNo(),response);
		ioSession.write(frame);
		SocketFrame result = DataManager.getMessage(frame.getFrameNo());
		if(result == null){
			message.setStatus(StatusEnum.TIMEOUT);
			logger.error("超时FrameNo:"+frame.getFrameNo() +" beginTime:"+currentTimeMillis +" endTime:"+System.currentTimeMillis()+" cost:"+(System.currentTimeMillis()-currentTimeMillis));
		}else
			message.setMsgBody(result.getFrameBody());
		return message;
	}
	
	

	public MinaTcpClient startClient() {
		ProtocolCodecFactory codecFactory = new CodecFactory(new MessageEncoder(), new MessageDecoder());
		IoHandler handler = new ClientMessageIoHandler();
		if(isNeedheartBeat){
			filters.put("keep-alive-heartBeat", new HeartBeatKeepAliveFilter(true,heartBeatInterval,heartBeatTimeOut));
		}
		new SocketClient(serverIP, serverPort, appNumber,codecFactory, handler,filters);
		return this;
	}
	public int getHeartBeatInterval() {
		return heartBeatInterval;
	}
	public void setHeartBeatInterval(int heartBeatInterval) {
		this.heartBeatInterval = heartBeatInterval;
	}
	public int getHeartBeatTimeOut() {
		return heartBeatTimeOut;
	}
	public void setHeartBeatTimeOut(int heartBeatTimeOut) {
		this.heartBeatTimeOut = heartBeatTimeOut;
	}
	public void addFilters(String key,IoFilter filter) {
		this.filters.put(key, filter);
	}
	public  long getTimeout() {
		return this.timeout;
	}

	public  void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
