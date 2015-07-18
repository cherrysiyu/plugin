package com.cherry.utils.observer;


import java.util.Observable;
import java.util.Observer;

import com.cherry.utils.LogUtils;

public class MyObServer2 implements Observer {

	public void update(Observable o, Object arg) {
		Article art = (Article)arg;
		
		LogUtils.debug("博主发表了新的文章，快去看吧!");
		LogUtils.debug("博客标题为：" + art.getArticleTitle());
		LogUtils.debug("博客内容为:" + art.getArticleContent());
	}

}
