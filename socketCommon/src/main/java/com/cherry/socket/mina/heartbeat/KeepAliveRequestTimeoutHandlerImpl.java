package com.cherry.socket.mina.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳超时处理Handler
 */
public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter fileter, IoSession s) throws Exception {
		logger.error("心跳超时，关闭session！ip:" + s.getRemoteAddress());
		s.close(true);
	}

}
