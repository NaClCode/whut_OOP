package com.whut.server.controller;

import java.net.ServerSocket;
import java.net.Socket;

import com.whut.server.service.DataProcessing;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

<<<<<<< HEAD

=======
>>>>>>> origin
@Slf4j
public class SystemServer {
    
    private ServerSocket server;
    private int port = Integer.parseInt(DataProcessing.config.get("server_port"));
    private int backlog = Integer.parseInt(DataProcessing.config.get("server_backlog"));

    public SystemServer() {
    }

    public void start() {
        try{
            
            server = new ServerSocket(port, backlog); //端口号 8080, 最大连接数 100
            while(true){
                Socket socket = server.accept();
                new ServerThread(socket).start();
            }
        }catch(IOException e){
            log.error("错误" + e.getMessage());
<<<<<<< HEAD

=======
>>>>>>> origin
        }
    }
}
