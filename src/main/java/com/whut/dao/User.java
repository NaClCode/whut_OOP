package com.whut.dao;

import java.io.IOException;

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
	
	public boolean downloadFile(String ID){
		
		System.out.println("下载文件... ...");
		return true;
	}

	// 文件上传
	public void uploadFile(){
		// 此功能在Operator类中实现
		System.out.println("上传文件... ...");
	}

	// 文件列表
	public void showFileList(){
		// 实验三需要实现的功能
		System.out.println("列表... ...");
	}
	public abstract void showMenu();

	public void exitSystem(){
		System.out.println("系统退出, 谢谢使用 ! ");
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