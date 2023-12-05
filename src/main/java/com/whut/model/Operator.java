package com.whut.model;

import com.whut.DataProcessing;
import java.awt.Frame;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ProgressMonitorInputStream;
public class Operator extends User{

	public Operator(String name, String password, String role){
		super(name, password, role);
	}
	
	@Override
	public boolean uploadFile(String ID, String description, String uploadPath, String fileName) throws IOException{

        Date data = new Date();
		long time = data.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd");
		String serverFileName = ft.format(data) + "-" + getName() + "-" + ID + "-" + fileName; //时间-用户名-ID-上传文件名
        String server_filepath = DataProcessing.config.get("server_filepath");
        if(DataProcessing.insertDoc(new Doc(ID, getName(), description, fileName, time, serverFileName))){
            File file = new File(uploadPath);
            File serverFile = new File(server_filepath + "\\" + serverFileName);
            if(file.exists() && serverFile.createNewFile()){
                FileOutputStream fop = new FileOutputStream(serverFile);
                FileInputStream in = new FileInputStream(file);
                ProgressMonitorInputStream pm = new ProgressMonitorInputStream(
                        new Frame(), "文件上传中，请稍后...", in);
                int c = 0;
                pm.getProgressMonitor().setMillisToDecideToPopup(0);
                byte[] bytes = new byte[1024];
                while ((c = pm.read(bytes)) != -1) {
                    fop.write(bytes, 0, c);
                    //System.out.println(c); 延时上传
                }
                fop.close(); 
                pm.close(); 
            }else{
                return false;
            }
            return true;
        }else{
            return false;
        }
	}

}
