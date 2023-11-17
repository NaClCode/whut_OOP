package com.whut.utils;

import com.whut.DataProcessing;
public class Role {

    public static String getRole(){
        String tip_menu = "1. 普通用户 \n 2. 管理员 \n 3. 操作员 \n请输入角色名: ";
        
        while(true){
            System.out.print(tip_menu);
            String input = DataProcessing.scanner.nextLine();
            if(!input.matches("[1-3]")){
                System.err.println("输入错误, 请重新输入!");
                System.err.print(tip_menu);	
            }else{
                int role = Integer.parseInt(input);
                switch(role){
                    case 1:
                        return "Browser";
                    case 2:
                        return "Administrator";
                    case 3:
                        return "Operator";
                }
            }
        }
    }




}
