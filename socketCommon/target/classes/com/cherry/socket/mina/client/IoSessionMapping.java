package com.cherry.socket.mina.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.MinaSessionAttribute;

/**
 * session管理类，以及session定时检测
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年7月12日 下午7:41:09
 */
public final class IoSessionMapping {
	private static final Logger logger = LoggerFactory.getLogger(IoSessionMapping.class);
	private static final ConcurrentMap<Integer, SocketClient> clients = new ConcurrentHashMap<Integer, SocketClient>();
	private static final ConcurrentMap<Integer, IoSession> clientSessions = new ConcurrentHashMap<Integer, IoSession>();
	private static int checkInterval = 20; // session检测间隔时间，秒
	
	static{
		 Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new CheckSession(), checkInterval, checkInterval, TimeUnit.SECONDS);
	}
	
	
	public static boolean isContainSession(int appNumber){
		return clientSessions.containsKey(appNumber);
	}
	
	public static IoSession getSession(int appNumber) {
		if (!isContainSession(appNumber))
			return null;
		return clientSessions.get(appNumber);
	}
	
	public synchronized static boolean addSocketClient(SocketClient client){
		if(clients.containsKey(client.getClientAppNumber())){
			if(client.getSession() != null && client.getSession().isConnected()){
				clients.put(client.getClientAppNumber(), client);
				//立即连接
				new Thread(new CheckSession()).start();
				return true;
			}else{
				return false;
			}
		}else{
			clients.put(client.getClientAppNumber(), client);
			//立即连接
			new Thread(new CheckSession()).start();
			return true;
		}
	}
	
	public synchronized static void removeSession(IoSession session) {
		if(session != null){
			MinaSessionAttribute attr = (MinaSessionAttribute)session.getAttribute(Constans.SESSIONATTR_KEY);
			if (attr != null && session.getId() == attr.getSessionId()) {
				clientSessions.remove(attr.getAppNumber());
				logger.info("remove session from sessionmapping,ip:" + session.getRemoteAddress().toString());
			}
		}
	}
	
	private static void addClientSession(int appNumber, IoSession session) {
		IoSession old = clientSessions.get(appNumber);
		if (old != null && !old.isClosing()) {
			old.close(true);
		}
		clientSessions.put(appNumber, session);
	}
	
	/**
	 * 获取所有session的附加属性
	 * 
	 * @return
	 */
	public static List<MinaSessionAttribute> getAllSessionAttribute() {
		List<MinaSessionAttribute> sas = new ArrayList<MinaSessionAttribute>();
		for (SocketClient client : clients.values()) {
			if (client.getSession() != null && client.getSession().isConnected())
				sas.add((MinaSessionAttribute) client.getSession().getAttribute(Constans.SESSIONATTR_KEY));
		}
		return sas;
	}
	
	public static void setCheckInterval(int checkInterval) {
		IoSessionMapping.checkInterval = checkInterval;
	}
	/**
	 * session检测线程
	 * @author:Cherry
	 * @date:2014-6-28
	 * @version:1.0
	 */
	private static   class CheckSession extends TimerTask {
		@Override
		public void run() {
			try {
				for (Map.Entry<Integer, SocketClient> entry : clients.entrySet()) {
					IoSession session = getSession(entry.getKey());
					if (session == null || !session.isConnected()) {
						logger.error("客户端:"+entry.getKey()+"不在线，建立链接");
						IoSession newSession = entry.getValue().connect();
						if (newSession != null){
							logger.info("客户端:"+entry.getKey()+"建立链接成功");
							addClientSession(entry.getKey(), newSession);
						}
					}
				}
			} catch (Exception e) {
				logger.error("CheckSession", e);
			}
		}
	}

}
