package com.codecool.stackoverflowtw.controller.dto;

import java.time.LocalDateTime;

public record AnswerDTO(int id, String answer, LocalDateTime created, int question_id) {
}
