package com.cherry.utils.observer;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cherry.utils.ConcurrentHashSet;

/**
 * 此类表示模型视图范例中的 observable 对象，或者说“数据”。可将其子类化，表示应用程序想要观察的对象。 
	一个 observable 对象可以有一个或多个观察者。观察者可以是实现了 Observer 接口的任意对象。一个 observable 实例改变后，调用 Observable 的 notifyObservers 方法的应用程序会通过调用观察者的 update 方法来通知观察者该实例发生了改变。 
	未指定发送通知的顺序。Observable 类中所提供的默认实现将按照其注册的重要性顺序来通知 Observers，但是子类可能改变此顺序，从而使用非固定顺序在单独的线程上发送通知，或者也可能保证其子类遵从其所选择的顺序。 
	注意，此通知机制与线程无关，并且与 Object 类的 wait 和 notify 机制完全独立。 
	新创建一个 observable 对象时，其观察者集是空的。当且仅当 equals 方法为两个观察者返回 true 时，才认为它们是相同的
  @description:此类和JDK中提供的Observable类类似，只是为了防止多线程的问题提高了性能
  @version:0.1
  @author:Cherry
  @date:Aug 26, 2013
 */
public class MyObservable extends Observable{
	
	 private AtomicBoolean changed = new AtomicBoolean(false);
	 private ConcurrentHashSet<Observer> obs;
	   
	    /** Construct an Observable with zero Observers. */

	    public MyObservable() {
	    	obs = new ConcurrentHashSet<Observer>();
	    }

	    /**
	     * Adds an observer to the set of observers for this object, provided 
	     * that it is not the same as some observer already in the set. 
	     * The order in which notifications will be delivered to multiple 
	     * observers is not specified. See the class comment.
	     * @param   o   an observer to be added.
	     * @throws NullPointerException   if the parameter o is null.
	     */
	    public  void addObserver(Observer o) {
	        if (o == null)
	            throw new NullPointerException();
			if (!obs.contains(o))
			    obs.add(o);
	    }

	    /**
	     * Deletes an observer from the set of observers of this object. 
	     * Passing <CODE>null</CODE> to this method will have no effect.
	     * @param   o   the observer to be deleted.
	     */
	    public  void deleteObserver(Observer o) {
	        obs.remove(o);
	    }

	    /**
	     * If this object has changed, as indicated by the 
	     * <code>hasChanged</code> method, then notify all of its observers 
	     * and then call the <code>clearChanged</code> method to 
	     * indicate that this object has no longer changed. 
	     * <p>
	     * Each observer has its <code>update</code> method called with two
	     * arguments: this observable object and <code>null</code>. In other 
	     * words, this method is equivalent to:
	     * <blockquote><tt>
	     * notifyObservers(null)</tt></blockquote>
	     *
	     * @see     java.util.Observable#clearChanged()
	     * @see     java.util.Observable#hasChanged()
	     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
	     */
	    public void notifyObservers() {
	    	notifyObservers(null);
	    }

	    /**
	     * If this object has changed, as indicated by the 
	     * <code>hasChanged</code> method, then notify all of its observers 
	     * and then call the <code>clearChanged</code> method to indicate 
	     * that this object has no longer changed. 
	     * <p>
	     * Each observer has its <code>update</code> method called with two
	     * arguments: this observable object and the <code>arg</code> argument.
	     *
	     * @param   arg   any object.
	     * @see     java.util.Observable#clearChanged()
	     * @see     java.util.Observable#hasChanged()
	     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
	     */
	    public void notifyObservers(Object arg) {
		/*
	         * a temporary array buffer, used as a snapshot of the state of
	         * current Observers.
	         */
	    	Observer[] arrLocal;

		synchronized (this) {
		    /* We don't want the Observer doing callbacks into
		     * arbitrary code while holding its own Monitor.
		     * The code where we extract each Observable from 
		     * the Vector and store the state of the Observer
		     * needs synchronization, but notifying observers
		     * does not (should not).  The worst result of any 
		     * potential race-condition here is that:
		     * 1) a newly-added Observer will miss a
		     *   notification in progress
		     * 2) a recently unregistered Observer will be
		     *   wrongly notified when it doesn't care
		     */
		    if (!changed.get())
	                return;
	            arrLocal = obs.toArray(new Observer[obs.size()]);
	            clearChanged();
	        }
		
	        for (int i = arrLocal.length-1; i>=0; i--)
	            arrLocal[i].update(this, arg);
	    }

	    /**
	     * Clears the observer list so that this object no longer has any observers.
	     */
	    public  void deleteObservers() {
	    	obs.clear();
	    }

	    /**
	     * Marks this <tt>Observable</tt> object as having been changed; the 
	     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
	     */
	    protected  void setChanged() {
	    	changed.getAndSet(true);
	    }

	    /**
	     * Indicates that this object has no longer changed, or that it has 
	     * already notified all of its observers of its most recent change, 
	     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>. 
	     * This method is called automatically by the 
	     * <code>notifyObservers</code> methods. 
	     *
	     * @see     java.util.Observable#notifyObservers()
	     * @see     java.util.Observable#notifyObservers(java.lang.Object)
	     */
	    protected  void clearChanged() {
	    	changed.getAndSet(false);
	    }

	    /**
	     * Tests if this object has changed. 
	     * @return  <code>true</code> if and only if the <code>setChanged</code> 
	     *          method has been called more recently than the 
	     *          <code>clearChanged</code> method on this object; 
	     *          <code>false</code> otherwise.
	     * @see     java.util.Observable#clearChanged()
	     * @see     java.util.Observable#setChanged()
	     */
	    public  boolean hasChanged() {
	    	return changed.get();
	    }

	    /**
	     * Returns the number of observers of this <tt>Observable</tt> object.
	     * @return  the number of observers of this object.
	     */
	    public  int countObservers() {
	    	return obs.size();
	    }
	
	
}
