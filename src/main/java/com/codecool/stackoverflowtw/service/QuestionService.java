package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.dao.QuestionsDAO;
import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private QuestionsDAO questionsDAO;

    @Autowired
    public QuestionService(QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    public List<QuestionDTO> getAllQuestions() {

        List<QuestionDTO> allTheQuestions = new ArrayList<>();
        try (Connection connection = questionsDAO.getConnection()) {
            String query = "SELECT * FROM questions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    QuestionDTO question = new QuestionDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            (LocalDateTime) resultSet.getObject("date"));
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
        try (Connection connection = questionsDAO.getConnection()) {
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

    public boolean deleteQuestionById(int id) {
        // TODO
        return false;
    }

    public int addNewQuestion(NewQuestionDTO question) {
        // TODO
        int createdId = 0;
        return createdId;
    }
}
