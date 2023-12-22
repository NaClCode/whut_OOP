package com.whut.server.controller;

import java.net.Socket;

import java.util.HashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.whut.server.common.ClientMsg;
import com.whut.server.common.ServerMsg;
import com.whut.server.service.DataProcessing;
import com.whut.server.utils.Email;

import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerThread extends Thread{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean exit;
    private ConcurrentHashMap<String, Consumer<String[]>> actionMappings = new ConcurrentHashMap<>();
    private Email email = new Email();

    
    public ServerThread(Socket socket) {
        this.socket = socket;

        actionMappings.put(ClientMsg.SEARCH_USER, 
                        (String[] args) -> {sendSuccessMsg(DataProcessing.searchUser(args[0], args[1]));});
        actionMappings.put(ClientMsg.INSERT_USER,
                        (String[] args) -> {sendSuccessMsg(DataProcessing.insertUser(args[0], args[1], args[2]));});
        actionMappings.put(ClientMsg.DELETE_USER,
                        (String[] args) -> {sendSuccessMsg(DataProcessing.deleteUser(args[0]));});
        actionMappings.put(ClientMsg.UPDATE_USER, 
                        (String[] args) -> {sendSuccessMsg(DataProcessing.updateUser(args[0], args[1], args[2]));});
        actionMappings.put(ClientMsg.GET_ALL_USER,
                        (String[] args) -> {sendSuccessMsg(DataProcessing.getAllUser());});
        actionMappings.put(ClientMsg.SEARCH_DOC, 
                        (String[] args) -> {sendSuccessMsg(DataProcessing.searchDoc(args[0]));});
        actionMappings.put(ClientMsg.GET_ALL_DOC, 
                        (String[] args) -> {sendSuccessMsg(DataProcessing.getAllDocs());});
        actionMappings.put(ClientMsg.EXIT, 
                        (String[] args) -> {exit(args);});
        actionMappings.put(ClientMsg.INSERT_DOC, 
                        (String[] args) -> {sendSuccessMsg(DataProcessing.insertDoc(args[0], args[1], args[2], args[3], args[4], args[5]));});
        actionMappings.put(ClientMsg.SEND_CODE, 
                        (String[] args) -> {sendSuccessMsg(email.sendEmail(args[0]));});
        actionMappings.put(ClientMsg.VERIFY_CODE, 
                        (String[] args) -> {sendSuccessMsg(email.verifyCode(args[0], args[1]));});
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    private void sendSuccessMsg(Object msg){
        sendMsg(ServerMsg.SUCCESS, msg);
    }

    private void sendMsg(int type, Object msg){
        try {
            HashMap<String, Object> rmsg = new HashMap<>();
            rmsg.put("Status", type);
            rmsg.put("Data", msg);
            out.writeObject(rmsg);
        } catch (IOException e) {
            log.error("Error", e.getMessage());
        }

    }

    @Override
    
    public void run() {
        try {
            exit = false;

            int mod = in.read();
            switch(mod){
                case ClientMsg.DATABASES: database(); break;
                case ClientMsg.UPLOAD: upload(); break;
                case ClientMsg.DOWNLOAD: download(); break;
            }
            this.interrupt();

        } catch (Exception e) {
            log.error("错误", e);
        }
    }

    private void exit(String[] data){
        exit = true; 
        try {
            sendMsg(ServerMsg.EXIT, null);
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            log.error("关闭失败", e);
        }     
    }

    @SuppressWarnings("unchecked")
    private void database(){
        try{
            log.info("数据库");
            do{ 
                if(in.markSupported()) in.reset();
                HashMap<String, Object> msg = (HashMap<String, Object>) in.readObject();
                String type = (String) msg.get("Type");
                String[] args = (String[]) msg.get("Data");
                actionMappings.get(type).accept(args);            
            }while(!exit);
            log.info("退出");
        }catch(IOException | ClassNotFoundException e){
            log.error("错误", e);
        }
        
    }

    public void upload(){
        log.info("上传");
        try {
            String uploadFileName = (String) in.readObject();
            String filePath = DataProcessing.config.get("server_filepath") + "//" + uploadFileName;

            File file = new File(filePath);
            byte[] bytes = new byte[1024];
            FileOutputStream outputStream = new FileOutputStream(file);
            while(in.read(bytes, 0, 1024) != -1){
                outputStream.write(bytes, 0, 1024);
            }
            in.close();
            outputStream.close();
            log.info("上传成功");
        } catch (IOException | ClassNotFoundException e) {
            log.error("上传失败", e);
        } 
    }

    public void download(){
        log.info("下载");
        try {
            String downloadFileName = (String) in.readObject();
            String filePath = DataProcessing.config.get("server_filepath") + "//" + downloadFileName;
            log.info(downloadFileName);

            File file = new File(filePath);
            byte[] bytes = new byte[1024];
            FileInputStream inputStream = new FileInputStream(file);
                    
            out.writeObject(inputStream.available());

            while(inputStream.read(bytes, 0, 1024) != -1){
                out.write(bytes, 0, 1024);
            }
            in.close();
            out.close();
            inputStream.close();
            log.info("下载成功");
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }

}
