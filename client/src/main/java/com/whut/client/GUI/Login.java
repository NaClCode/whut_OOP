package com.whut.client.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.whut.client.model.User;
import com.whut.client.service.DataProcessing;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.WindowAdapter;

@Slf4j
public class Login extends JFrame{
    
    private JPasswordField testPassword;
    private JTextField textUsernamer;
    private JButton button1;
    private JButton button2;

    private static Login instance = new Login();

    private Login(){
        super();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            log.error("无法设置主题", ex);
        }
        init();
    }

    private void init(){
        
        while(!DataProcessing.connectServer()){
            if(JOptionPane.showConfirmDialog(null, "无法连接服务器，是否重试", "错误", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                log.info("重新连接");
            }else{
                System.exit(0);
            }
        }

        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                DataProcessing.disconnectServer();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                DataProcessing.disconnectServer();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(400,300);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
        setTitle("Login");
        setResizable(false);

        URL url  = MainFrame.class.getResource("Image/icon.png");
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());

        //内容面板
        JPanel content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout(0, 0));
		setContentPane(content);

        JPanel panel = new JPanel();
		content.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);


        //文字
		JLabel label1 = new JLabel("档案管理系统", JLabel.CENTER);
		label1.setBounds(40, 30, 300, 40);
        label1.setToolTipText("by NaCl");
        label1.setFont(new Font("楷体", Font.BOLD, 30));
		panel.add(label1);

        JLabel label2 = new JLabel("账号:");
		label2.setBounds(70, 100, 40, 20);
        label2.setFont(new Font("楷体", Font.BOLD, 15));
		panel.add(label2);

		JLabel label = new JLabel("密码:");
		label.setBounds(70, 150, 40, 20);
        label.setFont(new Font("楷体", Font.BOLD, 15));
		panel.add(label);

        textUsernamer = new JTextField();
		textUsernamer.setToolTipText("请输入账号");
        textUsernamer.setBounds(120, 95, 180, 30);
        panel.add(textUsernamer);


        testPassword = new JPasswordField();
        testPassword.setBounds(120, 145, 180, 30);
        panel.add(testPassword);


        button1 = new JButton("登录");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
        button1.setBounds(80, 200, 80, 30);
        button1.setFont(new Font("楷体",0, 15));
        panel.add(button1);

        button2 = new JButton("注册");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register.getInstance().setVisible(true);
                Login.instance.setVisible(false);
			}
		});
        button2.setBounds(210, 200, 80, 30);
        button2.setFont(new Font("楷体",0, 15));
        panel.add(button2);

    }

    private void login() {
        String username = textUsernamer.getText();
        String password = new String(testPassword.getPassword());
        if(username.equals("") || password.equals(""))
            JOptionPane.showMessageDialog(null, "您输入的账号或密码为空", "错误", JOptionPane.ERROR_MESSAGE);
        else{
            try {
            User user = DataProcessing.searchUser(username, password);
                if(user == null){
                    JOptionPane.showMessageDialog(null, "您输入的账号或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                }else{
                    setVisible(false);
                    textUsernamer.setText("");
                    testPassword.setText("");
                    MainFrame.getInstance(user).setVisible(true);
                }
            } catch (Exception e) {
                log.error("错误", e);
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

    public static Login getInstance(){
        return instance;
    }
}
