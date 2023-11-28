package com.whut.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import com.whut.DataProcessing;


public abstract class User{
	private String name;
	private String password;
	private String role;

	User(String name, String password, String role){
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public void changeUserPass(){
		System.out.print("请输入新密码：");
		String newPassword = DataProcessing.scanner.nextLine();

		if(this.changeUserInfo(newPassword)){
			System.out.println("操作成功");
		}else{
			System.out.println("操作失败");
		}

	}
	
	public boolean changeUserInfo(String password){
		try{
			if (DataProcessing.updateUser(name, password, role)){
				this.password = password;
				return true;
			}
			else return false;
		}catch(IOException e){
			return false;
		}	
	}
	
	public boolean downloadFile(String ID) throws IOException{
		Doc doc = DataProcessing.searchDoc(ID);
		if (doc != null) {
			String server_filepath = DataProcessing.config.get("server_filepath");
			String download_filepath = DataProcessing.config.get("download_filepath");
			String path =  server_filepath + "\\" + doc.getFilename();
			String downPath = download_filepath + "\\" + doc.getFilename();
			File file = new File(path);
			File downloadFile = new File(downPath);

			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));

			byte[] bytes = new byte[(int) file.length()];
			bufferedInputStream.read(bytes);
			bufferedOutputStream.write(bytes);
			bufferedInputStream.close();
			bufferedOutputStream.close();
			return true;
		}
		return false;
	}

	// 文件列表
	public void showFileList(){
		Enumeration<Doc> docEnumeration = DataProcessing.getAllDocs();

        Doc doc;
		System.out.println("ID\tCreator\tFileName\tDescription\tTime");
        while(docEnumeration.hasMoreElements()){
            doc = docEnumeration.nextElement();
            System.out.println(doc.getID() + ")\t" + doc.getCreator() + "\t" + doc.getFilename() + "\t" + 
								doc.getDescription() + "\t" + (new Date(doc.getTimestamp())));
        }
	}
	public abstract void showMenu();

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

}