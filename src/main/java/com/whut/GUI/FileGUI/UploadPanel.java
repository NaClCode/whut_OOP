package com.whut.GUI.FileGUI;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.whut.DataProcessing;
import com.whut.model.Doc;
import com.whut.model.User;
import com.whut.utils.ImageUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.JOptionPane;

public class UploadPanel extends JPanel{

    JTextField fileID;
    JTextField fileDsc;
    JTextField fileName;
    String filePath;
    User user;

    UploadPanel(User user){
        this.setLayout(null);
        this.user = user;

        URL url  = getClass().getResource("../Image/upload.png");
        ImageIcon add = ImageUtil.createAutoAdjustIcon(url, true);
        JLabel pic = new JLabel();
        pic.setIcon(add);
        pic.setBounds(75, 10, 130, 130);
        this.add(pic);

        JLabel label = new JLabel("编号:");
        label.setBounds(40, 150, 80, 30);
        label.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label);

        JLabel label_1 = new JLabel("描述:");
        label_1.setBounds(40, 190, 80, 30);
        label_1.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label_1);

        JLabel label_2 = new JLabel("文件名:");
        label_2.setBounds(40, 230, 80, 30);
        label_2.setFont(new Font("楷体", Font.BOLD, 15));
        this.add(label_2);

        fileID = new JTextField();
        fileID.setBounds(100, 150, 140, 30);
        this.add(fileID);
        fileID.setColumns(10);

        fileDsc = new JTextField();
        fileDsc.setBounds(100, 190, 140, 30);
        this.add(fileDsc);

        fileName = new JTextField();
        fileName.setBounds(100, 230, 105, 30);
        JButton button1 = new JButton("打开");
        URL openFile = getClass().getResource("../Image/file.png");
        ImageIcon openPic = ImageUtil.createAutoAdjustIcon(openFile, true);
        
        button1.setIcon(openPic);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fDialog = new FileDialog(new Frame(), "请选择要上传的文件", FileDialog.LOAD);
                fDialog.setVisible(true);
                if (fDialog.getFile() != null) {
                    filePath = fDialog.getDirectory() + fDialog.getFile();
                    fileName.setText(fDialog.getFile());
                }
            }
        });
        button1.setBounds(210, 230, 30, 30);
        this.add(button1);
        this.add(fileName);

        JButton button2 = new JButton("上传");
		button2.addActionListener(new ActionListener() {

            @Override
			public void actionPerformed(ActionEvent e) {
                new Thread(runnable).start();
			}
		});
        button2.setBounds(100, 280, 80, 30);
        button2.setFont(new Font("楷体",0, 15));
        this.add(button2);
		
    }

   

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String ID = fileID.getText();
            String description = fileDsc.getText();
            String filename = fileName.getText();

            if(ID.equals("") || description.equals("") || filename.equals("")){
                JOptionPane.showMessageDialog(null, "请输入完整信息", "错误", JOptionPane.ERROR_MESSAGE);
            }else{
                if(JOptionPane.showConfirmDialog(null, "是否上传" + filename, "上传", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION) == 0){
                    try {
                        if(user.uploadFile(ID, description, filePath, filename)){
                            JOptionPane.showMessageDialog(null, "上传文件成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                            fileID.setText("");
                            fileDsc.setText("");
                            fileName.setText("");
                        }else
                            JOptionPane.showMessageDialog(null, "编号重复", "错误", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }     
    };


}

