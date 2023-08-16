package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.AnswerDAO;
import com.codecool.stackoverflowtw.dao.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private AnswerDAO answerDAO;
    @Autowired
    public AnswerService(AnswerDAO answerDAO) {
        this.answerDAO = answerDAO;
    }

    public List<AnswerDTO> getAllAnswers() {
        return answerDAO.getAllAnswers();
    }

    public AnswerDTO getAnswerById(int id) {
        return answerDAO.getAnswerById(id);
    }

    public boolean deleteAnswerById(int id) {
        return answerDAO.deleteAnswerById(id);
    }

    public int addNewAnswer(NewAnswerDTO answerDTO) {
        return answerDAO.addNewAnswer(new Answer(answerDTO.title(),0));
    }
}
