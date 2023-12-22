package com.whut.server;

<<<<<<< HEAD
import org.apache.log4j.BasicConfigurator;

=======
>>>>>>> origin
import com.whut.server.controller.SystemServer;
import com.whut.server.service.DataProcessing;

public class Main {
    public static void main( String[] args ){
<<<<<<< HEAD
        BasicConfigurator.configure();
=======
>>>>>>> origin
        try{
            DataProcessing.Init(args);
            SystemServer systemServer = new SystemServer();
            systemServer.start();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
