package com.whut.client;

<<<<<<< HEAD
import org.apache.log4j.BasicConfigurator;

=======
>>>>>>> origin
import com.whut.client.GUI.Login;
import com.whut.client.service.DataProcessing;


public class MainGUI{
    public static void main(String[] args) {
<<<<<<< HEAD
        BasicConfigurator.configure();
		DataProcessing.Init(args);
=======
        	
		DataProcessing.Init(args);

>>>>>>> origin
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login.getInstance().setVisible(true);
            }
        });
    }
}
