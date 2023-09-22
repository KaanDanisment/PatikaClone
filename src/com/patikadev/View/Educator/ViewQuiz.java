package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ViewQuiz extends JFrame {
    private JPanel wrapper;
    private JScrollPane scrll_quiz_list;
    private JTable tbl_quiz_list;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;
    private JPopupMenu quizMenu;

    private final Content content;

    public ViewQuiz(Content content){
        this.content = content;
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        quizMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        quizMenu.add(updateMenu);
        quizMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),0).toString());
            UpdateQuizGUI updateQuizGUI = new UpdateQuizGUI(Quiz.getFetch(selected_id));
            updateQuizGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadQuizModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),0).toString());
                if (Quiz.delete(select_id)){
                    Helper.showMsg("succes");
                    loadQuizModel();
                }else Helper.showMsg("error");
            }
        });

        mdl_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_quiz_list = {"Quiz ID", "Content ID", "Question", "Answer"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        loadQuizModel();

        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.setComponentPopupMenu(quizMenu);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);





        tbl_quiz_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_quiz_list.rowAtPoint(point);
                tbl_quiz_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    public void loadQuizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj: Quiz.getList(content.getId())){
            i = 0;
            row_quiz_list[i++] = obj.getQuizID();
            row_quiz_list[i++] = obj.getContentID();
            row_quiz_list[i++] = obj.getQuestion();
            row_quiz_list[i++] = obj.getAnswer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }
}