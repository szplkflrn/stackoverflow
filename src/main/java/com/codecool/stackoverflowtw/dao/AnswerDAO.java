package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.controller.dto.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import com.codecool.stackoverflowtw.dao.model.Answer;
import com.codecool.stackoverflowtw.dao.model.Question;

import java.util.List;

public interface AnswerDAO {

    List<AnswerDTO> getAllAnswers();
    List<AnswerDTO> getAnswerById(int id);
    boolean deleteAnswerById(int id);
    int addNewAnswer(Answer answer);


}
