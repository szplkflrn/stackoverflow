package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.ServerConnector;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuestionsDaoJdbc implements QuestionsDAO {

    private final ServerConnector serverConnector;

    public QuestionsDaoJdbc(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }


    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> allTheQuestions = new ArrayList<>();
        try (Connection connection = serverConnector.getConnection()) {
            String query = "SELECT * FROM questions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                while (resultSet.next()) {
                    QuestionDTO question = new QuestionDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            LocalDateTime.parse(resultSet.getString("date"),formatter));
                    allTheQuestions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTheQuestions;
    }


    public QuestionDTO getQuestionById(int id) {
        QuestionDTO actualQuestion = null;
        try (Connection connection = serverConnector.getConnection()) {
            String query = "SELECT * FROM questions WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        actualQuestion = new QuestionDTO(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                (LocalDateTime) resultSet.getObject("date"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualQuestion;
    }



    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }



}
