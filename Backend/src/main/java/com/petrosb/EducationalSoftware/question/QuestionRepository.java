package com.petrosb.EducationalSoftware.question;

import com.petrosb.EducationalSoftware.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);
    List<Question> findByDifficultyLevel(Level difficultyLevel);
    boolean existsQuestionById(Long id);
}
