package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JFrame{
    private JPanel pnl_register;
    private JTextField fld_user_name;
    private JTextField fld_user_userName;
    private JTextField fld_user_password;
    private JButton bttn_register;

    public RegisterGUI(){
        add(pnl_register);
        setSize(300,300);
        setLocation(Helper.ScreenCenterPoint("x",getSize()), Helper.ScreenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        bttn_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_userName) || Helper.isFieldEmpty(fld_user_password)){
                Helper.showMsg("fill");
            }else {
                User.create(fld_user_name.getText(),fld_user_userName.getText(),fld_user_password.getText(),"student");
                Helper.showMsg("success");
                dispose();

            }
        });
    }
}
