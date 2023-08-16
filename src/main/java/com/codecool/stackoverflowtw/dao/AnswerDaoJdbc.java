package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.ServerConnector;
import com.codecool.stackoverflowtw.controller.dto.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import com.codecool.stackoverflowtw.dao.model.Answer;
import com.codecool.stackoverflowtw.logger.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoJdbc implements AnswerDAO{

    private final ServerConnector serverConnector;
    private final Logger consoleLogger;

    public AnswerDaoJdbc(ServerConnector serverConnector, Logger consoleLogger) {
        this.serverConnector = serverConnector;
        this.consoleLogger = consoleLogger;
    }

    @Override
    public List<AnswerDTO> getAllAnswers() {
        List<AnswerDTO> allTheAnswers = new ArrayList<>();
        try (Connection connection = serverConnector.getConnection()) {
            String query = "SELECT * FROM answers";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AnswerDTO answer = new AnswerDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("answer"),
                            LocalDateTime.parse(resultSet.getString("date")),
                            resultSet.getInt("question_id"));
                    allTheAnswers.add(answer);
                }
            }
        } catch (SQLException e) {
            consoleLogger.logInfo(e.getMessage());
        }
        return allTheAnswers;
    }

    @Override
    public AnswerDTO getAnswerById(int id) {
        return null;
    }

    @Override
    public boolean deleteAnswerById(int id) {
        return false;
    }

    @Override
    public int addNewAnswer(Answer answer) {
        try (Connection connection = serverConnector.getConnection()) {
            String query = "INSERT INTO answers (answer,date,question_id) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, answer.getAnswer());
                preparedStatement.setString(2, String.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, answer.getQuestion_id());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    consoleLogger.logInfo("Question added to database");
                    return generatedId;
                } else {
                    consoleLogger.logError("Failed to retrieve generated key");
                    return 0;
                }
            }
        } catch (SQLException e) {
            consoleLogger.logError(e.getMessage());
            return 0;
        }
    }
}
