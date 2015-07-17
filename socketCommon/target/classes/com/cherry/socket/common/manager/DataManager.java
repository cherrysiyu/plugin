package com.cherry.socket.common.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;

/**
 * 
 * @author Cherry
 * @version 0.1
 * @Desc 管理客户端首发数据
 * 2014年7月11日 下午8:58:33
 */
public class DataManager {
	private static final Logger logger = LoggerFactory.getLogger(DataManager.class);
	public static ConcurrentMap<Long,Response> map = new ConcurrentHashMap<Long, Response>();
	
	/**
	 * 放入信息
	 * @param frameNo
	 * @param res
	 */
	public static void addMessage(long frameNo,Response res){
		map.put(frameNo,res);
	}
	
	/**
	 * 客户端等待获取消息
	 * @param frameNo 序号
	 * @return
	 */
	public static SocketFrame  getMessage(long frameNo){
		if(map.containsKey(frameNo)){
			Response response = map.get(frameNo);
			SocketFrame take = response.take();
			if(take == null)
				logger.error(new StringBuilder().append("客户端等待消息返回超时，frameNo:").append(frameNo).append("，移除消息，time:").append(System.currentTimeMillis()).toString());
			map.remove(frameNo);
			return take;
		}else{
			logger.error("获取消息没有对应的信息frameNo:"+frameNo);
			return null;
		}
	}
	
	/**
	 * 客户端收到回应消息
	 * @param frame
	 */
	public static void receivedMessage(SocketFrame frame){
		Response response = map.get(frame.getFrameNo());
		if(response != null){
			response.put(frame);
		}else{
			logger.error(new StringBuilder().append("消息返回时信息已移除，frameNo:").append(frame.getFrameNo()).append("，消息返回时，time:").append(System.currentTimeMillis()).toString());
			frame.clear();
			frame = null;
		}
	}

}
