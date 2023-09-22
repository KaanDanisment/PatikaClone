package com.patikadev.View.Operator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Path;
import com.patikadev.Model.User;

import javax.swing.*;

public class UpdateCourseGUI extends JFrame {
    private JPanel wrapper;
    private JTextField field_course_name;
    private JTextField field_course_language;
    private JComboBox cmb_course_path;
    private JButton bttn_course_update;
    private JComboBox cmb_course_educator;
    private Course course;

    public UpdateCourseGUI(Course course){
        this.course = course;
        add(wrapper);
        setSize(300,300);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        field_course_name.setText(course.getName());
        bttn_course_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_course_name)){
                Helper.showMsg("fill");
            }else {
                if (Course.update(course.getId(), field_course_name.getText())){
                    Helper.showMsg("success");
                }
                dispose();
            }
        });

        loadPathCombo();
        loadEducatorCombo();
    }
    public void loadPathCombo() {
        cmb_course_path.removeAllItems();
        for (Path obj : Path.getList()) {
            cmb_course_path.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo() {
        cmb_course_educator.removeAllItems();
        for (User obj : User.getList()) {
            if (obj.getType().equals("educator")) {
                cmb_course_educator.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Course c = new Course(9,18,9,"dsad","sdf");
        UpdateCourseGUI up = new UpdateCourseGUI(c);

    }

}
