package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;
import com.patikadev.View.Educator.EducatorGUI;
import com.patikadev.View.Operator.OperatorGUI;
import com.patikadev.View.Student.StudentGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField field_username;
    private JPasswordField field_user_password;
    private JButton bttn_login;
    private JButton registerButton;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.ScreenCenterPoint("x",getSize()), Helper.ScreenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        bttn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_username) || Helper.isFieldEmpty(field_user_password)){
                Helper.showMsg("fill");
            }else {
                User user = User.getFetch(field_username.getText(),field_user_password.getText());
                if (user == null){
                    Helper.showMsg("User Name or Password is Wrong!");
                }else {
                    switch (user.getType()){
                        case "operator":
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI((Educator) user);
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI((Student) user);
                            break;
                    }
                    dispose();
                }
            }
        });
        registerButton.addActionListener(e -> {
            RegisterGUI registerGUI = new RegisterGUI();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
