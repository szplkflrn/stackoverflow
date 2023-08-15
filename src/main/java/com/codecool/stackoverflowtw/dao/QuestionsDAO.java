package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;

import java.sql.Connection;
import java.util.List;

public interface QuestionsDAO {
    void sayHi();
    List<QuestionDTO> getAllQuestions();
    QuestionDTO getQuestionById(int id);
}
