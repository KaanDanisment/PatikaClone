package com.patikadev.View.Student;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StudentContentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_content_list;
    private JTable tbl_content_list;
    private JScrollPane scrll_content_list;
    private JTextField fld_content_comment;
    private JComboBox cmb_content_rate;
    private JButton bttn_feedback;
    private JPanel pnl_feedback;
    private JTextField fld_content_id;
    private JButton bttn_view_feedbacks;
    private JButton quizButton;
    private JTextField fld_user_name;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private Course course;
    private User student;

    public StudentContentGUI(Course course, User student){
        this.course = course;
        this.student = student;
        add(wrapper);
        setSize(800, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        mdl_content_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_content_list = {"Content ID", "Title","Description","Youtube Link","Course Name"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];
        loadContentModel();

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = String.valueOf(Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString()));
                fld_content_id.setText(selected_row);
            }catch (Exception exception){
            }
        });

        bttn_feedback.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id) || Helper.isFieldEmpty(fld_content_comment)){
                Helper.showMsg("fill");
            }else {
                int contentId = Integer.parseInt(fld_content_id.getText());
                String userName = student.getUserName();
                String comment = fld_content_comment.getText();
                int rate = Integer.parseInt(cmb_content_rate.getSelectedItem().toString());
                if (Feedback.add(contentId,userName,comment,rate)){
                    Helper.showMsg("success");
                    fld_content_id.setText(null);
                    fld_content_comment.setText(null);
                }
            }
        });
        bttn_view_feedbacks.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)){
                Helper.showMsg("fill");
            }else {
                FeedbackGUI feedbackGUI = new FeedbackGUI(Content.getFetch(Integer.parseInt(fld_content_id.getText())),User.getFetch(student.getId()));
            }
        });
        quizButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)){
                Helper.showMsg("fill");
            }else {
                StudentQuizGUI studentQuizGUI = new StudentQuizGUI(Content.getFetch(Integer.parseInt(fld_content_id.getText())));
            }
        });
    }

    public void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj: Content.getList(this.course.getId())){
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getYoutubeLink();
            row_content_list[i++] = obj.getCourseName();
            mdl_content_list.addRow(row_content_list);
        }
    }
}
