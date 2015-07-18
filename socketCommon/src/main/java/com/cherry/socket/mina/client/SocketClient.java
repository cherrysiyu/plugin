package com.cherry.socket.mina.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.MinaSessionAttribute;

/**
 * @author Cherry
 * @version 0.1
 * @Desc mina实现client端
 * 2014年7月11日 下午10:16:23
 */
public class SocketClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String serverIP;
	private int serverPort;
	private int clientAppNumber; // 每一个session长连接对应的标识，全局唯一
	private ProtocolCodecFactory codecFactory;
	private IoHandler handler;
	private Map<String, IoFilter> filters;
	private IoConnector connector;
	private InetSocketAddress socketAddress;
	private IoSession session;
	
	public SocketClient(String serverIP, int serverPort,int clientAppNumber,ProtocolCodecFactory codecFactory, IoHandler handler,Map<String, IoFilter> filters) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientAppNumber = clientAppNumber;
		this.codecFactory = codecFactory;
		this.handler = handler;
		this.filters = filters;
		init();
	}

	/**
	 * 初始化
	 * @throws IOException
	 */
	private void init() {
		if(IoSessionMapping.isContainSession(clientAppNumber))
				throw new IllegalArgumentException("clientAppNumber is exist,please init other clientAppNumber,exist clientAppNumber= "+clientAppNumber);
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.getSessionConfig().setMinReadBufferSize(1024*10);//10k
		connector.getSessionConfig().setMaxReadBufferSize(1024*1024*10);//10M
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		if(filters != null && !filters.isEmpty()){
			for (String key : filters.keySet())
				connector.getFilterChain().addLast(key, filters.get(key));
		}
		connector.setHandler(handler);
		socketAddress = new InetSocketAddress(serverIP, serverPort);
		IoSessionMapping.addSocketClient(this);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接服务器
	 * @return
	 * @throws IOException
	 */
	public synchronized IoSession connect() {
		if(session == null || !session.isConnected()){
			ConnectFuture future = connector.connect(socketAddress); // 创建连接
			logger.debug("start connect:" + socketAddress.toString());
			future.awaitUninterruptibly();
			if (future.isConnected()) {
				session = future.getSession();
				logger.info("connect server success!address:" + socketAddress.toString());
				MinaSessionAttribute attr = new MinaSessionAttribute(session.getId(),clientAppNumber);
				session.setAttribute(Constans.SESSIONATTR_KEY, attr);
			} else
				logger.error("connect server failed！address:" + socketAddress.toString());
		}
		return session;
	}

	public void setFilters(Map<String, IoFilter> filters) {
		this.filters = filters;
	}

	public int getClientAppNumber() {
		return clientAppNumber;
	}

	public IoSession getSession() {
		return session;
	}
	
}
