package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {

    private int quizID;
    private int contentID;
    private String question;
    private String answer;

    public Quiz(int quizID, int contentID, String question, String answer) {
        this.quizID = quizID;
        this.contentID = contentID;
        this.question = question;
        this.answer = answer;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static boolean create(int contentID, String question,String answer){
        String query = "INSERT INTO quiz (content_id,question,answer)  VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,contentID);
            pr.setString(2,question);
            pr.setString(3,answer);
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

    public static ArrayList<Quiz> getList(int contentID){
        String query = "SELECT * FROM quiz WHERE content_id = " + contentID;
        ArrayList<Quiz> quizList = new ArrayList<>();
        Quiz obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                int content_id = rs.getInt("content_id");
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                obj = new Quiz(id,content_id,question,answer);
                quizList.add(obj);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizList;
    }
    public static boolean update(int quizID, String question, String answer){
        String query= "UPDATE quiz SET question = ?, answer = ? WHERE id = " + quizID;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,question);
            pr.setString(2,answer);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Quiz getFetch(int id) {
        Quiz obj = null;
        String query = "SELECT * FROM quiz WHERE id = ?";
        try {
            PreparedStatement pr =  DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Quiz(rs.getInt("id"), rs.getInt("content_id"), rs.getString("question"), rs.getString("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM quiz WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String response(int id){
        String query = "SELECT answer FROM quiz WHERE id = " + id;
        String obj = null;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()){
                obj = rs.getString("answer");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
