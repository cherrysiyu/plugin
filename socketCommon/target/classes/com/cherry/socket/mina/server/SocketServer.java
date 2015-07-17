package com.cherry.socket.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * server端
 * @author:Cherry
 * @date:2014-6-28
 * @version:1.0
 */
public class SocketServer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IoHandler handler;
	private Map<String, IoFilter> filters;
	private ProtocolCodecFactory codecFactory;
	private int serverPort;// 服务器端口
	public SocketServer(int serverPort, IoHandler handler,ProtocolCodecFactory codecFactory) {
		super();
		this.serverPort = serverPort;
		this.handler = handler;
		this.codecFactory = codecFactory;
	}

	public SocketServer(int serverPort, IoHandler handler,Map<String, IoFilter> filters, ProtocolCodecFactory codecFactory) {
		super();
		this.serverPort = serverPort;
		this.handler = handler;
		this.filters = filters;
		this.codecFactory = codecFactory;
	}

	public void connect() throws IOException   {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		SocketSessionConfig config = acceptor.getSessionConfig();
		// config.setKeepAlive(true);
		//config.setReuseAddress(true);
		//config.setTcpNoDelay(true);
		config.setMinReadBufferSize(1024*10);//10k
		config.setMaxReadBufferSize(1024*1024*10);//10M
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		// config.setUseReadOperation(true);
		if(filters != null && !filters.isEmpty()){
			for (String key : filters.keySet())
				acceptor.getFilterChain().addLast(key, filters.get(key));
		}
		acceptor.setHandler(handler);
		InetSocketAddress address = new InetSocketAddress(serverPort);
		acceptor.bind(address);
		logger.info("server start success,serverPort:"+address);
	}

	public void setFilters(Map<String, IoFilter> filters) {
		this.filters = filters;
	}
}
