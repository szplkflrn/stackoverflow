package com.codecool.stackoverflowtw.dao.model;

public class Question {

    private String questionText;
    private String description;
    private int answerCount;


    public Question(String questionText, String description, int answerCount) {
        this.questionText = questionText;
        this.description = description;
        this.answerCount = answerCount;
    }


    public int getAnswerCount() {
        return answerCount;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getDescription() {
        return description;
    }


}

