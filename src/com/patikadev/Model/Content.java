package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private String title;
    private String description;
    private String youtubeLink;
    private String courseName;
    private int courseID;

    public Content(){

    }

    public Content(int id, String title, String description, String youtubeLink, String courseName, int courseID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.youtubeLink = youtubeLink;
        this.courseName = courseName;
        this.courseID = courseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public static ArrayList<Content> getList(int course_id){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM content WHERE course_id = " + course_id);
            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String youtubeLink = rs.getString("youtube_link");
                String courseName = rs.getString("course_name");
                int courseID = rs.getInt("course_id");
                obj = new Content(id,title,description,youtubeLink,courseName,courseID);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }
    public static boolean create(String title,String description,String youtubeLink,String courseName,int courseID){
        String query = "INSERT INTO content (title,description,youtube_link,course_name,course_id) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,description);
            pr.setString(3,youtubeLink);
            pr.setString(4,courseName);
            pr.setInt(5,courseID);
            int response = pr.executeUpdate();
            if (response == -1){
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static String searchQuery(int courseID, String courseName, String contentTitle){
        String query = "SELECT * FROM content WHERE course_id = " + courseID + " AND course_name LIKE '%{{courseName}}%' AND title LIKE '%{{contentTitle}}%'";
        query = query.replace("%{{courseName}}",courseName);
        query = query.replace("%{{contentTitle}}",contentTitle);

        return query;
    }

    public static ArrayList<Content> searchContent(String query){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setTitle(rs.getString("title"));
                obj.setDescription(rs.getString("description"));
                obj.setYoutubeLink(rs.getString("youtube_link"));
                obj.setCourseName(rs.getString("course_name"));
                obj.setCourseID(rs.getInt("course_id"));
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static boolean update(int id,String title, String description, String youtubeLink){
        String query = "UPDATE content SET title = ?, description = ?, youtube_link = ? WHERE id = " + id;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,description);
            pr.setString(3,youtubeLink);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static Content getFetch(int id) {
        Content obj = null;
        String query = "SELECT * FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Content(rs.getInt("id"), rs.getString("title"), rs.getString("description"),rs.getString("youtube_link"), rs.getString("course_name"),rs.getInt("course_id") );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
