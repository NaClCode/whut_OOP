package com.whut.GUI.UserGUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.whut.DataProcessing;
import com.whut.model.User;
import com.whut.utils.ImageUtil;

public class UpdateUserPanel extends JPanel{
    
    JTextField passwordField;
    JComboBox<String> usernameComboBox;
    ArrayList<User> userList;
    User user;
    JComboBox<String> comboBox;

    public UpdateUserPanel(User user){
        this.setLayout(null);
        this.user = user;

        URL url  = getClass().getResource("../Image/update.png");
        ImageIcon add = ImageUtil.createAutoAdjustIcon(url, true);
        JLabel pic = new JLabel();;
        pic.setIcon(add);
        pic.setBounds(100, 20, 110, 110);
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

        usernameComboBox = new JComboBox<String>();
        
        usernameComboBox.setBounds(100, 150, 140, 30);
        usernameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = userList.get(usernameComboBox.getSelectedIndex());
                passwordField.setText(user.getPassword());
                comboBox.setSelectedItem(user.getRole());
            }
        });
        this.add(usernameComboBox);

        passwordField = new JTextField();
        passwordField.setBounds(100, 190, 140, 30);
        this.add(passwordField);

        comboBox = new JComboBox<>();
        comboBox.addItem("Administrator");
        comboBox.addItem("Browser");
        comboBox.addItem("Operator");
        comboBox.setBounds(100, 230, 140, 30); 
        this.add(comboBox);

        //加载用户清单
        updateUserList();
    

        JButton button1 = new JButton("修改");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "是否更新", "更新", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                    String userName = (String)usernameComboBox.getSelectedItem();
                    String password = passwordField.getText();
                    String role = (String)comboBox.getSelectedItem();
                    
                    update(userName, password, role);
                }
			}
		});
        button1.setBounds(100, 280, 80, 30);
        button1.setFont(new Font("楷体",0, 15));
        this.add(button1);
    }

    //更新用户清单
    private void updateUserList(){
        userList = DataProcessing.getAllUsers();
        userList.removeIf(i -> user.getName().equals(i.getName()));
        usernameComboBox.removeAllItems();
        for(User i:userList)
            usernameComboBox.addItem(i.getName());
        if(userList.size() > 0){
            passwordField.setText(userList.get(0).getPassword());
            comboBox.setSelectedItem(userList.get(0).getRole());
        }else{
            passwordField.setText("");
            comboBox.setSelectedIndex(0);
        }
        
        repaint();
    }

    private void update(String name, String password, String role) {
        if(name.equals("") || password.equals(""))
            JOptionPane.showMessageDialog(null, "没有可以修改的", "错误", JOptionPane.ERROR_MESSAGE);
        else{
            if(DataProcessing.updateUser(name, password, role)){
                updateUserList(); //更新用户清单
                JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }else
                JOptionPane.showMessageDialog(null, "修改错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
           
    }
    

}