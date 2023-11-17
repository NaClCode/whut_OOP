package com.whut.dao;

import java.io.IOException;
import java.util.Enumeration;

import com.whut.DataProcessing;
import com.whut.utils.Role;

//实验一：在此处实现Administrator类
public class Administrator extends User {

    public Administrator(String name, String password, String role) {
        super(name, password, role);
    }

    @Override
    public void showMenu() {
        String tip_system = "系统管理员菜单";
        String tip_menu = "请选择菜单: ";
        String infos = "****欢迎进入"+ tip_system + "****\n\t" + 
                        "1. 修改用户\n\t" + 
                        "2. 删除用户\n\t" + 
                        "3. 新增用户\n\t" + 
                        "4. 列出用户\n\t"+ 
                        "5. 下载文件\n\t" + 
                        "6. 文件列表\n\t" + 
                        "7. 修改密码\n\t" + 
                        "8. 退出\n" + 
                        "****************************";

        String name, password, role;
        System.out.println(infos);
        System.out.print(tip_menu);
        String input = null;

        while (true){
            input = DataProcessing.scanner.nextLine().trim();
            if(!input.matches("[1-8]")){
                System.err.print(tip_menu);
            }else{
                int nextInt = Integer.parseInt(input);
                switch(nextInt){
                    case 1: //修改用户信息
                        System.out.println("修改用户");
                        System.out.print("请输入用户名：");
                        name = DataProcessing.scanner.nextLine();
                        System.out.print("请输入口令：");
                        password = DataProcessing.scanner.nextLine();
                        role = Role.getRole();

                        if(name.equals(this.getName())) System.out.println("不能修改自己");
                        else changeUserInfo(name, password, role);
                        break;
                    case 2:
                        System.out.println("删除用户");
                        System.out.print("请输入用户名：");
                        name = DataProcessing.scanner.nextLine();

                        if(name.equals(this.getName())) System.out.println("不能删除自己");
                        else delUser(name);
                        break;
                    case 3:
                        System.out.println("新增用户");
                        System.out.print("请输入用户名：");
                        name = DataProcessing.scanner.nextLine();
                        System.out.print("请输入口令：");
                        password = DataProcessing.scanner.nextLine();
                        role = Role.getRole();

                        addUser(name, password, role);
                        break;
                    case 4:
                        System.out.println("列出用户");
                        listUser();
                        break;
                    case 5:
                        System.out.println("下载文件");
                        System.out.print("请输入档案名：");
                        String docID = DataProcessing.scanner.nextLine();

                        if(downloadFile(docID)) System.out.println("下载成功");
                        else System.out.println("下载失败");
                        break;
                    case 6:
                        System.out.println("文件列表");
                        showFileList();
                        break;
                    case 7:
                        System.out.println("修改密码");
                        changeUserPass();
                        break;
                    case 8:
                        System.out.println("退出");
                        return;
                }
                System.out.println(infos);
                System.out.print(tip_menu);
            }

        }

    }

    public boolean changeUserInfo(String name, String password, String role){
        try {
            if(DataProcessing.updateUser(name, password, role)){
                System.out.println("修改成功");
                return true;
            }else{
                System.out.println("用户重名");
                return false;
            }
        } catch (IOException e) {
            System.out.println("文件保存失败:" + e.getMessage());
            return false;
        }
        
    }

    public boolean delUser(String name){
        try {
            if(DataProcessing.deleteUser(name)){
                System.out.println("删除成功");
                return true;
            }else{
                System.out.println("用户不存在");
                return false;
            }
        } catch (IOException e) {
            System.out.println("文件保存失败:" + e.getMessage());
            return false;
        }
    }

    public boolean addUser(String name, String password, String role){
        try {
            if(DataProcessing.insertUser(name, password, role)){
                System.out.println("添加成功");
                return true;
            }else{
                System.out.println("用户重名");
                return false;
            }
        } catch (IOException e) {
            System.out.println("文件保存失败:" + e.getMessage());
            return false;
        }
        
    }

    public void listUser(){
        Enumeration<User> usEnumeration = DataProcessing.getAllUser();

        User user;

        while(usEnumeration.hasMoreElements()){
            user = usEnumeration.nextElement();
            System.out.println(user.getName() + " " + user.getPassword() + " " + user.getRole());
        }
    }
}
