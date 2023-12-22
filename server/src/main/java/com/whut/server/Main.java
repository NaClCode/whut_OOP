package com.whut.server;

import org.apache.log4j.BasicConfigurator;

import com.whut.server.controller.SystemServer;
import com.whut.server.service.DataProcessing;

public class Main {
    public static void main( String[] args ){
        BasicConfigurator.configure();
        try{
            DataProcessing.Init(args);
            SystemServer systemServer = new SystemServer();
            systemServer.start();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
