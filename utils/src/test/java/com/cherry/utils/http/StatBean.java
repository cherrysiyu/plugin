package com.cherry.utils.http;

public class StatBean {
	private boolean isSuccess;
	private long benginTime;
	private long endTime;
	private long fileLength;
	
	private long downloadTime;
	public long getBenginTime() {
		return benginTime;
	}
	public void setBenginTime(long benginTime) {
		this.benginTime = benginTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public long getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(long downloadTime) {
		this.downloadTime = downloadTime;
	}
	
	public long getHttpRequestTime(){
		return endTime - benginTime;
	}
	
	public long getDownloadSuccessTime(){
		return downloadTime - endTime;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	
}
