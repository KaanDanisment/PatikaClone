package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateQuizGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_question;
    private JTextField fld_answer;
    private JButton bttn_update;
    private Quiz quiz;

    public UpdateQuizGUI(Quiz quiz){
        this.quiz = quiz;
        add(wrapper);
        setSize(300,300);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_question.setText(quiz.getQuestion());
        fld_answer.setText(quiz.getAnswer());


        bttn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question) || Helper.isFieldEmpty(fld_answer)){
                Helper.showMsg("fill");
            }else {
                if (Quiz.update(quiz.getQuizID(), fld_question.getText(), fld_answer.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });
    }
}
