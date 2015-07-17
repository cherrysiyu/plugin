package com.cherry.socket.netty.server;

import java.util.concurrent.Callable;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;

/**
 * 线程处理类
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年9月20日 下午8:43:30
 * 
 * 2.4. Netty线程开发最佳实践
2.4.1. 时间可控的简单业务直接在IO线程上处理
如果业务非常简单，执行时间非常短，不需要与外部网元交互、访问数据库和磁盘，不需要等待其它资源，则建议直接在业务ChannelHandler中执行，不需要再启业务的线程或者线程池。避免线程上下文切换，也不存在线程并发问题。

2.4.2. 复杂和时间不可控业务建议投递到后端业务线程池统一处理
对于此类业务，不建议直接在业务ChannelHandler中启动线程或者线程池处理，建议将不同的业务统一封装成Task，统一投递到后端的业务线程池中进行处理。

过多的业务ChannelHandler会带来开发效率和可维护性问题，不要把Netty当作业务容器，对于大多数复杂的业务产品，仍然需要集成或者开发自己的业务容器，做好和Netty的架构分层。

2.4.3. 业务线程避免直接操作ChannelHandler
对于ChannelHandler，IO线程和业务线程都可能会操作，因为业务通常是多线程模型，这样就会存在多线程操作ChannelHandler。为了尽量避免多线程并发问题，建议按照Netty自身的做法，通过将操作封装成独立的Task由NioEventLoop统一执行，而不是业务线程直接操作，相关代码如下所示：



图2-31 封装成Task防止多线程并发操作

如果你确认并发访问的数据或者并发操作是安全的，则无需多此一举，这个需要根据具体的业务场景进行判断，灵活处理。

3. 总结
尽管Netty的线程模型并不复杂，但是如何合理利用Netty开发出高性能、高并发的业务产品，仍然是个有挑战的工作。只有充分理解了Netty的线程模型和设计原理，才能开发出高质量的产品。
 */
public class MessageProcessThread implements Callable<byte[]> {
	private MessageListener messageListener;
	private SocketFrame requestSocketFrame;
	public MessageProcessThread(MessageListener messageListener,SocketFrame requestSocketFrame) {
		super();
		this.messageListener = messageListener;
		this.requestSocketFrame = requestSocketFrame;
	}
	@Override
	public byte[] call() throws Exception {
		return messageListener.onMessage(requestSocketFrame);
	}

	
}
