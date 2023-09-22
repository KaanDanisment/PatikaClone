package com.patikadev.View.Operator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Path;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePathGUI extends JFrame {
    private JPanel wrapper;
    private JTextField field_path_name;
    private JButton bttn_update;
    private Path path;

    public UpdatePathGUI(Path path){
        this.path = path;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        field_path_name.setText(path.getName());
        bttn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_path_name)){
                Helper.showMsg("fill");
            }else {
                if (Path.update(path.getId(),field_path_name.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}
