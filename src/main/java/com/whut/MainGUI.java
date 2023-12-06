package com.whut;

import com.whut.GUI.Login;
public class MainGUI{
    public static void main(String[] args) {
        	
		try {
			DataProcessing.Init(args);
		} catch (Exception e) {
            e.printStackTrace();
			System.err.println("初始化异常:" + e.getMessage());
		}

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login.getInstance().setVisible(true);
            }
        });
    }
}
