package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Student extends User{

    public Student() {
    }
    public Student(int id, String name, String userName, String password, String type) {
        super(id, name, userName, password, type);
    }

    public static boolean register(int studentID, int pathID) {
        Student obj = null;
        String searchQuery = "SELECT * FROM student_register WHERE student_id = ? AND path_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(searchQuery);
            pr.setInt(1, studentID);
            pr.setInt(2, pathID);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Student();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            return false;
        } else {
            String query = "INSERT INTO student_register (student_id, path_id) VALUES (?,?)";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setInt(1, studentID);
                pr.setInt(2, pathID);
                int response = pr.executeUpdate();
                if (response == -1) {
                    Helper.showMsg("error");
                }
                return response != -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static ArrayList<Path> getPathways(int studentID){
        int pathID;
        ArrayList<Integer> pathIdList = new ArrayList<>();
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT path_id FROM student_register WHERE student_id = " + studentID);
            while (rs.next()){
                pathID = rs.getInt("path_id");
                pathIdList.add(pathID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Path> pathList = new ArrayList<>();
        for (Integer i: pathIdList){
            Path obj;
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM patika WHERE id = " + i);
                while (rs.next()){
                    obj = new Path(rs.getInt("id"), rs.getString("name"));
                    pathList.add(obj);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return pathList;
    }

    public static ArrayList<Course> getCourse(int studentID){
        int pathID;
        ArrayList<Integer> pathIDList = new ArrayList<>();
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT path_id FROM student_register WHERE student_id = " + studentID);
            while (rs.next()){
                pathID = rs.getInt("path_id");
                pathIDList.add(pathID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Course> courseList = new ArrayList<>();
        for (Integer i: pathIDList){
            Course obj;
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM course WHERE path_id = " + i);
                while (rs.next()){
                    int id = rs.getInt("id");
                    int user_id = rs.getInt("user_id");
                    int path_id = rs.getInt("path_id");
                    String name = rs.getString("name");
                    String lang = rs.getString("lang");
                    obj = new Course(id,user_id,path_id,name,lang);
                    courseList.add(obj);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return courseList;
    }
}
