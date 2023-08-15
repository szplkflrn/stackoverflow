package com.codecool.stackoverflowtw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuestionsDaoJdbc implements QuestionsDAO {


    public Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/StackoverflowDB";
            String user = "postgres";
            String password = "JoeSatriani1228";
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }



}
