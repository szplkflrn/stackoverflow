package com.codecool.stackoverflowtw.dao.model;

public class Question {

    private String questionText;
    private String description;


    public Question(String questionText, String description) {
        this.questionText = questionText;
        this.description = description;
    }


    public String getQuestionText() {
        return questionText;
    }

    public String getDescription() {
        return description;
    }


}

