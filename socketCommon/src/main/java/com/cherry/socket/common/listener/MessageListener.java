package com.cherry.socket.common.listener;

import com.cherry.socket.common.bean.SocketFrame;

/**
 * 服务端实现的接口，客户端消息到达时会调用次方法
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年7月11日 下午10:45:20
 */
public interface MessageListener {
	
	/**
	 *服务端处理，服务器端取得数据后<span style="font:bold;color:red">需要回填SocketFrame中的frameBody属性</span>
	 * @param requestSocketFrame
	 * @return frameBody
	 */
	public byte[] onMessage(SocketFrame requestSocketFrame);

}