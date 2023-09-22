package com.patikadev.View.Operator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Path;
import com.patikadev.Model.User;
import com.patikadev.View.LoginGUI;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JButton bttn_logout;
    private JPanel pnl_user_list;
    private JLabel lbl_welcome;
    private JScrollPane scrll_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField field_user_name;
    private JTextField field_user_userName;
    private JTextField field_user_password;
    private JComboBox cmb_user_type;
    private JButton bttn_user_create;
    private JTextField field_user_id;
    private JButton bttn_user_delete;
    private JTextField field_srch_user_name;
    private JTextField field_srch_user_userName;
    private JComboBox combo_srch_user_type;
    private JButton bttn_user_srch;
    private JPanel pnl_path_list;
    private JScrollPane scrll_path_list;
    private JTable tbl_path_list;
    private JTextField field_path_name;
    private JButton bttn_path_add;
    private JPanel pnl_course_list;
    private JScrollPane scrll_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField field_course_name;
    private JTextField field_course_language;
    private JComboBox cmb_course_path;
    private JComboBox cmb_course_user;
    private JButton bttn_course_add;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_path_list;
    private Object[] row_path_list;
    private JPopupMenu pathMenu, courseMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome " + operator.getName());

        // UserList
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name Surname", "User Name", "Password", "Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        row_user_list = new Object[col_user_list.length];
        loadUserModel();
        loadEducatorCombo();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                field_user_id.setText(select_user_id);
            } catch (Exception exception) {
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.update(user_id, name, user_name, password, type)) {
                    Helper.showMsg("success");

                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });
        // ## UserList


        // PathList
        pathMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        pathMenu.add(updateMenu);
        pathMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_path_list.getValueAt(tbl_path_list.getSelectedRow(), 0).toString());
            UpdatePathGUI updatePathGUI = new UpdatePathGUI(Path.getFetch(select_id));
            updatePathGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPathModel();
                    loadPathCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_path_list.getValueAt(tbl_path_list.getSelectedRow(), 0).toString());
                if (Path.delete(select_id)) {
                    Helper.showMsg("success");
                    loadPathModel();
                    loadPathCombo();
                    loadCourseModel();
                } else Helper.showMsg("error");
            }
        });

        mdl_path_list = new DefaultTableModel();
        Object[] col_path_list = {"ID", "Path Name"};
        mdl_path_list.setColumnIdentifiers(col_path_list);
        row_path_list = new Object[col_path_list.length];
        loadPathModel();

        tbl_path_list.setModel(mdl_path_list);
        tbl_path_list.setComponentPopupMenu(pathMenu);
        tbl_path_list.getTableHeader().setReorderingAllowed(false);
        tbl_path_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_path_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_path_list.rowAtPoint(point);
                tbl_path_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        // ## PathList

        // CourseList
        courseMenu = new JPopupMenu();
        updateMenu = new JMenuItem("Update");
        deleteMenu = new JMenuItem("Delete");
        courseMenu.add(updateMenu);
        courseMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
            UpdateCourseGUI updateCourseGUI = new UpdateCourseGUI(Course.getFetch(select_id));
            updateCourseGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
                if (Course.delete(select_id)){
                    Helper.showMsg("succes");
                    loadCourseModel();
                }else Helper.showMsg("error");
            }
        });
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "course Name", "Programing Language", "Path", "Educator"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.setComponentPopupMenu(courseMenu);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_course_list.rowAtPoint(point);
                tbl_course_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        loadPathCombo();
        loadEducatorCombo();


        // ## CourseList

        bttn_user_create.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_user_name) || Helper.isFieldEmpty(field_user_userName) || Helper.isFieldEmpty(field_user_password)) {
                Helper.showMsg("fill");
            } else {
                String name = field_user_name.getText();
                String userName = field_user_userName.getText();
                String password = field_user_password.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.create(name, userName, password, type)) {
                    Helper.showMsg("success");
                    loadUserModel();
                    loadEducatorCombo();

                    field_user_name.setText(null);
                    field_user_userName.setText(null);
                    field_user_password.setText(null);
                }
            }
        });
        bttn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_user_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    if (User.delete(Integer.parseInt(field_user_id.getText()))) {
                        Helper.showMsg("success");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                        field_user_id.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        bttn_user_srch.addActionListener(e -> {
            String name = field_srch_user_name.getText();
            String userName = field_srch_user_userName.getText();
            String type = combo_srch_user_type.getSelectedItem().toString();
            String query = User.searchQuery(name, userName, type);
            loadUserModel(User.searchUserList(query));
        });
        bttn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        bttn_path_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_path_name)) {
                Helper.showMsg("fill");
            } else {
                if (Path.add(field_path_name.getText())) {
                    Helper.showMsg("success");
                    loadPathModel();
                    loadPathCombo();
                    field_path_name.setText(null);
                } else Helper.showMsg("error");
            }
        });
        bttn_course_add.addActionListener(e -> {
            Item pathItem = (Item) cmb_course_path.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(field_course_name) || Helper.isFieldEmpty(field_course_language)) {
                Helper.showMsg("fill");
            } else {
                if (Course.add(userItem.getKey(), pathItem.getKey(), field_course_name.getText(), field_course_language.getText())) {
                    Helper.showMsg("success");
                    loadCourseModel();
                    field_course_name.setText(null);
                    field_course_language.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
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

    private void loadPathModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_path_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Path obj : Path.getList()) {
            i = 0;
            row_path_list[i++] = obj.getId();
            row_path_list[i++] = obj.getName();
            mdl_path_list.addRow(row_path_list);
        }
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User obj : User.getList()) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUserName();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User obj : list) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUserName();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPathCombo() {
        cmb_course_path.removeAllItems();
        for (Path obj : Path.getList()) {
            cmb_course_path.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo() {
        cmb_course_user.removeAllItems();
        for (User obj : User.getList()) {
            if (obj.getType().equals("educator")) {
                cmb_course_user.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Kaan Danisment");
        op.setUserName("Kaan");
        op.setPassword("1234");
        op.setType("operator");
        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}
