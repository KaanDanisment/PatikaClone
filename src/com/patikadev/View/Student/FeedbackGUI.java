package com.patikadev.View.Student;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Feedback;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FeedbackGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_feedback_list;
    private JScrollPane scrll_feedback_list;
    private DefaultTableModel mdl_feedback_list;
    private Object[] row_feedback_list;

    private Content content;

    public FeedbackGUI(Content content, User student){
        this.content = content;

        add(wrapper);
        setSize(800, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        mdl_feedback_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_feedback_list = {"Student Name", "Comment", "Rate"};
        mdl_feedback_list.setColumnIdentifiers(col_feedback_list);
        row_feedback_list = new Object[col_feedback_list.length];
        loadFeedbackModel();

        tbl_feedback_list.setModel(mdl_feedback_list);
        tbl_feedback_list.getTableHeader().setReorderingAllowed(false);
    }

    public void loadFeedbackModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_feedback_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Feedback obj: Feedback.getList(content.getId())){
            i = 0;
            row_feedback_list[i++] = obj.getStudent_name();
            row_feedback_list[i++] = obj.getComment();
            row_feedback_list[i++] = obj.getRate();
            mdl_feedback_list.addRow(row_feedback_list);
        }
    }
}
