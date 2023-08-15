package com.codecool.stackoverflowtw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuestionsDaoJdbc implements QuestionsDAO {

    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }

    @Override
    public Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:src/main/resources/StackoverflowDB.db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
