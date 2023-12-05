package com.whut.GUI.UserGUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.whut.DataProcessing;
import com.whut.utils.ImageUtil;

public class AddUserPanel extends JPanel{
    JTextField usernameField;
    JPasswordField passwordField;

    public AddUserPanel(){
        this.setLayout(null);

        URL url  = getClass().getResource("../Image/add.png");
        ImageIcon add = ImageUtil.createAutoAdjustIcon(url, true);
        JLabel pic = new JLabel();;
        pic.setIcon(add);
        pic.setBounds(75, 10, 130, 130);
        this.add(pic);
        
        JLabel label = new JLabel("用户名:");
        label.setBounds(40, 150, 80, 30);
        label.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label);

        JLabel label_1 = new JLabel("密码:");
        label_1.setBounds(40, 190, 80, 30);
        label_1.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label_1);

        JLabel label_2 = new JLabel("角色:");
        label_2.setBounds(40, 230, 80, 30);
        label_2.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label_2);

        usernameField = new JTextField();
        usernameField.setBounds(100, 150, 140, 30);
        this.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 190, 140, 30);
        this.add(passwordField);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Administrator");
        comboBox.addItem("Browser");
        comboBox.addItem("Operator");
        comboBox.setBounds(100, 230, 140, 30);
        this.add(comboBox);

        JButton button1 = new JButton("添加");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "是否添加", "添加", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                    String name = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String role = (String) comboBox.getSelectedItem();
                    add(name, password, role);
                }
			}
		});
        button1.setBounds(100, 280, 80, 30);
        button1.setFont(new Font("楷体",0, 15));
        this.add(button1);

    }

    private void add(String name, String password, String role){
        if(name.equals("") || password.equals(""))
            JOptionPane.showMessageDialog(null, "您的输入的账号或密码为空", "错误", JOptionPane.ERROR_MESSAGE);
        else{
            if(DataProcessing.insertUser(name, password, role)){
                JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "换个用户名?", "用户重名", JOptionPane.ERROR_MESSAGE);
            }
        } 
        
    }
}
