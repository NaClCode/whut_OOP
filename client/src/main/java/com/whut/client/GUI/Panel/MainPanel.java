<<<<<<<< HEAD:client/src/main/java/com/whut/client/GUI/Panel/MainPanel.java
package com.whut.client.GUI.Panel;
========
package com.whut.client.GUI;
>>>>>>>> origin:client/src/main/java/com/whut/client/GUI/MainPanel.java

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

<<<<<<<< HEAD:client/src/main/java/com/whut/client/GUI/Panel/MainPanel.java
import com.whut.client.GUI.MainFrame;
========
>>>>>>>> origin:client/src/main/java/com/whut/client/GUI/MainPanel.java
import com.whut.client.model.User;
import com.whut.client.utils.ImageUtil;

public class MainPanel extends JPanel {
    
    private static MainPanel instance = new MainPanel();
    private MainPanel() {
        super();
    }

    private void init(User user){
        removeAll();
        setLayout(null);
        JButton button1 = new JButton();
        URL mainUrl  = MainFrame.class.getResource("Image/main.png");
        ImageIcon pic = ImageUtil.createAutoAdjustIcon(mainUrl, true);
        button1.setIcon(pic);
        button1.setBounds(85, 20, 120, 120);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "by NaCl", "欢迎使用档案管理系统", JOptionPane.INFORMATION_MESSAGE);
            }
        });
       
        JLabel label2 = new JLabel("用户:");
        label2.setBounds(50, 120, 150, 100);
        label2.setFont(new Font("楷体", Font.PLAIN,  20));

        JLabel label3 = new JLabel("角色:");
        label3.setBounds(50, 160, 150, 100);
        label3.setFont(new Font("楷体", Font.PLAIN, 20));

        
        JLabel label4 = new JLabel(user.getName());
        label4.setBounds(120, 155, 140, 30);
        label4.setFont(new Font("楷体", Font.PLAIN, 20));
        label4.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label5 = new JLabel(user.getRole());
        label5.setBounds(120, 195, 140, 30);
        label5.setFont(new Font("楷体", Font.PLAIN, 20));
        label5.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label6 = new JLabel("密码:");
        label6.setBounds(50, 200, 150, 100);
        label6.setFont(new Font("楷体", Font.PLAIN, 20));    

        JButton button2 = new JButton("修改密码");
        button2.setBounds(120, 235, 140, 30);
        button2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordDialog.getInstance(user).setVisible(true);      
            }
            
        });


        add(button1);
        add(label2);
        add(label3);
        add(label4);
        add(label5);
        add(label6);
        add(button2);

        setBounds(0, 0, 300, 400);

    }

    public static MainPanel getInstance(User user) {
        MainPanel.instance.init(user);
        return instance;
    }
}

class PasswordDialog extends JDialog implements ActionListener {
    
    private JPasswordField jPasswordField = new JPasswordField();
    private JPasswordField jPasswordField1 = new JPasswordField();;
    private User user;
    private static PasswordDialog instance = new PasswordDialog();
    private String ok = "确定";
    private String cancel = "取消";

    private PasswordDialog() {
        super();
    }

    private void init(User user){
        this.user = user;
        setLayout(null);
        setTitle("修改密码");
        setModal(true);
        setSize(300, 160);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
        JLabel jLabel = new JLabel("新的密码:");
        jLabel.setFont(new Font("楷体", Font.PLAIN, 15));
        jLabel.setBounds(30, 0, 180, 45);

        JLabel jLabel1 = new JLabel("再次输入:");
        jLabel1.setFont(new Font("楷体", Font.PLAIN, 15));
        jLabel1.setBounds(30, 35, 180, 45);

        jPasswordField.setBounds(120, 10, 130, 25);
        jPasswordField1.setBounds(120, 45, 130, 25);
        jPasswordField.setText("");
        jPasswordField1.setText("");
        
        JButton okBut = new JButton(ok);
        JButton cancelBut = new JButton(cancel);
        okBut.setBounds(30, 80, 90, 25);
        cancelBut.setBounds(160, 80, 90, 25);
        // 给按钮添加响应事件
        okBut.addActionListener(this);
        cancelBut.addActionListener(this);
        
        add(jLabel);
        add(okBut);
        add(cancelBut);
        add(jLabel1);
        add(jPasswordField);
        add(jPasswordField1);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ok.equals(e.getActionCommand())) {
            String newPassword = new String(jPasswordField.getPassword());
            String newPassword1 = new String(jPasswordField1.getPassword());
            if(newPassword.equals("") || newPassword1.equals("")){
                JOptionPane.showMessageDialog(null, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            }else{
                if(newPassword.equals(newPassword1)){
                    try {
                        if(user.changeUserInfo(newPassword)){
                            JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                            this.setVisible(false);
                            this.dispose();
                        }else{
                            JOptionPane.showMessageDialog(null, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }  
                    
                }else{
                    JOptionPane.showMessageDialog(null, "前后密码输入不一致", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (cancel.equals(e.getActionCommand())) {
            this.setVisible(false);
            this.dispose();
        }
    }

    public static PasswordDialog getInstance(User user){
        PasswordDialog.instance.init(user);
        return instance;
    }
}