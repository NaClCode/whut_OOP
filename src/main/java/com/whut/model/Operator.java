package com.whut.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whut.DataProcessing;

//实验一：实现Operator类
public class Operator extends User{

	public Operator(String name, String password, String role){
		super(name, password, role);
	}

	public boolean uploadFile(String ID, String description, String uploadPath, String fileName) throws IOException{

		Date data = new Date();
		long time = data.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd");
		String serverFileName = ft.format(data) + "-" + this.getName() + "-" + ID + "-" + fileName; //时间-用户名-ID-上传文件名
        String server_filepath = DataProcessing.config.get("server_filepath");
		File file = new File(uploadPath);
		File serverFile = new File(server_filepath + "\\" + serverFileName);
		if(file.exists() && serverFile.createNewFile()){
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));

			byte[] bytes = new byte[(int) file.length()];
			bufferedInputStream.read(bytes);
			bufferedOutputStream.write(bytes);
			bufferedInputStream.close();
			bufferedOutputStream.close();
		}else{
            return false;
        }
		return DataProcessing.insertDoc(new Doc(ID, getName(), description, fileName, time, serverFileName));
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
        String input = null;

        while (true){
            System.out.println(infos);
            System.out.print(tip_menu);
            input = DataProcessing.scanner.nextLine().trim();
            if(!input.matches("[1-5]")){
                System.err.print(tip_menu);
            }else{
                int nextInt = Integer.parseInt(input);
                switch(nextInt){
                    case 1:
                        System.out.println("下载文件");
                        System.out.print("请输入档案ID:");
                        String docID = DataProcessing.scanner.nextLine();
                        try {
                            if(downloadFile(docID)) System.out.println("下载成功");
                            else System.out.println("ID无效");
                        } catch (IOException e) {
                            System.out.println("下载失败:" + e.getMessage());
                        }
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
                        System.out.print("请输入档案ID:");
                        String ID = DataProcessing.scanner.nextLine();
                        System.out.print("请输入文件路径(建议绝对路径):");
                        String filePath = DataProcessing.scanner.nextLine();
                        System.out.print("请输入文件名:");
                        String fileName = DataProcessing.scanner.nextLine();
                        System.out.print("请输入文件内容的描述:");
                        String description = DataProcessing.scanner.nextLine();
                        try {
                            System.out.println("上传中...");
                            if(uploadFile(ID, description, filePath, fileName)){
                                System.out.println("上传成功");
                            }else{
                                System.out.println("上传失败");
                            }
                        } catch (IOException e) {
                            System.out.println("上传失败:" + e.getMessage());
                        }
                        break;
					case 5:
						System.out.println("退出");
						return;
                }
            }
		}
	}
}
