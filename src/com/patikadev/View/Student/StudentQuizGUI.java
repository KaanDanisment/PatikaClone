package com.patikadev.View.Student;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentQuizGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_question_list;
    private JScrollPane scrll_question_list;
    private JPanel pnl_question_list;
    private JPanel pnl_answer;
    private JTextField fld_question_id;
    private JTextField fld_response;
    private JButton bttn_answer;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    private Content content;

    public StudentQuizGUI(Content content){
        this.content = content;

        add(wrapper);
        setSize(800, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        mdl_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_quiz_list = {"ID","Question"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        loadQuizModel();

        tbl_question_list.setModel(mdl_quiz_list);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);
        tbl_question_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {
            String selected_row = tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),0).toString();
            fld_question_id.setText(selected_row);
        });

        bttn_answer.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question_id) || Helper.isFieldEmpty(fld_response)){
                Helper.showMsg("fill");
            }else {
                if (fld_response.getText().equals(Quiz.response(Integer.parseInt(fld_question_id.getText())))){
                    Helper.showMsg("Congratulations. Correct Answer!");
                }else Helper.showMsg("Wrong Answer!");
            }

            fld_response.setText(null);
        });
    }

    public void loadQuizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj: Quiz.getList(content.getId())){
            i = 0;
            row_quiz_list[i++] = obj.getQuizID();
            row_quiz_list[i++] = obj.getQuestion();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }
}
