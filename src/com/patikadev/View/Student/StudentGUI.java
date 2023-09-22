package com.patikadev.View.Student;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Path;
import com.patikadev.Model.Student;
import com.patikadev.View.LoginGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tab_student;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton bttn_logout;
    private JScrollPane scrll_course_list;
    private JTable tbl_course_list;
    private JTextField fld_register_path_id;
    private JPanel pnl_register_form;
    private JPanel pnl_course_list;
    private JPanel pnl_path_list;
    private JTable tbl_path_list;
    private JScrollPane scrll_path_list;
    private JButton bttn_register;
    private JPanel pnl_student_pathways;
    private JPanel pnl_student_courses;
    private JTable tbl_student_pathways;
    private JTable tbl_student_courses;
    private JScrollPane scrll_student_pathways;
    private JScrollPane scrll_student_courses;
    private JTextField fld_course_id;
    private JButton bttn_view_content;
    private DefaultTableModel mdl_path_list;
    private Object[] row_path_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_student_pathways;
    private Object[] row_student_pathways;
    private DefaultTableModel mdl_student_courses;
    private Object[] row_student_courses;

    private final Student student;


    public StudentGUI(Student student){
        this.student = student;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_welcome.setText("Welcome " + student.getName());

        // StudentCourses
        mdl_student_courses = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_student_courses = {"ID", "course Name", "Programing Language", "Path", "Educator"};
        mdl_student_courses.setColumnIdentifiers(col_student_courses);
        row_student_courses = new Object[col_student_courses.length];
        loadStudentCourses();

        tbl_student_courses.setModel(mdl_student_courses);
        tbl_student_courses.getTableHeader().setReorderingAllowed(false);
        tbl_student_courses.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_student_courses.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = String.valueOf(Integer.parseInt(tbl_student_courses.getValueAt(tbl_student_courses.getSelectedRow(), 0).toString()));
                fld_course_id.setText(selected_row);
            } catch (Exception exception) {

            }
        });

        // ## StudentCourses


        // StudentPathways
        mdl_student_pathways =new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_student_pathways = {"ID", "Path Name"};
        mdl_student_pathways.setColumnIdentifiers(col_student_pathways);
        row_student_pathways = new Object[col_student_pathways.length];
        loadStudentPathwaysModel();

        tbl_student_pathways.setModel(mdl_student_pathways);
        tbl_student_pathways.getTableHeader().setReorderingAllowed(false);
        tbl_student_pathways.getColumnModel().getColumn(0).setMaxWidth(50);

        // ## StudentPathways

        // PathList
        mdl_path_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_path_list = {"ID", "Path Name"};
        mdl_path_list.setColumnIdentifiers(col_path_list);
        row_path_list = new Object[col_path_list.length];
        loadPathModel();

        tbl_path_list.setModel(mdl_path_list);
        tbl_path_list.getTableHeader().setReorderingAllowed(false);
        tbl_path_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_path_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_id = tbl_path_list.getValueAt(tbl_path_list.getSelectedRow(),0).toString();
                fld_register_path_id.setText(selected_id);
            }catch (Exception exception){
            }
        });

        // ## PathList

        // CourseList
        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_courseList = {"ID", "course Name", "Programing Language", "Path", "Educator"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        // ## CourseList

        bttn_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_register_path_id)){
                Helper.showMsg("fill");
            }else {
                if (Student.register(student.getId(), Integer.parseInt(fld_register_path_id.getText()))){
                    Helper.showMsg("success");
                }else Helper.showMsg("This path has been registered before");

            }
            loadStudentPathwaysModel();
            loadStudentCourses();
        });
        bttn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        bttn_view_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_id)){
                Helper.showMsg("fill");
            }else {
                StudentContentGUI studentContentGUI = new StudentContentGUI(Course.getFetch(Integer.parseInt(fld_course_id.getText())),Student.getFetch(student.getId()));
            }
        });
    }

    public void loadStudentCourses(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_student_courses.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj: Student.getCourse(student.getId())){
            i = 0;
            row_student_courses[i++] = obj.getId();
            row_student_courses[i++] = obj.getName();
            row_student_courses[i++] = obj.getLang();
            row_student_courses[i++] = obj.getPath().getName();
            row_student_courses[i++] = obj.getEducator().getName();
            mdl_student_courses.addRow(row_student_courses);
        }
    }

    public void loadStudentPathwaysModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_student_pathways.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Path obj: Student.getPathways(student.getId())){
            i = 0;
            row_student_pathways[i++] = obj.getId();
            row_student_pathways[i++] = obj.getName();
            mdl_student_pathways.addRow(row_student_pathways);
        }
    }



    public void loadPathModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_path_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Path obj : Path.getList()) {
            i = 0;
            row_path_list[i++] = obj.getId();
            row_path_list[i++] = obj.getName();
            mdl_path_list.addRow(row_path_list);
        }
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj : Course.getList()) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPath().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);

        }
    }
}
