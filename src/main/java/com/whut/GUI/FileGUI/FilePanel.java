package com.whut.GUI.FileGUI;

import javax.swing.JPanel;

import com.whut.model.User;
import com.whut.utils.FilePanelEnum;

public class FilePanel{
    private User user;
    private FilePanelEnum filePanelEnum;
    
    public FilePanel(User user, FilePanelEnum filePanelEnum){
        this.user = user;
        this.filePanelEnum = filePanelEnum;
    }

    public JPanel getFilePanel(){
        switch (filePanelEnum) {
            case UPLOAD:
                return new UploadPanel(user);
            case DOWNLOAD:
                return new DownloadPanel(user);
        }
        return new JPanel();
    }
}
