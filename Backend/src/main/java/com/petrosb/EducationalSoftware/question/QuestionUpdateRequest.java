package com.petrosb.EducationalSoftware.question;

public record QuestionUpdateRequest(
        String questionText,

        String correctAnswer
) {
}
