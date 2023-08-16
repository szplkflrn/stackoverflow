package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import com.codecool.stackoverflowtw.dao.model.Question;

import java.sql.Connection;
import java.util.List;

public interface QuestionsDAO {
    void sayHi();
    List<QuestionDTO> getAllQuestions();
    QuestionDTO getQuestionById(int id);
    boolean deleteQuestionById(int id);
    int addNewQuestion(Question question);
}
