package com.cherry.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteBufferReader {

	private final Charset defaultCharset = Charset.forName("UTF-8");
	//private  final String EMPTY_STRING = "";
	// 二进制流
	private byte[] elementData;
	// 偏移量
	private int offset;
	
	
	public static ByteBufferReader wrap(byte[] elementData){
		return wrap(elementData, 0);
	}
	public static ByteBufferReader wrap(byte[] elementData, int offset){
		return new ByteBufferReader(elementData, offset);
	}
	public ByteBufferReader(byte[] elementData) {
		this(elementData,0);
	}


	public ByteBufferReader(byte[] elementData, int offset) {
		super();
		if(elementData == null)
			throw new  IllegalArgumentException("elementData is null or empty，please ensure elementData.length >0");
		this.elementData = elementData;
		if(offset >=0)
			this.offset = offset;
	}

	public void addAll(byte[] nextElementData){
		if(nextElementData != null){
			byte[] joinedArray = new byte[elementData.length + nextElementData.length];
	        System.arraycopy(elementData, 0, joinedArray, 0, elementData.length);
	        System.arraycopy(nextElementData, 0, joinedArray, elementData.length, nextElementData.length);
	        this.elementData = joinedArray;
		}
	}
	
	
	public  boolean readBoolean() {
		ensureLength(1);
		boolean readBoolean = BitUtils.readBoolean(elementData, offset);
		offset++;
        return readBoolean;
    } 
	
	public  boolean readBoolean(int offset) {
		ensureLength(1);
        return BitUtils.readBoolean(elementData, offset);
    }
	
	public  short readByte() {
		ensureLength(1);
		short readShort = BitUtils.readByte(elementData, offset);
		offset++;
        return readShort;
    } 
	
	public  short readByte(int offset) {
		ensureLength(1);
        return BitUtils.readByte(elementData, offset);
    }

	public  char readChar() {
		ensureLength(2);
		char readChar = BitUtils.readChar(elementData,offset);
		offset += 2;
        return readChar;
    }
	
	public  char readChar(int offset) {
		ensureLength(2);
        return BitUtils.readChar(elementData,offset);
    }
	
	public  int readShort() {
		ensureLength(2);
		int readShort = BitUtils.readShort(elementData,offset);
		offset += 2;
		return readShort;
    }
	
	public  int readShort(int offset) {
		ensureLength(2);
        return  BitUtils.readShort(elementData,offset);
    }

	public  int readInt() {
		ensureLength(4);
		int readInt = BitUtils.readInt(elementData,offset);
		offset += 4;
		return readInt;
	}
	public  int readInt(int offset) {
		ensureLength(4);
		return BitUtils.readInt(elementData,offset);
    }
	
	
	public  float readFloat() {
		ensureLength(4);
		float readFloat = BitUtils.readFloat(elementData,offset);
		offset += 4;
		return readFloat;
	}
	public  float readFloat(int offset) {
		ensureLength(4);
        return BitUtils.readFloat(elementData,offset);
    }

	public  long readLong() {
		ensureLength(8);
		long readLong = BitUtils.readLong(elementData,offset);
		offset += 8;
		return readLong;
	}
	public  long readLong(int offset) {
		ensureLength(8);
		return BitUtils.readLong(elementData,offset);
    }

	public  double readDouble() {
		ensureLength(8);
		double readDouble = BitUtils.readDouble(elementData,offset);
		offset += 8;
		return readDouble;
	}
	
	public  double readDouble(int offset) {
		ensureLength(8);
        return BitUtils.readDouble(elementData,offset);
    }
	
	
	public byte[] readBytes(int length){
		byte[] readBytes = readBytes(offset,length);
		offset += length;
		return readBytes;
	}
	
	public byte[] readBytes(int offset,int length){
		ensureLength(length,offset);
		return Arrays.copyOfRange(elementData, offset, offset+length);
	}
	
	
	public String readShortString(){
		return readShortString(defaultCharset);
	}
	public List<String> readShortStringArray(int arrayLength){
		return readShortStringArray(arrayLength,defaultCharset);
	}
	public List<String> readShortStringArray(int arrayLength,Charset charset) {
		return readStringArray(arrayLength,false,charset);
	}
	public String readShortString(Charset charset){
		return readString(false,charset);
	}
	
	public String readShortString(int offset){
		return readShortString(offset,defaultCharset);
	}
	public String readShortString(int offset,Charset charset){
		return readString(offset,false,charset);
	}
	public List<String> readShortStringArray(int arrayLength,int offset){
		return readShortStringArray(arrayLength,offset,defaultCharset);
	}
	public List<String> readShortStringArray(int arrayLength,int offset,Charset charset) {
		return readStringArray(arrayLength,offset,false,charset);
	}
	
	


	public String readIntString(){
		return readIntString(defaultCharset);
	}
	public String readIntString(Charset charset) {
		return readString(true,charset);
	}
	public List<String> readIntStringArray(int arrayLength){
		return readIntStringArray(arrayLength,defaultCharset);
	}
	public List<String> readIntStringArray(int arrayLength,Charset charset) {
		return readStringArray(arrayLength,true,charset);
	}

	public String readIntString(int offset){
		return readIntString(offset,defaultCharset);
	}
	public String readIntString(int offset,Charset charset){
		return readString(offset,true,charset);
	}
	public List<String> readIntStringArray(int arrayLength,int offset){
		return readIntStringArray(arrayLength,offset,defaultCharset);
	}
	public List<String> readIntStringArray(int arrayLength,int offset,Charset charset) {
		return readStringArray(arrayLength,offset,false,charset);
	}
	
	private String readString(boolean isIntLength,Charset charset){
		int length = 0;
		if(isIntLength){
			ensureLength(4);
			length = this.readInt();
			
		}else{
			ensureLength(2);
			length = this.readShort();
		}
		ensureLength(length);
		if(charset == null)
			charset = defaultCharset;
		byte[] bytes = Arrays.copyOfRange(elementData, offset, offset+length);
		offset += length;
		return new String(bytes, charset);
	}
	
	private List<String> readStringArray(int arrayLength, boolean isIntLength, Charset charset) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arrayLength; i++) {
			list.add(readString(isIntLength,charset));
		}
		return list;
	}
	private List<String> readStringArray(int arrayLength, int offset,boolean isIntLength, Charset charset) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arrayLength; i++) {
			list.add(readString(offset,isIntLength,charset));
		}
		return list;
	}
	
	
	private String readString(int offset,boolean isIntLength,Charset charset){
		int length = 0;
		if(isIntLength){
			ensureLength(4,offset);
			length = this.readInt(offset);
			offset +=4;
		}else{
			ensureLength(2,offset);
			length = this.readShort(offset);
			offset +=2;
		}
		ensureLength(length,offset);
		if(charset == null)
			charset = defaultCharset;
		byte[] subarray = subarray(elementData, offset, offset+length);
		offset += length;
		return new String(subarray,charset);
	}

	public String toString() {
		return BitUtils.asHex(elementData, " ");
	}
	
	public int remainingSize(){
		return elementData.length - offset;
	}
	
	public int currentOffset(){
		return offset;
	}
	
	private void ensureLength(int needLength){
		if(this.elementData == null || this.elementData.length<(offset+needLength))
			throw new RuntimeException(" remaining length is not enough for "+needLength);
	}
	private void ensureLength(int needLength,int offset){
		if(this.elementData == null || this.elementData.length<(offset+needLength))
			throw new RuntimeException(" remaining length is not enough for "+needLength);
	}
	
	/**
     * <p>Produces a new <code>byte</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     */
    private  byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return new byte[]{};
        }

        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

}
