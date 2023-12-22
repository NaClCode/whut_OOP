package com.whut.client.GUI;

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

import com.whut.client.service.DataProcessing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Register extends JFrame{
    private static Register instance = new Register();
    private JPasswordField textPassword;
    private JTextField textUsernamer;
    private JTextField textEmail;
    private JTextField textCode;
    private JButton button1;
    private JButton button2;
    private JButton button3;


    public Register() {   
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            log.error("无法设置主题", ex);
        }
        init();
    }

    public void init(){
    
        URL url  = MainFrame.class.getResource("Image/icon.png");
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(400,300);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
        setTitle("Register");
        setResizable(false);

                //内容面板
        JPanel content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout(0, 0));
		setContentPane(content);

        JPanel panel = new JPanel();
		content.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);


        JLabel label1 = new JLabel("账号:");
		label1.setBounds(70, 15, 40, 20);
        label1.setFont(new Font("楷体", Font.BOLD, 15));
		panel.add(label1);

		JLabel label2 = new JLabel("密码:");
		label2.setBounds(70, 65, 40, 20);
        label2.setFont(new Font("楷体", Font.BOLD, 15));
		panel.add(label2);

        JLabel label3 = new JLabel("邮箱:");
		label3.setBounds(70, 115, 40, 20);
        label3.setFont(new Font("楷体", Font.BOLD, 15));
        panel.add(label3);

        JLabel label4 = new JLabel("验证码:");
		label4.setBounds(55, 165, 60, 20);
        label4.setFont(new Font("楷体", Font.BOLD, 15));
        panel.add(label4);


        textUsernamer = new JTextField();
		textUsernamer.setToolTipText("请输入账号");
        textUsernamer.setBounds(120, 10, 180, 30);
        panel.add(textUsernamer);


        textPassword = new JPasswordField();
        textPassword.setBounds(120, 60, 180, 30);
        panel.add(textPassword);

        textEmail = new JTextField();
        textEmail.setToolTipText("请输入邮箱");
        textEmail.setBounds(120, 110, 180, 30);
        panel.add(textEmail);

        textCode = new JTextField();
        textCode.setToolTipText("请输入验证码");
        textCode.setBounds(120, 160, 110, 30);
        panel.add(textCode);


        button1 = new JButton("注册");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textEmail.getText();
                String code = textCode.getText();
                String name = textUsernamer.getText();
                String password = new String(textPassword.getPassword());
                if(email.equals("") && code.equals("")){
                    JOptionPane.showMessageDialog(null, "请输入邮箱与验证码", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    try {
                        if(DataProcessing.verifyCode(email, code)){
                            if(DataProcessing.insertUser(name, password, "Browser")){
                                JOptionPane.showMessageDialog(null, "注册成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                                textEmail.setText("");
                                textCode.setText("");
                                textUsernamer.setText("");
                                textPassword.setText("");
                            }else{
                                JOptionPane.showMessageDialog(null, "用户名冲突", "失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "验证码不一致", "失败", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception e1) {
                        log.error("发送验证码失败", e);
                    }
                }
			}
		});
        button1.setBounds(50, 210, 100, 30);
        panel.add(button1);

        button2 = new JButton("获取");
        button2.setBounds(240, 158, 60, 33);
        button2.setFont(new Font("楷体", Font.BOLD, 13));
        button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String email = textEmail.getText();
                if(email.equals("")){
                    JOptionPane.showMessageDialog(null, "请输入邮箱", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    try {
                        if(DataProcessing.sendCode(textEmail.getText())){
                            JOptionPane.showMessageDialog(null, "发送验证码", "成功", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(null, "发送验证码失败", "失败", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e1) {
                        log.error("发送验证码失败", e1);
                    }
                }
                
            }
        });
        panel.add(button2);

        button3 = new JButton("返回");
        button3.setBounds(200, 210, 100, 30);
        button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Login.getInstance().setVisible(true);
                setVisible(false);
                textEmail.setText("");
                textCode.setText("");
                textUsernamer.setText("");
                textPassword.setText("");
                
            }
        });
        panel.add(button3);
    }

    

    public static Register getInstance(){
        return instance;
    }
}
