package com.cherry.utils.observer;
import java.util.Observable;
import java.util.Observer;

import com.cherry.utils.LogUtils;
/** 
 *  
 *描述：卧底A 
 */  
public class UndercoverA implements Observer {  
  
    private String time;  
    @Override  
    public void update(Observable o, Object arg) {  
        time = (String) arg;  
        LogUtils.debug("卧底A接到消息，行动时间为："+time);  
    }  
  
  
}  