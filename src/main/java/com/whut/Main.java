package com.whut;

import com.whut.model.User;
import com.whut.utils.Config;

public class Main{

	public static Config config = new Config();
	public static void main(String[] args){


		config.init(args);		

		try {
			DataProcessing.Init();
		} catch (Exception e) {
			System.err.println("初始化异常:" + e.getMessage());
		}
		
		// 实验一: 此处编写代码实现系统的启动
		String infos = "****欢迎进入档案管理系统****\n\t" + 
		"1. 登录\n\t" + 
		"2. 退出\n" + 
		"****************************";
		String tip_menu = "请选择菜单: ";
		String input;
		User user;

		while(true){
			System.out.println(infos);
			System.out.print(tip_menu);	
			input = DataProcessing.scanner.nextLine().trim();
			if(!input.matches("[1-2]")){
				System.err.println("输入错误, 请重新输入!");
				System.err.print(tip_menu);	
			}else{
				int nextInt = Integer.parseInt(input);
				switch(nextInt){
					case 1:
						System.out.print("请输入用户名: ");
						String username = DataProcessing.scanner.nextLine().trim();
						System.out.print("请输入口令: ");
						String password = DataProcessing.scanner.nextLine().trim();
						user = DataProcessing.searchUser(username, password);
						if(user == null){
							System.err.println("用户名或口令错误!");
						}else user.showMenu();
						break;
					case 2:
						System.out.println("谢谢使用!");
						return;
				}
			}
		}
	}
}