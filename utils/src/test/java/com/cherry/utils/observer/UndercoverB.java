package com.cherry.utils.observer;
import java.util.Observable;
import java.util.Observer;

import com.cherry.utils.LogUtils;
/** 
 *  
 *描述：卧底B 
 */  
public class UndercoverB implements Observer {  
    private String time;  
    @Override  
    public void update(Observable o, Object arg) {  
        time = (String) arg;  
        LogUtils.debug("卧底B接到消息，行动时间为："+time);  
    }  
  
  
  
}  