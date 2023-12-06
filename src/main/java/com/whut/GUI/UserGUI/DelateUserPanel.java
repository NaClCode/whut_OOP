package com.whut.GUI.UserGUI;

import java.awt.Color;
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
import javax.swing.border.LineBorder;

import com.whut.DataProcessing;
import com.whut.model.User;
import com.whut.utils.ImageUtil;

public class DelateUserPanel extends JPanel{
    private JComboBox<String> usernameComboBox;
    private ArrayList<User> userList;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private User user;
    private static DelateUserPanel instance = new DelateUserPanel();

    private DelateUserPanel(){
        super();
    }

    private void init(User user){
        removeAll();
        this.setLayout(null);
        this.user = user;

        URL url  = getClass().getResource("../Image/remove.png");
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
        this.add(usernameComboBox);

        passwordLabel = new JLabel();
        passwordLabel.setBounds(100, 190, 140, 30);
        LineBorder bonder = new LineBorder(Color.GRAY);
        passwordLabel.setBorder(bonder);
        this.add(passwordLabel);

        roleLabel = new JLabel();
        roleLabel.setBounds(100, 230, 140, 30);
        roleLabel.setBorder(bonder);
        this.add(roleLabel);

        //加载用户清单
        updateUserList();

        JButton button1 = new JButton("删除");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "是否删除", "删除", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                    String userName = (String)usernameComboBox.getSelectedItem();
                    if(userName != null){
                        delete(userName);
                    }else{
                        JOptionPane.showMessageDialog(null, "请选择要删除的用户", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                    
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
            passwordLabel.setText(userList.get(0).getPassword());
            roleLabel.setText(userList.get(0).getRole());
        }else{
            passwordLabel.setText("");
            roleLabel.setText("");
        }
        repaint();
    }

    private void delete(String name) {
        if(name.equals(""))
            JOptionPane.showMessageDialog(null, "没有可以删除的", "错误", JOptionPane.ERROR_MESSAGE);
        else{
            if(DataProcessing.deleteUser(name)){
                updateUserList(); //更新用户清单
                JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }else JOptionPane.showMessageDialog(null, "修改错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DelateUserPanel getInstance(User user){
        DelateUserPanel.instance.init(user);
        return instance;
    }
}