package com.whut.GUI.FileGUI;

import javax.swing.JPanel;

import com.whut.DataProcessing;
import com.whut.model.Doc;
import com.whut.model.User;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.Date;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DownloadPanel extends JPanel{

    private JTable table;
    private JScrollPane scrollPane;
    private ArrayList<Doc> docList;
    private User user;
    private static DownloadPanel instance = new DownloadPanel();
    
    private DownloadPanel(){
        super();
    }

    private void init(User user){
        removeAll();

        this.setLayout(null);
        this.user = user;
        String[] columnNames = {"文件名", "时间", "创建者", "描述"};
        docList = DataProcessing.getAllDocss();
        String[][] docListData = new String[docList.size()][4];
        for(Doc i:docList){
            docListData[docList.indexOf(i)][0] = i.getFilename();
            docListData[docList.indexOf(i)][1] = new Date(i.getTimestamp()).toString();
            docListData[docList.indexOf(i)][2] = i.getCreator();
            docListData[docList.indexOf(i)][3] = i.getDescription();
        }
        table = new Table(docListData, columnNames);
        table.setBounds(0, 0, 300, 300);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(false);
        table.getSelectedColumn();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 260, 260);
        this.add(scrollPane);
        

        JButton button = new JButton("下载");
		
        button.setBounds(100, 280, 80, 30);
        button.setFont(new Font("楷体",0, 15));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(runnable).start();
            }
        });
        this.add(button);
    }



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int id = table.getSelectedColumn();
            if(id != -1){
                Doc doc = docList.get(id);

                if(JOptionPane.showConfirmDialog(null, "下载文件" + doc.getFilename(), "是否下载", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){                   
                    try {

                        if(user.downloadFile(doc.getID())){
                            JOptionPane.showMessageDialog(null, "下载文件成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                        }else
                            JOptionPane.showMessageDialog(null, "重复下载文件", "错误", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "没有选中表格", "错误", JOptionPane.ERROR_MESSAGE);
            }  
        };
    };

    public static DownloadPanel getInstace(User user){
        DownloadPanel.instance.init(user);
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
