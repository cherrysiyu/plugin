package com.cherry.socket.mina.heartbeat;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

public class HeartBeatKeepAliveFilter extends  KeepAliveFilter{
	
    public HeartBeatKeepAliveFilter(boolean isClient,int heartBeatInterval, int heartBeatTimeOut) {
		super (isClient?new ClientKeepAliveMessageFactoryImpl():new ServerKeepAliveMessageFactoryImpl(), IdleStatus.BOTH_IDLE);
		 setRequestInterval(heartBeatInterval);
	     setRequestTimeout(heartBeatTimeOut);
	     this.setForwardEvent( false );  //此消息不会继续传递，不会被业务层看见   
	}
	public  HeartBeatKeepAliveFilter(boolean isClient) {  
          this(isClient, 20, 30);
    }

}
