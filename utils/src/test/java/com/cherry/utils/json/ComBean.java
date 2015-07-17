package com.cherry.utils.json;

import java.util.List;
import java.util.Map;

public class ComBean	extends JSONBean {
    private Map<String, Object> mapValue;
    private List<Object>        listValue;
    private Object              nullValue;

    public ComBean(String stringValue, int intValue, double doubleValue, long longValue,
                          boolean booleanValue, Map<String, Object> mapValue, List<Object> listValue) {
        super();
        this.setStringValue(stringValue);
        this.setIntValue(intValue);
        this.setDoubleValue(doubleValue);
        this.setLongValue(longValue);
        this.setBooleanValue(booleanValue);
        this.mapValue = mapValue;
        this.listValue = listValue;
        this.nullValue = null;
    }

    public Map<String, Object> getMapValue() {
        return mapValue;
    }

    public void setMapValue(Map<String, Object> mapValue) {
        this.mapValue = mapValue;
    }

    public List<Object> getListValue() {
        return listValue;
    }

    public void setListValue(List<Object> listValue) {
        this.listValue = listValue;
    }

    public Object getNullValue() {
        return nullValue;
    }

    public void setNullValue(Object nullValue) {
        this.nullValue = nullValue;
    }

	public String toJson(){
        StringBuffer sb = new StringBuffer();
        sb.append("{")
        .append("\"stringValue\":\"")
        .append(this.getStringValue())
        .append("\",")
        .append("\"intValue\":")
        .append(this.getIntValue())
        .append(",")
        .append("\"doubleValue\":")
        .append(this.getDoubleValue())
        .append(",")
        .append("\"longValue\":")
        .append(this.getLongValue())
        .append(",")
        .append("\"booleanValue\":")
        .append(this.getBooleanValue())
        .append(",")
        .append("\"nullValue\":")
        .append(this.getNullValue())
        .append(",")
        .append("\"listValue\":[\"")
	        .append(this.getListValue().get(0))
	        .append("\",\"")
	        .append(this.getListValue().get(1))
	        .append("\",{")
	        .append("\"mapValue\":{},")
	        .append("\"listValue\":[],")
	        .append("\"nullValue\":")
	        .append(((ComBean)this.getListValue().get(2)).getNullValue())
	        .append(",\"stringValue\":\"")
	        .append(((ComBean)this.getListValue().get(2)).getStringValue())
	        .append("\",\"intValue\":")
	        .append(((ComBean)this.getListValue().get(2)).getIntValue())
	        .append(",\"doubleValue\":")
	        .append(((ComBean)this.getListValue().get(2)).getDoubleValue())
	        .append(",\"longValue\":")
	        .append(((ComBean)this.getListValue().get(2)).getLongValue())
	        .append(",\"booleanValue\":false")
	        .append("}")
	    .append("],")
        .append("\"mapValue\":{")
        	.append("\"key3\":{")
		        .append("\"mapValue\":{},")
		        .append("\"listValue\":[],")
		        .append("\"nullValue\":null,")
		        .append("\"stringValue\":\"a string\",")
		        .append("\"intValue\":123,")
		        .append("\"doubleValue\":123.123,")
		        .append("\"longValue\":123,")
		        .append("\"booleanValue\":false")
		        .append("},")
		    .append("\"key2\":\"")
		    .append(this.getMapValue().get("key2"))
		    .append("\",")
		    .append("\"key1\":\"")
		    .append(this.getMapValue().get("key1"))
		    .append("\"}")
        .append("}");
        return sb.toString();
    }

}
