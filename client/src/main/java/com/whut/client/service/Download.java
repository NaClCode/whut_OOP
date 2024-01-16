package com.whut.client.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.whut.client.GUI.utils.ProgressMonitorStream;
import com.whut.client.common.ClientMsg;
import com.whut.client.utils.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Download extends Thread{

    private Socket socket;
    private String fileName;
    private String downloadFileName;

    public Download(String downloadFileName, String fileName) {
        super();
        this.fileName = fileName;
        this.downloadFileName = downloadFileName;
    }
    
    @Override
    public void run() {
        try {
            Config config = DataProcessing.config;
            String serverIp = config.get("server_ip");
            int serverPort = Integer.parseInt(config.get("server_port"));

            socket = new Socket(serverIp, serverPort);
            socket.setSoTimeout(10000);
            log.info("连接服务器下载");


            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.write(ClientMsg.DOWNLOAD);
            out.writeObject(downloadFileName);
            out.flush();

            String downFilePath = config.get("download_filepath") + "//" + fileName;
            File file = new File(downFilePath);
            FileOutputStream outFile = new FileOutputStream(file);
            log.info("下载文件名：" + fileName);

            int size = (int)in.readObject();
            ProgressMonitorStream progressMonitorStream = new ProgressMonitorStream("下载" + fileName, size);
            progressMonitorStream.getProgressMonitor().setMillisToDecideToPopup(0);

            byte[] bytes = new byte[1024];
            int len;
            while((len = progressMonitorStream.setProgress(in.read(bytes))) != -1){
                outFile.write(bytes, 0, len);
                outFile.flush();
            }

            log.info("下载成功");
            JOptionPane.showMessageDialog(null, "下载文件" + fileName + "成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            outFile.close();
            in.close();
            socket.close();
            this.interrupt();
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }
}
