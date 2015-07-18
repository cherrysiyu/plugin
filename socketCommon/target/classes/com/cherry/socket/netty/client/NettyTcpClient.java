package com.cherry.socket.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.bean.StatusEnum;
import com.cherry.socket.common.manager.DataManager;
import com.cherry.socket.common.manager.MessageResponse;
import com.cherry.socket.common.manager.Response;
import com.cherry.socket.netty.common.ChannelMapping;
import com.cherry.socket.netty.handler.ClientMessageHandler;

/**
 * 
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年9月20日 下午9:39:57
 */
public class NettyTcpClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String serverIP;
	private int serverPort;
	private int clientAppNumber; // 每一个session长连接对应的标识，全局唯一 
	private long  timeout = 3000;
	private int heartBeatInterval = 20; // 心跳间隔，默认20秒
	private boolean isNeedheartBeat = true;
	private Map<String, ChannelHandler> filters = new HashMap<String, ChannelHandler>();
	private ChannelInboundHandler clientMessageHandler;
	
	public NettyTcpClient(int clientAppNumber, String serverIP, int serverPort,boolean isNeedheartBeat) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.isNeedheartBeat = isNeedheartBeat;
	}
	public NettyTcpClient(int clientAppNumber, String serverIP, int serverPort,long  timeout,boolean isNeedheartBeat) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.isNeedheartBeat = isNeedheartBeat;
		this.timeout = timeout;
	}

	public NettyTcpClient(int clientAppNumber,String serverIP, int serverPort,int heartBeatInterval) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.heartBeatInterval = heartBeatInterval;
	}
	public NettyTcpClient(int clientAppNumber,String serverIP, int serverPort,long  timeout,int heartBeatInterval) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.heartBeatInterval = heartBeatInterval;
		this.timeout = timeout;
	}
	/**
	 * 发送消息
	 * @param frameBody
	 * @return
	 */
	public  MessageResponse sendMessage(byte[] frameBody){
		long currentTimeMillis = System.currentTimeMillis();
		Channel channel = ChannelMapping.getChannel(clientAppNumber);
		MessageResponse message = new MessageResponse();
		if(channel == null){
			logger.error(clientAppNumber+" 对应的客户端没有连上服务端");
			message.setStatus(StatusEnum.NOTONLINE);
			return message;
		}
		SocketFrame frame = new SocketFrame(clientAppNumber,frameBody);
		frame.buildFrameBytes();
		Response response = new Response(timeout);
		DataManager.addMessage(frame.getFrameNo(),response);
		channel.writeAndFlush(frame);
		SocketFrame result = DataManager.getMessage(frame.getFrameNo());
		if(result == null){
			message.setStatus(StatusEnum.TIMEOUT);
			logger.error("超时FrameNo:"+frame.getFrameNo() +" beginTime:"+currentTimeMillis +" endTime:"+System.currentTimeMillis()+" cost:"+(System.currentTimeMillis()-currentTimeMillis)+"ms");
		}else
			message.setMsgBody(result.getFrameBody());
		return message;
	}
	
	public Channel getChannel(){
		return ChannelMapping.getChannel(clientAppNumber);
	}

	public NettyTcpClient startClient() {
		if(this.clientMessageHandler == null)
			this.clientMessageHandler = new ClientMessageHandler();
		if(isNeedheartBeat){
			filters.put("keep-alive-heartBeat",new IdleStateHandler(0,0,heartBeatInterval,TimeUnit.SECONDS));
			new SocketClient(serverIP, serverPort, clientAppNumber,clientMessageHandler,filters);
		}else
			new SocketClient(serverIP, serverPort, clientAppNumber,clientMessageHandler);
		return this;
	}
	
	public int getHeartBeatInterval() {
		return heartBeatInterval;
	}
	public void setHeartBeatInterval(int heartBeatInterval) {
		this.heartBeatInterval = heartBeatInterval;
	}
	public  long getTimeout() {
		return this.timeout;
	}

	public  void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public void setClientMessageHandler(ChannelInboundHandler clientMessageHandler) {
		this.clientMessageHandler = clientMessageHandler;
	}
	
}
