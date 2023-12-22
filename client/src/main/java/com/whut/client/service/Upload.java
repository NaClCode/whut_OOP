package com.whut.client.service;


import java.io.FileInputStream;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.Socket;

import javax.swing.JOptionPane;

import com.whut.client.GUI.utils.ProgressMonitorStream;
import com.whut.client.common.ClientMsg;
import com.whut.client.utils.Config;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Upload extends Thread{

    private Socket socket;
    private String uploadfileName;
    private String fileName;

    public Upload(String uploadfileName, String fileName){
        super();
        this.uploadfileName = uploadfileName;
        this.fileName = fileName;
        
    }

    @Override
    public void run() {
        try {
            Config config = DataProcessing.config;
            String serverIp = config.get("server_ip");
            int serverPort = Integer.parseInt(config.get("server_port"));

            socket = new Socket(serverIp, serverPort);
            socket.setSoTimeout(10000);
            log.info("连接服务器上传");
            FileInputStream in = new FileInputStream(uploadfileName);

            ProgressMonitorStream stream = new ProgressMonitorStream("上传" + fileName, in.available());
            stream.getProgressMonitor().setMillisToDecideToPopup(0);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
			out.write(ClientMsg.UPLOAD);
            out.writeObject(fileName);
            out.flush();
            log.info("上传文件名：" + fileName);
            byte[] bytes = new byte[1024];
            while(stream.setProgress(in.read(bytes, 0, 1024)) != -1){
                out.write(bytes, 0, 1024);
            }

            log.info("上传成功");
            in.close();
            JOptionPane.showMessageDialog(null, "上传文件" + fileName + "成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            socket.close();
            this.interrupt();
        } catch (Exception e) {
            log.error("上传失败", e);
        }
        
    }
    
}
