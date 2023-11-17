package com.whut.dao;

// 实验三：实现此类
public class Doc{
	private String ID;
	private String creator;
	private long timestamp;
	private String description;
	private String filename;
	
	public Doc(String iD, String creator, String description, String filename, long timestamp) {
		ID = iD;
		this.creator = creator;
		this.timestamp = timestamp;
		this.description = description;
		this.filename = filename;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	

}