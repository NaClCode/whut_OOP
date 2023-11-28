import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends JFrame{
    
    public Login(){
        super();
        JPanel panel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
        setTitle("Login");
        setResizable(false);

        URL url  = getClass().getResource("Image/icon.png");
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());
  
    }
}
