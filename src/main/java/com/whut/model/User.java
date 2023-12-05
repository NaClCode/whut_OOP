package com.whut.model;

import com.whut.DataProcessing;
import javax.swing.ProgressMonitorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Frame;
public abstract class User{
	private String name;
	private String password;
	private String role;

	User(String name, String password, String role){
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public boolean changeUserInfo(String password){
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

	public boolean downloadFile(String ID) throws IOException{
		Doc doc = DataProcessing.searchDoc(ID);
		if (doc != null) {
			String server_filepath = DataProcessing.config.get("server_filepath");
			String download_filepath = DataProcessing.config.get("download_filepath");
			String path =  server_filepath + "\\" + doc.getUploadFileName();
			String downPath = download_filepath + "\\" + doc.getFilename();
			File file = new File(path);
			File downloadFile = new File(downPath);
            if(file.exists() && !downloadFile.exists()){
                FileOutputStream fop = new FileOutputStream(downloadFile);
                FileInputStream in = new FileInputStream(file);
                ProgressMonitorInputStream pm = new ProgressMonitorInputStream(
                        new Frame(), "文件下载中，请稍后...", in);
                int c = 0;
                pm.getProgressMonitor().setMillisToDecideToPopup(0);
                byte[] bytes = new byte[1024];
                while ((c = pm.read(bytes)) != -1) {
                    fop.write(bytes, 0, c);
                    //System.out.println(c); 延时下载
                }
                fop.close(); 
                pm.close(); 
                return true;
            }else{
                return false;
            }
			
		}
		return false;
	}


	public boolean uploadFile(String ID, String description, String filePath, String filename) throws IOException{
		return false;
	}

}