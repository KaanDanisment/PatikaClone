package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContentGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_content_title;
    private JTextField fld_content_description;
    private JTextField fld_content_youtube_link;
    private JButton bttn_update;
    private Content content;

    public UpdateContentGUI(Content content){
        this.content = content;
        add(wrapper);
        setSize(300,300);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_content_title.setText(content.getTitle());
        fld_content_description.setText(content.getDescription());
        fld_content_youtube_link.setText(content.getYoutubeLink());

        bttn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_youtube_link) || Helper.isFieldEmpty( fld_content_description )){
                Helper.showMsg("fill");
            }else {
                if (Content.update(content.getId(), fld_content_title.getText(),fld_content_description.getText(),fld_content_youtube_link.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}
