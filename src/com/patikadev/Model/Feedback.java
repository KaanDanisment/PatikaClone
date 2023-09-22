package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Feedback {

    private int content_id;
    private String student_name;
    private String comment;
    private int rate;

    public Feedback(int content_id, String student_name, String comment, int rate) {
        this.content_id = content_id;
        this.student_name = student_name;
        this.comment = comment;
        this.rate = rate;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public static boolean add(int contentID,String userName, String comment, int rate) {
        String query = "INSERT INTO feedback (content_id,student_name,comment,rate) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, contentID);
            pr.setString(2, userName);
            pr.setString(3, comment);
            pr.setInt(4, rate);
            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Feedback> getList(int content_id){
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        Feedback obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM feedback WHERE content_id = " + content_id);
            while (rs.next()){
                int contentID = rs.getInt("content_id");
                String studentName = rs.getString("student_name");
                String comment = rs.getString("comment");
                int rate = rs.getInt("rate");
                obj = new Feedback(contentID,studentName,comment,rate);
                feedbackList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return feedbackList;
    }
}
