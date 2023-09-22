package com.patikadev.View.Educator;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ContentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel panel_content_list;
    private JScrollPane scrll_content_list;
    private JTable tbl_content_list;
    private JPanel pnl_content_create;
    private JTextField field_content_title;
    private JTextField field_content_description;
    private JTextField field_youtube_link;
    private JTextField field_srch_content_course_name;
    private JTextField field_srch_content_title;
    private JButton bttn_content_srch;
    private JButton bttn_add_content;
    private JPanel panel_content_search;
    private JPopupMenu contentMenu;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private final Course course;

    public ContentGUI(Course course){
        this.course = course;
        add(wrapper);
        setSize(800, 400);
        setLocation(Helper.ScreenCenterPoint("x", getSize()), Helper.ScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        contentMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        contentMenu.add(updateMenu);
        contentMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString());
            UpdateContentGUI updateContentGUI = new UpdateContentGUI(Content.getFetch(selected_id));
            updateContentGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadContentModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString());
                if (Content.delete(select_id)){
                    Helper.showMsg("succes");
                    loadContentModel();
                }else Helper.showMsg("error");
            }
        });

        mdl_content_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        Object[] col_content_list = {"Content ID", "Title","Description","Youtube Link","Course Name","Course ID"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];
        loadContentModel();

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.setComponentPopupMenu(contentMenu);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_content_list.rowAtPoint(point);
                tbl_content_list.setRowSelectionInterval(selected_row, selected_row);            }
        });


        bttn_add_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_content_title) || Helper.isFieldEmpty(field_content_description) || Helper.isFieldEmpty(field_youtube_link)){
                Helper.showMsg("fill");
            }else {
                String title = field_content_title.getText();
                String description = field_content_description.getText();
                String youtubeLink = field_youtube_link.getText();
                String courseName = course.getName();
                int courseID = course.getId();
                if (Content.create(title,description,youtubeLink,courseName,courseID)){
                    Helper.showMsg("success");
                    loadContentModel();
                    field_content_title.setText(null);
                    field_content_description.setText(null);
                    field_youtube_link.setText(null);
                }
            }
        });
        bttn_content_srch.addActionListener(e -> {
            String courseName = field_srch_content_course_name.getText();
            String contentTitle = field_srch_content_title.getText();

            String query = Content.searchQuery(course.getId(),courseName,contentTitle);
            loadContentModel(Content.searchContent(query));
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
            row_content_list[i++] = obj.getCourseID();
            mdl_content_list.addRow(row_content_list);
        }
    }

    public void loadContentModel(ArrayList<Content> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj: list){
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getYoutubeLink();
            row_content_list[i++] = obj.getCourseName();
            row_content_list[i++] = obj.getCourseID();
            mdl_content_list.addRow(row_content_list);
        }
    }
}