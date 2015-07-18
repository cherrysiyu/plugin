package com.cherry.utils.observer;

public class MainClass {
	public static void main(String[] args) {
		BlogUser user = new BlogUser();
		user.addObserver(new MyObServer());
		user.addObserver(new MyObServer2());
		user.publishBlog("哈哈，博客上线了", "大家多来访问");
	}
}
