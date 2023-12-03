package com.whut.GUI.UserGUI;
import javax.swing.JPanel;
import com.whut.model.User;
import com.whut.utils.UserPanelEnum;

public class UserPanel{

    private User user;
    private UserPanelEnum userFrameEnum;

    public UserPanel(User user, UserPanelEnum userFrameEnum) {
        this.user = user;
        this.userFrameEnum = userFrameEnum;
    }
 
    public JPanel getUserPanel(){
        switch (userFrameEnum) {
            case ADD:
                return new AddUserPanel();
            case DELATE:
                return new DelateUserPanel(user);
            case UPDATE:
                return new UpdateUserPanel(user);
            case LIST:
                return new ListUserPanel();
        }
        return new JPanel();
    }

}







