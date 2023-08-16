package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.ServerConnector;
import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import com.codecool.stackoverflowtw.dao.model.Question;
import com.codecool.stackoverflowtw.logger.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuestionsDaoJdbc implements QuestionsDAO {

    private final ServerConnector serverConnector;
    private final Logger consoleLogger;

    public QuestionsDaoJdbc(ServerConnector serverConnector, Logger consoleLogger) {
        this.serverConnector = serverConnector;
        this.consoleLogger = consoleLogger;
    }


    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> allTheQuestions = new ArrayList<>();
        try (Connection connection = serverConnector.getConnection()) {
            String query = "SELECT * FROM questions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    QuestionDTO question = new QuestionDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            LocalDateTime.parse(resultSet.getString("date")));
                    allTheQuestions.add(question);
                }
            }
        } catch (SQLException e) {
            consoleLogger.logInfo(e.getMessage());
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
                                LocalDateTime.parse(resultSet.getString("date")));
                    }
                }
            }
        } catch (SQLException e) {
            consoleLogger.logInfo(e.getMessage());
        }
        return actualQuestion;
    }

    public boolean deleteQuestionById(int id) {
        try (Connection connection = serverConnector.getConnection()) {
            String query = "DELETE FROM questions WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                consoleLogger.logInfo("User deleted from database with id: " + id);

                return true;
            }
        } catch (SQLException e) {
            consoleLogger.logError(e.getMessage());
            return false;
        }

    }

    public int addNewQuestion(Question question) {
        try (Connection connection = serverConnector.getConnection()) {
            String query = "INSERT INTO questions (title,description,date,answer_count) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, question.getQuestionText());
                preparedStatement.setString(2, question.getDescription());
                preparedStatement.setString(3, String.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(4, question.getAnswerCount());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                consoleLogger.logInfo("Question added to database");
                return generatedKeys.getInt(0);
            }
        } catch (SQLException e) {
            consoleLogger.logError(e.getMessage());
            return 0;
        }
    }


    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }


}
