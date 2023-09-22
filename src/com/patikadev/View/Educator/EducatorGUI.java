package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.View.LoginGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTable tabel_course_list;
    private JScrollPane scrll_course_list;
    private JPanel panel_top;
    private JLabel lbl_welcome;
    private JButton button_logout;
    private JPanel panel_course_list;
    private JPanel panel_contents;
    private JTextField field_course_id;
    private JButton bttn_view_content;
    private JButton bttn_quiz;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Educator educator;


    public EducatorGUI(Educator educator) {
        this.educator = educator;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_course_list = {"Course ID", "Course Name", "Programing Language", "Path"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();

        tabel_course_list.setModel(mdl_course_list);
        tabel_course_list.getTableHeader().setReorderingAllowed(false);

        lbl_welcome.setText("Welcome " + educator.getName());

        tabel_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = String.valueOf(Integer.parseInt(tabel_course_list.getValueAt(tabel_course_list.getSelectedRow(), 0).toString()));
                field_course_id.setText(selected_row);
            } catch (Exception exception) {

            }
        });

        bttn_view_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_course_id)) {
                Helper.showMsg("fill");
            } else {
                ContentGUI contentGUI = new ContentGUI(Course.getFetch(Integer.parseInt(field_course_id.getText())));
            }
        });


        button_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });


        bttn_quiz.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_course_id)) {
                Helper.showMsg("fill");
            } else {
                EducatorQuizGUI educatorQuizGUI = new EducatorQuizGUI(Course.getFetch(Integer.parseInt(field_course_id.getText())));
            }
        });
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tabel_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj : Course.getListByUser(educator.getId())) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPath().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }
}