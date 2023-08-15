package com.codecool.stackoverflowtw.dao.model;

public class Question {
    private int questionId;
    private String questionText;
    private String description;
    private int answerId;

    public Question(int questionId, String questionText, String description, int answerId) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.description = description;
        this.answerId = answerId;
    }

}

