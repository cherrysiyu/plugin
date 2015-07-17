package com.cherry.utils.observer;

import java.util.List;
import java.util.Observer;

/**
 * 警察
  @description:
  @version:0.1
  @author:Cherry
  @date:Aug 27, 2013
 */
public class Police extends MyObservable {  
	  
    private String time ;  
    public Police(List<Observer> list) {  
        super();  
        for (Observer o:list) {  
            addObserver(o);  
        }  
    }  
    public void change(String time){  
        this.time = time;  
        setChanged();  
        notifyObservers(this.time);  
    }  
}  
