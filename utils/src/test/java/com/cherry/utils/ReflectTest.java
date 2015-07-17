package com.cherry.utils;



import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ReflectTest {
	 private final static Map<String, Class<?>> primitiveClassMap;  
	    static {  
	        Map<String,Class<?>> tmp = new HashMap<String, Class<?>>();  
	        tmp.put("short", short.class );  
	        tmp.put("int",  int.class);  
	        tmp.put("char",  char.class);  
	        tmp.put("long",  long.class);  
	        tmp.put("boolean",  boolean.class);  
	        tmp.put("float",  float.class);  
	        tmp.put("double",  double.class);  
	        tmp.put("byte",  byte.class);  
	        primitiveClassMap = Collections.unmodifiableMap( tmp );  
	    }  
	    private final static Map<String, String> primitiveArrayClassMap;  
	    static {  
	        Map<String,String> tmp = new HashMap<String, String>();  
	        tmp.put("short[]", "[S" );  
	        tmp.put("int[]",  "[I");  
	        tmp.put("char[]",  "[C");  
	        tmp.put("long[]",  "[L");  
	        tmp.put("boolean[]",  "[Z");  
	        tmp.put("float[]",  "[F");  
	        tmp.put("double[]",  "[D");  
	        tmp.put("byte[]",  "[B");  
	        primitiveArrayClassMap = Collections.unmodifiableMap( tmp );  
	    }  
	public static void main(String[] args) throws Exception {
		
		Class<?> cls = Class.forName("com.cherry.util.ReflectTestBean");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			 LogUtils.debug(field.getName()+"---"+field.getDeclaringClass()+"---"+field.getType());
			 boolean array = field.getType().isArray();
			 if(array){
				// Array.getLength(array);
			 }
		}
		
	}
	
	
}
class ReflectTestBean{
	
	private boolean booleanField;
	private byte byteFiled;
	
	private char charField;
	
	private short shortField;
	
	private int intField;
	
	private float floatField;
	
	private long longField;
	
	private double doubleField;
	
	private String stringField;
	
	private String[] arrayField;
	
	private int[] intArrayField;
	
	private Map<String,Object> mapField;

	public boolean isBooleanField() {
		return booleanField;
	}

	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}

	public byte getByteFiled() {
		return byteFiled;
	}

	public void setByteFiled(byte byteFiled) {
		this.byteFiled = byteFiled;
	}

	public char getCharField() {
		return charField;
	}

	public void setCharField(char charField) {
		this.charField = charField;
	}

	public short getShortField() {
		return shortField;
	}

	public void setShortField(short shortField) {
		this.shortField = shortField;
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

	public double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public String[] getArrayField() {
		return arrayField;
	}

	public void setArrayField(String[] arrayField) {
		this.arrayField = arrayField;
	}

	public Map<String, Object> getMapField() {
		return mapField;
	}

	public void setMapField(Map<String, Object> mapField) {
		this.mapField = mapField;
	}

	public int[] getIntArrayField() {
		return intArrayField;
	}

	public void setIntArrayField(int[] intArrayField) {
		this.intArrayField = intArrayField;
	}
	
	
}
