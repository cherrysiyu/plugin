package com.cherry.socket.common.bean;
import java.io.Serializable;
import java.util.Arrays;

import com.cherry.socket.utils.BitUtils;
import com.cherry.socket.utils.ByteBufferWriter;
import com.cherry.socket.utils.UUIDGenderUtils;

public class SocketFrame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7796376646747825197L;

	/**
	 * 流水号
	 */
	private long frameNo;
	/**
	 * 是否是心跳
	 */
	private boolean isHearBeat;
	
	/**
	 * 全局唯一标识名称
	 */
	private  int appNumber;
	
	
	/**
	 * 1个字节的扩展字段
	 */
	private byte extField =(byte) 0;
	/**
	 * 数据区长度
	 */
	private int bodyLength;

	/**=================================头信息================================================**/
	
	/**
	 * 数据区
	 */
	private byte[] frameBody;
	
	/**=================================消息体信息================================================**/
	
	
	/***
	 * 整个消息信息
	 */
	private byte[] frameBytes;
	
	
	/**
	 * 心跳是否已经保存过了（临时变量,Mina使用）
	 */
	private boolean isHearBeatSaved;
	
	public SocketFrame(int appNumber) {
		if(appNumber <0)
			throw new IllegalArgumentException("appNumber must >0!");
		this.appNumber = appNumber;
		this.frameNo = UUIDGenderUtils.getLongSeq();
	}

	public SocketFrame(int appNumber, long frameNo) {
		if(appNumber <0)
			throw new IllegalArgumentException("appNumber must >0!");
		this.appNumber = appNumber;
		this.frameNo = frameNo;
	}
	
	public SocketFrame(int appNumber, byte[] frameBody) {
		super();
		if(appNumber <0)
			throw new IllegalArgumentException("appNumber must >0!");
		this.appNumber = appNumber;
		frameNo = UUIDGenderUtils.getLongSeq();
		setFrameBody(frameBody);
	}
	
	public SocketFrame(int appNumber, long frameNo, byte[] frameBody) {
		if(appNumber <0)
			throw new IllegalArgumentException("appNumber must >0!");
		this.appNumber = appNumber;
		this.frameNo = frameNo;
		setFrameBody(frameBody);
	}

	public long getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(long frameNo) {
		this.frameNo = frameNo;
	}
	public SocketFrame addFrameNo(long frameNo){
		setFrameNo(frameNo);
		return this;
	}
	public byte getExtField() {
		return extField;
	}
	public void setExtField(byte extField) {
		this.extField = extField;
	}
	public SocketFrame addExtField(byte extField){
		setExtField(extField);
		return this;
	}
	
	public boolean isHearBeat() {
		return isHearBeat;
	}
	
	public void setHearBeat(boolean isHearBeat) {
		this.isHearBeat = isHearBeat;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public void setFrameBody(byte[] frameBody) {
		if(frameBody !=null && frameBody.length>0){
			this.frameBody = frameBody;
			this.bodyLength = frameBody.length;
		}
	}
	
	public byte[] getFrameBody() {
		return frameBody;
	}

	public byte[] getFrameBytes() {
		if(frameBytes == null)
			buildFrameBytes();
		return frameBytes;
	}

	public SocketFrame buildFrameBytes() {
		ByteBufferWriter writer = new ByteBufferWriter(Constans.FRAME_MIN_LENGTH);
		writer.writeByte(Constans.FRAME_HEAD);
		writer.writeLong(frameNo);
		writer.writeBoolean(isHearBeat);
		writer.writeInt(appNumber);
		writer.writeByte(extField);
		writer.writeInt(bodyLength);
		if(frameBody !=null && frameBody.length>0)
			writer.writeBytes(frameBody);
		this.frameBytes = writer.toByteArray();
		return this;
	}
	
	
	
	public int getAppNumber() {
		return appNumber;
	}
	
	public boolean isHearBeatSaved() {
		return isHearBeatSaved;
	}

	public void setHearBeatSaved(boolean isHearBeatSaved) {
		this.isHearBeatSaved = isHearBeatSaved;
	}
	public void clear(){
		if(frameBody != null)
			Arrays.fill(frameBody, (byte)0);
		frameBody = null;
	}

	@Override
	public String toString() {
		return BitUtils.asHex(frameBytes, " ");
	}
}
