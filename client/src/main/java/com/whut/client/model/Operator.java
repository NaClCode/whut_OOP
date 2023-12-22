package com.whut.client.model;


import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whut.client.service.DataProcessing;
import com.whut.client.service.Upload;

public class Operator extends User{

	public Operator(String name, String password, String role){
		super(name, password, role);
	}
	
	@Override
	public boolean uploadFile(String ID, String description, String uploadPath, String fileName) throws IOException, Exception{

        Date data = new Date();
		long time = data.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd");
		String serverFileName = ft.format(data) + "-" + getName() + "-" + ID + "-" + fileName; //时间-用户名-ID-上传文件名
        if(DataProcessing.insertDoc(new Doc(ID, getName(), description, fileName, time, serverFileName))){
            File file = new File(uploadPath);
            if(file.exists()){
                new Upload(uploadPath, serverFileName).start(); 
            }else{
                return false;
            }
            return true;
        }else{
            return false;
        }
	}

}
