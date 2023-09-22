package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EducatorQuizGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_content_list;
    private JTable tbl_content_list;
    private JScrollPane scrll_content_list;
    private JPanel pnl_quiz_add;
    private JTextField fld_content_id;
    private JPanel pnl_content_search;
    private JTextField fld_question;
    private JTextField fld_answer;
    private JButton bttn_quiz_add;
    private JTextField field_srch_content_course_name;
    private JTextField field_srch_content_title;
    private JButton bttn_content_srch;
    private JButton bttn_view_quiz;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private final Course course;

    public EducatorQuizGUI(Course course) {
        this.course = course;
        add(wrapper);
        setSize(800, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_content_list = {"ID", "Title", "Description", "Youtube Link", "Course Name"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];
        loadContentModel();

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        bttn_content_srch.addActionListener(e -> {
            String courseName = field_srch_content_course_name.getText();
            String contentTitle = field_srch_content_title.getText();

            String query = Content.searchQuery(course.getId(),courseName,contentTitle);
            loadContentModel(Content.searchContent(query));
        });

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = String.valueOf(Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString()));
                fld_content_id.setText(selected_row);
            }catch (Exception exception){
            }
        });

        bttn_quiz_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id) || Helper.isFieldEmpty(fld_answer) || Helper.isFieldEmpty(fld_question)){
                Helper.showMsg("fill");
            }else {
                int contentID = Integer.parseInt(fld_content_id.getText());
                String question = fld_question.getText();
                String answer = fld_answer.getText();
                if (Quiz.create(contentID,question,answer)){
                    Helper.showMsg("success");
                    fld_content_id.setText(null);
                    fld_question.setText(null);
                    fld_answer.setText(null);
                }

            }
        });
        bttn_view_quiz.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)){
                Helper.showMsg("fill");
            }else {
                ViewQuiz viewQuiz = new ViewQuiz(Content.getFetch(Integer.parseInt(fld_content_id.getText())));
            }


        });
    }

    public void loadContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj : Content.getList(this.course.getId())) {
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getYoutubeLink();
            row_content_list[i++] = obj.getCourseName();
            mdl_content_list.addRow(row_content_list);
        }
    }



    public void loadContentModel(ArrayList<Content> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj : list) {
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
