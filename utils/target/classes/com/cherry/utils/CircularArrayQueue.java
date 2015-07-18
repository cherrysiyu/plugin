package com.cherry.utils;

import java.util.AbstractQueue;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 循环队列
 * @description:
 * @version:0.1
 * @author:Cherry
 * @date:Aug 8, 2013
 */
public class CircularArrayQueue<E> extends AbstractQueue<E> {

	private Object[] elements;
	private int head;
	private int tail;
	private int count;
	private int modcount;

	/**
	 * Constructs an empty queue.
	 * @param capacity the maximum capacity of the queue
	 */
	public CircularArrayQueue(int capacity) {
		elements = new Object[capacity];
		count = 0;
		head = 0;
		tail = 0;
	}

	public boolean offer(E newElement) {
		assert newElement != null;
		if (count < elements.length) {
			elements[tail] = newElement;
			tail = (tail + 1) % elements.length;
			count++;
			modcount++;
			return true;
		} else
			return false;
	}

	public E poll() {
		if (count == 0)
			return null;
		E r = peek();
		head = (head + 1) % elements.length;
		count--;
		modcount++;
		return r;
	}

	@SuppressWarnings("unchecked")
	public E peek() {
		if (count == 0)
			return null;
		return (E) elements[head];
	}

	public int size() {
		return count;
	}

	public Iterator<E> iterator() {
		return new QueueIterator();

	}
	
	/**
	 * 队列迭代器，不支持删除
	  @description:
	  @version:0.1
	  @author:Cherry
	  @date:Aug 8, 2013
	 */
	private class QueueIterator implements Iterator<E> {
		private int offset;
		private int modcountAtConstruction;

		public QueueIterator() {
			modcountAtConstruction = modcount;
		}

		@SuppressWarnings("unchecked")
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E r = (E) elements[(head + offset) % elements.length];
			offset++;
			return r;
		}

		public boolean hasNext() {
			if (modcount != modcountAtConstruction)
				throw new ConcurrentModificationException();
			return offset < count;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
