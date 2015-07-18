package com.cherry.utils;

import java.nio.charset.Charset;
import java.util.Arrays;


public class ByteBufferWriter {
	
	private final Charset defaultCharset = Charset.forName("UTF-8");
	/**
     * The number of valid components in this {@code ByteBufferWriter} object.
     * Components {@code elementData[0]} through
     * {@code elementData[size-1]} are the actual items.
     *
     * @serial
     */
	private int size;
	/**
    * The array buffer into which the components of the ByteBufferWriter are
    * stored. The capacity of the vector is the length of this array buffer,
    * and is at least large enough to contain all the ByteBufferWriter's elements.
    *
    * <p>Any array elements following the last element in the ByteBufferWriter are null.
    *
    * @serial
    */
	private byte[] elementData;
	/**
     * The amount by which the capacity of the ByteBufferWriter is automatically
     * incremented when its size becomes greater than its capacity.  If
     * the capacity increment is less than or equal to zero, the capacity
     * of the ByteBufferWriter is doubled each time it needs to grow.
     *
     * @serial
     */
	private int capacityIncrement; 
	
	/**
     * Constructs an empty ByteBufferWriter with the specified initial capacity and
     * capacity increment.
     *
     * @param   initialCapacity     the initial capacity of the vector
     * @param   capacityIncrement   the amount by which the capacity is
     *                              increased when the vector overflows
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ByteBufferWriter(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        this.elementData = new byte[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    /**
     * Constructs an empty ByteBufferWriter with the specified initial capacity and
     * with its capacity increment equal to zero.
     *
     * @param   initialCapacity   the initial capacity of the vector
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ByteBufferWriter(int initialCapacity) {
        this(initialCapacity, 0);
    }

    /**
     * Constructs an empty ByteBufferWriter so that its internal data array
     * has size {@code 8} and its standard capacity increment is
     * zero.
     */
    public ByteBufferWriter() {
        this(8);
    }

    /**
     * Constructs a ByteBufferWriter containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this
     *       ByteBufferWriter
     * @throws NullPointerException if the specified collection is null
     */
    public ByteBufferWriter(byte[] bytes) {
       this.elementData = bytes;
       this.size = elementData.length;
    }
    
    public  ByteBufferWriter writeBoolean(boolean val) {
    	return writeByte(BitUtils.boolean2Byte(val));
    }
    
    public  ByteBufferWriter writeChar(char val) {
    	return writeBytes(BitUtils.char2Bytes(val));
    }
    
    public  ByteBufferWriter writeInt(int val) {
    	return writeBytes(BitUtils.int2Bytes(val));
    }
    
    public  ByteBufferWriter writeShort(short val) {
    	return writeBytes(BitUtils.short2Bytes(val));
    }
    public  ByteBufferWriter writeFloat(float val) {
    	return writeBytes(BitUtils.float2Bytes(val));
    }
    public  ByteBufferWriter writeLong(long val) {
    	
    	return writeBytes(BitUtils.long2Bytes(val));
    }
    public  ByteBufferWriter writeDouble(double val) {
    	return writeBytes(BitUtils.double2Bytes(val));
    }
    
    
    public ByteBufferWriter writeShortLengthString(String str){
    	return writeString(str, defaultCharset, false);
    }
    
    public ByteBufferWriter writeShortLengthString(String str,Charset charset){
    	return writeString(str, charset, false);
    }
    
    public ByteBufferWriter writeIntLengthString(String str){
    	return writeString(str, defaultCharset, true);
    }
    
    public ByteBufferWriter writeIntLengthString(String str,Charset charset){
    	return writeString(str, charset, true);
    }
    
    public ByteBufferWriter writeString(String str,Charset charset,boolean isIntLength){
    	if(str != null){
    		if(charset == null)
    			charset = defaultCharset;
    		byte[] bytes = str.getBytes(charset);
    		if(isIntLength)
    			writeInt(bytes.length);
    		else{
    			if(bytes.length > Short.MAX_VALUE)
    				throw new IllegalArgumentException("the length of argument str is too larger,please substring this argument to  sure it's length <= "+Short.MAX_VALUE+", or set the argument isIntLength=true");
    			writeShort((short)bytes.length);
    		}
    		if(bytes.length>0)
    			writeBytes(bytes);
    	}
    	return this;
    }
    
    
    public ByteBufferWriter writeByte(byte byteVal){
    	ensureCapacity(size + 1);
    	elementData[size] = byteVal;
    	size++;
    	return this;
    }
    
    public ByteBufferWriter writeBytes(byte[] bytes){
    	if(bytes == null)
    		throw new NullPointerException("bytes is null");
    	ensureCapacity(size + bytes.length);
    	System.arraycopy(bytes, 0, elementData, size, bytes.length);
    	size += bytes.length;
    	return this;
    	
    }
    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void clear(){
    	Arrays.fill(elementData, (byte)0);
    	this.size = 0;
    	elementData = null;
    }
    
    public byte[] toByteArray(){
    	return Arrays.copyOf(elementData, size);
    }
    
    private  void ensureCapacity(int minCapacity) {
    	if (minCapacity - elementData.length > 0){
    		int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + ((capacityIncrement > 0) ?capacityIncrement : oldCapacity);
            if(newCapacity - minCapacity <0)
            	newCapacity = minCapacity;
            elementData = Arrays.copyOf(elementData, newCapacity);
    	}
    }
    
    public String toString(){
    	return BitUtils.asHex(Arrays.copyOf(elementData, size), " ");
    }
    
    

}
