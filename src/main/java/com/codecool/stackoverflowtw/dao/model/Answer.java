package com.codecool.stackoverflowtw.dao.model;

public class Answer {
    private final String answer;
    private final int question_id;

    public Answer(String answer, int questionId) {
        this.answer = answer;
        question_id = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuestion_id() {
        return question_id;
    }
}
