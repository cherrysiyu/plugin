package com.cherry.utils.serializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import com.cherry.utils.CommonMethod;

public class MessageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			
	private char charField='c';
	private byte byteField;
	private boolean booleanField;
	private short charShort;
	private int intField;
	private float floatField;		
	private long longField;
	
	private Byte byteField2=1;
	private Boolean booleanField2=true;
	private Short charShort2=1;
	private Integer intField2=1;
	private Float floatField2=1.0f;		
	private Long longField2=1L;
	
	private String strField;
	
	private BigInteger bigIntegerField;
	
	private BigDecimal bigDecimalField;
	
	private Timestamp time=new Timestamp(System.currentTimeMillis());
	private Date date = new Date();
	public char getCharField() {
		return charField;
	}

	public void setCharField(char charField) {
		this.charField = charField;
	}

	public byte getByteField() {
		return byteField;
	}

	public void setByteField(byte byteField) {
		this.byteField = byteField;
	}

	public boolean isBooleanField() {
		return booleanField;
	}

	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}

	public short getCharShort() {
		return charShort;
	}

	public void setCharShort(short charShort) {
		this.charShort = charShort;
	}

	public int getIntField() {
		return intField;
	}

	public void setIntField(int intField) {
		this.intField = intField;
	}

	public float getFloatField() {
		return floatField;
	}

	public void setFloatField(float floatField) {
		this.floatField = floatField;
	}

	public long getLongField() {
		return longField;
	}

	public void setLongField(long longField) {
		this.longField = longField;
	}

	public Byte getByteField2() {
		return byteField2;
	}

	public void setByteField2(Byte byteField2) {
		this.byteField2 = byteField2;
	}

	public Boolean getBooleanField2() {
		return booleanField2;
	}

	public void setBooleanField2(Boolean booleanField2) {
		this.booleanField2 = booleanField2;
	}

	public Short getCharShort2() {
		return charShort2;
	}

	public void setCharShort2(Short charShort2) {
		this.charShort2 = charShort2;
	}

	public Integer getIntField2() {
		return intField2;
	}

	public void setIntField2(Integer intField2) {
		this.intField2 = intField2;
	}

	public Float getFloatField2() {
		return floatField2;
	}

	public void setFloatField2(Float floatField2) {
		this.floatField2 = floatField2;
	}

	public Long getLongField2() {
		return longField2;
	}

	public void setLongField2(Long longField2) {
		this.longField2 = longField2;
	}

	public String getStrField() {
		return strField;
	}

	public void setStrField(String strField) {
		this.strField = strField;
	}

	public BigInteger getBigIntegerField() {
		return bigIntegerField;
	}

	public void setBigIntegerField(BigInteger bigIntegerField) {
		this.bigIntegerField = bigIntegerField;
	}

	public BigDecimal getBigDecimalField() {
		return bigDecimalField;
	}

	public void setBigDecimalField(BigDecimal bigDecimalField) {
		this.bigDecimalField = bigDecimalField;
	}
	
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return CommonMethod.convertBean2Map(this).toString();
	}
	
	

}
