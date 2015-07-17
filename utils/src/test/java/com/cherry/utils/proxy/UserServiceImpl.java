package com.cherry.utils.proxy;

import com.cherry.utils.LogUtils;


public class UserServiceImpl implements UserService {

	public void add(String username,String password) {
		LogUtils.debug("UserServiceImpl--->add()");

	}

	public void delete(String username) {
		LogUtils.debug("UserServiceImpl--->delete()");

	}

	public void update(String username,String username2) {
		LogUtils.debug("UserServiceImpl--->update()");
	}

	
	public void  check(){
		LogUtils.debug("check");
	}
}
