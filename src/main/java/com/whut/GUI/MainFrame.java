package com.whut.GUI;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.whut.GUI.FileGUI.DownloadPanel;
import com.whut.GUI.FileGUI.UploadPanel;
import com.whut.GUI.UserGUI.AddUserPanel;
import com.whut.GUI.UserGUI.DelateUserPanel;
import com.whut.GUI.UserGUI.ListUserPanel;
import com.whut.GUI.UserGUI.UpdateUserPanel;
import com.whut.model.User;

public class MainFrame extends JFrame {

    private JPanel content = new JPanel();;
    private JPanel panel = new JPanel();;
    private User user;
    private static MainFrame instance = new MainFrame();

    public MainFrame(){
        super();
        
        try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("无法设置主题");
        }
    }
    private void init(User user){
    
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(300,400);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
        setTitle("User");
        setResizable(false);

        URL url  = getClass().getResource("Image/icon.png");
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());

        //内容面板
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout(0, 0));
		setContentPane(content);

		content.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

        //菜单
        JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
        if(user.getRole().equals("Administrator")) 
            menuBar.add(AdminMenu());

        JMenu fileMenu = new JMenu("档案管理");
        if(user.getRole().equals("Operator")) 
            fileMenu.add(upload());
        fileMenu.add(download());
        menuBar.add(fileMenu);

        panel.add(getMainJPanel());
        JMenu mainMenu = new JMenu("主页面");
        mainMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                panel.remove(panel.getComponentAt(0, 0));
                panel.add(getMainJPanel());
                panel.repaint();
                panel.revalidate();
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {};
        });
        menuBar.add(mainMenu);

    }

    private JMenu AdminMenu() {
        JMenu userMenu = new JMenu("用户管理");
        JMenuItem addUser = new JMenuItem("新增用户");
        JMenuItem deleteUser = new JMenuItem("删除用户");
        JMenuItem updateUser = new JMenuItem("更新用户");
        JMenuItem searchUser = new JMenuItem("查询用户");
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaker(AddUserPanel.getInstance());
            }
        });
        updateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaker(UpdateUserPanel.getInstance(user));
            }
        });
        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaker(DelateUserPanel.getInstance(user));
            }
        });
        searchUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaker(ListUserPanel.getInstance());
            }
        });

        userMenu.add(addUser);
        userMenu.add(deleteUser);
        userMenu.add(updateUser);
        userMenu.add(searchUser);
        return userMenu;
    }

    private void panelMaker(JPanel jPanel){
        panel.remove(panel.getComponentAt(0, 0));
        jPanel.setBounds(0, 0, 300, 400);
        panel.add(jPanel);
        panel.repaint();
    }

    private JMenuItem upload(){
        JMenuItem uploadFile = new JMenuItem("上传档案");
        uploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               panelMaker(UploadPanel.getInstance(user));
            }
        });
        return uploadFile;
    }

    private JMenuItem download(){
        JMenuItem downloadFile = new JMenuItem("下载档案");
        downloadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaker(DownloadPanel.getInstace(user));
            }
        });
        return downloadFile;
    }

    private JPanel getMainJPanel(){
        JPanel mainPanel = MainPanel.getInstance(user);
        JButton button = new JButton("退出登录");
        button.setBounds(70, 270, 150, 30);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                setVisible(false);
                Login.getInstance().setVisible(true);
            }
            
        });

        mainPanel.add(button);
        return mainPanel;

    }

    public static MainFrame getInstance(User user){
        MainFrame.instance.init(user);
        return instance;
    }

}
