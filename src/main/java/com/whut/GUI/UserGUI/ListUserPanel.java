package com.whut.GUI.UserGUI;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.whut.DataProcessing;
import com.whut.model.User;

public class ListUserPanel extends JPanel{
    private JTable table;
    private JScrollPane scrollPane;
    private ArrayList<User> userList;
    private static ListUserPanel instance = new ListUserPanel();

    private ListUserPanel(){
        super();
    }

    private void init(){
        removeAll();
        this.setLayout(null);
        String[] columnNames = {"用户名", "密码", "角色"};
        userList = DataProcessing.getAllUsers();
        String[][] userListData = new String[userList.size()][3];
        for(User i:userList){
            userListData[userList.indexOf(i)][0] = i.getName();
            userListData[userList.indexOf(i)][1] = i.getPassword();
            userListData[userList.indexOf(i)][2] = i.getRole();
        }
        table = new Table(userListData, columnNames);
        table.setBounds(0, 0, 300, 300);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(false);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 260, 300);
        this.add(scrollPane);
        
    }

    public static ListUserPanel getInstance(){
        ListUserPanel.instance.init();
        return instance;
    }
}

class Table extends JTable{
    public Table(String[][] userListData, String[] columnNames) {
        super(userListData, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
