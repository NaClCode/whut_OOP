package com.whut.client.GUI.Panel.UserGUI;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.whut.client.GUI.MainFrame;
import com.whut.client.model.User;
import com.whut.client.service.DataProcessing;
import com.whut.client.utils.ImageUtil;

public class UpdateUserPanel extends JPanel{
    
    private JTextField passwordField;
    private JComboBox<String> usernameComboBox;
    private Vector<User> userList;
    private User user;
    private JComboBox<String> comboBox;
    private JButton button1;
    private static UpdateUserPanel instance = new UpdateUserPanel();

    private UpdateUserPanel(){
        super();
    }

    private void init(User user){
        removeAll();

        this.setLayout(null);
        this.user = user;
        URL url  = MainFrame.class.getResource("Image/update.png");
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

        passwordField = new JTextField();
        passwordField.setBounds(100, 190, 140, 30);
        this.add(passwordField);

        comboBox = new JComboBox<>();
        comboBox.addItem("Administrator");
        comboBox.addItem("Browser");
        comboBox.addItem("Operator");
        comboBox.setBounds(100, 230, 140, 30); 
        this.add(comboBox);

        button1 = new JButton("更新");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "是否更新", "更新", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                    String userName = (String)usernameComboBox.getSelectedItem();
                    String password = passwordField.getText();
                    String role = (String)comboBox.getSelectedItem();
                    if(userName != null && password != null && role != null){
                        update(userName, password, role);
                    }else{
                        JOptionPane.showMessageDialog(null, "请选择要更新的用户", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    
                }
			}
		});
        button1.setBounds(100, 280, 80, 30);
        button1.setFont(new Font("楷体",0, 15));
        this.add(button1);
        
        usernameComboBox = new JComboBox<String>();
        //加载用户清单
        updateUserList();
        usernameComboBox.setBounds(100, 150, 140, 30);
        usernameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = usernameComboBox.getSelectedIndex();
                if(id != -1){
                    User user = userList.get(id);
                    passwordField.setText(user.getPassword());
                    comboBox.setSelectedItem(user.getRole());
                }else{
                    passwordField.setText("");
                    comboBox.setSelectedIndex(-1);

                }
            }
        });
        this.add(usernameComboBox);


    
    }

    //更新用户清单
    private void updateUserList(){
        try {
            userList = DataProcessing.getAllUsers();
            userList.removeIf(i -> user.getName().equals(i.getName()));
            usernameComboBox.removeAllItems();
            for(User i:userList)
                usernameComboBox.addItem(i.getName());
            if(userList.size() > 0){
                usernameComboBox.setSelectedIndex(0);
                passwordField.setText(userList.get(0).getPassword());
                comboBox.setSelectedItem(userList.get(0).getRole());
            }else{
                usernameComboBox.setSelectedIndex(-1);
                passwordField.setText("");
                comboBox.setSelectedIndex(0);
            }
            
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void update(String name, String password, String role) {
        if(name.equals("") || password.equals(""))
            JOptionPane.showMessageDialog(null, "没有可以修改的", "错误", JOptionPane.ERROR_MESSAGE);
        else{
            try {
                if(DataProcessing.updateUser(name, password, role)){
                updateUserList(); //更新用户清单
                JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            }else
                JOptionPane.showMessageDialog(null, "修改错误", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            
        }
           
    }

    public static UpdateUserPanel getInstance(User user){
        UpdateUserPanel.instance.init(user);
        return instance;
    }
    

}