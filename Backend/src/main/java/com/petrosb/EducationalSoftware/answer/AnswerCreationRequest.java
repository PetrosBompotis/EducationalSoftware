package com.petrosb.EducationalSoftware.answer;

public record AnswerCreationRequest(
        String userAnswer,

        Boolean isCorrect
) {
}
