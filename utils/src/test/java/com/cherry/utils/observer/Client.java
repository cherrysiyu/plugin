package com.cherry.utils.observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import com.cherry.utils.LogUtils;
;
/** 
 *  
 *描述：测试 
 */  
public class Client {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        UndercoverA o1 = new UndercoverA();  
        UndercoverB o2 = new UndercoverB();  
        List<Observer> list = new ArrayList<Observer>();  
        list.add(o1);  
        list.add(o2);  
        Police subject = new Police(list);  
        subject.change("02:25");  
        LogUtils.debug("===========由于消息败露，行动时间提前=========");  
        subject.change("01:05");  
          
    }  
  
}  