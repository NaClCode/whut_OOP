package com.whut.client.model;

import com.whut.client.service.DataProcessing;
import com.whut.client.service.Download;

import java.io.File;
import java.io.IOException;

import java.io.Serializable;
public abstract class User implements Serializable{
	private String name;
	private String password;
	private String role;

	User(String name, String password, String role){
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public boolean changeUserInfo(String password) throws Exception{
		if (DataProcessing.updateUser(name, password, role)){
			this.password = password;
			return true;
		}
		else return false;
	}

	public void exitSystem(){
		System.exit(0);
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getRole(){
		return role;
	}

	public void setRole(String role){
		this.role = role;
	}

	public boolean downloadFile(String ID) throws IOException, Exception{
		Doc doc = DataProcessing.searchDoc(ID);
		if (doc != null) {
			String download_filepath = DataProcessing.config.get("download_filepath");
			String downPath = download_filepath + "\\" + doc.getFilename();
			File downloadFile = new File(downPath);

			if(!downloadFile.exists()){
				new Download(doc.getUploadFileName(), doc.getFilename()).start();
        		return true;
            }
			
			
		}
		return false;
	}


	public boolean uploadFile(String ID, String description, String filePath, String filename) throws IOException, Exception{
		return false;
	}

}