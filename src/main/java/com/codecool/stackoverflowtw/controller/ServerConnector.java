package com.codecool.stackoverflowtw.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnector {

    public Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/StackoverflowDB";
            String user = "postgres";
            String password = System.getenv("password");
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
