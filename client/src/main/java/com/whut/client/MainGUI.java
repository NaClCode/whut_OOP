package com.whut.client;

import org.apache.log4j.BasicConfigurator;

import com.whut.client.GUI.Login;
import com.whut.client.service.DataProcessing;


public class MainGUI{
    public static void main(String[] args) {
        BasicConfigurator.configure();
		DataProcessing.Init(args);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login.getInstance().setVisible(true);
            }
        });
    }
}
