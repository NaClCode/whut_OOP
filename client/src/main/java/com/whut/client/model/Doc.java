package com.whut.client.model;

import java.io.Serializable;

public class Doc implements Serializable{
	private String ID;
	private String creator;
	private long timestamp;
	private String description;
	private String filename;
	private String uploadFileName;
	private int downloadCount;
	
	public Doc(String iD, String creator, String description, String filename, long timestamp, String uploadFileName) {
		ID = iD;
		this.creator = creator;
		this.timestamp = timestamp;
		this.description = description;
		this.filename = filename;
		this.uploadFileName = uploadFileName;
		this.downloadCount = 0;
	}
	public Doc(String iD, String creator, String description, String filename, long timestamp, String uploadFileName, int downloadCount) {
		ID = iD;
		this.creator = creator;
		this.timestamp = timestamp;
		this.description = description;
		this.filename = filename;
		this.uploadFileName = uploadFileName;
		this.downloadCount = downloadCount;
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
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFileName(){
		return this.uploadFileName;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

}