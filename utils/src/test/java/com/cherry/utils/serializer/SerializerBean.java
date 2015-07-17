package com.cherry.utils.serializer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SerializerBean {
	
	private boolean flag;
	
	private int age;
	
	private String name;
	
	private Date now = new Date();
	
	private Timestamp time = new Timestamp(now.getTime());
	
	private BigDecimal bd;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public BigDecimal getBd() {
		return bd;
	}

	public void setBd(BigDecimal bd) {
		this.bd = bd;
	}
	
	
}
