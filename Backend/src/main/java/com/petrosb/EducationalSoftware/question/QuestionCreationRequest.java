package com.petrosb.EducationalSoftware.question;

import com.petrosb.EducationalSoftware.Level;

public record QuestionCreationRequest(
        String questionText,

        String correctAnswer,

        QuestionType questionType,

        Level difficultyLevel,

        String choice1,

        String choice2,

        String choice3
) {
}
