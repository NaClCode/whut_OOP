package com.whut.dao;

import com.whut.DataProcessing;

//实验一：实现Operator类
public class Operator extends User{

	public Operator(String name, String password, String role){
		super(name, password, role);
	}

	// 实验三中实现此函数，完成文件上传功能
	public void uploadFile() {
		System.out.println("上传文件");
	}

	@Override
	public void showMenu() {
		String tip_system = "系统操作员菜单";
        String tip_menu = "请选择菜单: ";
        String infos = "****欢迎进入"+ tip_system + "****\n\t" + 
                        "1. 下载文件\n\t" + 
                        "2. 文件列表\n\t" + 
                        "3. 修改密码\n\t" + 
						"4. 上传文件\n\t" +
                        "5. 退出\n" + 
                        "****************************";

        System.out.println(infos);
        System.out.print(tip_menu);
        String input = null;

        while (true){
            input = DataProcessing.scanner.nextLine().trim();
            if(!input.matches("[1-5]")){
                System.err.print(tip_menu);
            }else{
                int nextInt = Integer.parseInt(input);
                switch(nextInt){
                    case 1:
                        System.out.println("下载文件");
                        System.out.print("请输入档案名：");
                        String docID = DataProcessing.scanner.nextLine();

                        if(downloadFile(docID)) System.out.println("下载成功");
                        else System.out.println("下载失败");
                        break;
                    case 2:
                        System.out.println("文件列表");
                        showFileList();
                        break;
                    case 3:
                        System.out.println("修改口令");
                        changeUserPass();
                        break;
                    case 4:
						System.out.println("上传文件");
                        uploadFile();
                        break;
					case 5:
						System.out.println("退出");
						return;
                }
                System.out.println(infos);
                System.out.print(tip_menu);
            }
		}
	}
}
