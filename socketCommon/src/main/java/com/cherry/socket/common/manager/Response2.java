package com.cherry.socket.common.manager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cherry.socket.common.bean.SocketFrame;

public final class Response2 {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	private SocketFrame  frame;
	private boolean has;
	private long timeOut;

	public Response2(long timeOut) {
		super();
		this.timeOut = timeOut;
	}

	public SocketFrame take(){
		lock.lock();
		try {
			if (!has) {
				notEmpty.await(timeOut, TimeUnit.MILLISECONDS);
			}
			has = false;
			notFull.signal();
		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}
		return frame;
	}

	public void put(SocketFrame frame){
		lock.lock();
		try {
			if (has) {
				notFull.await(timeOut, TimeUnit.MILLISECONDS);
			}
			has = true;
			this.frame = frame;
			notEmpty.signal();
		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
			
		}
	}
}
