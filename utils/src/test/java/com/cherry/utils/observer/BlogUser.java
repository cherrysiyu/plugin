package com.cherry.utils.observer;

import com.cherry.utils.LogUtils;

public class BlogUser extends MyObservable {
	
	public void publishBlog(String articleTitle,String articleContent) {
		Article art = new Article();
		art.setArticleTitle(articleTitle);
		art.setArticleContent(articleContent);
		LogUtils.debug("博主:发表新文章，文章标题:" + articleTitle + ",文章内容:" + articleContent);
		this.setChanged();
		this.notifyObservers(art);
		
		LogUtils.debug("观察者的个数:"+this.countObservers());
	}
}
