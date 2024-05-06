package com.petrosb.EducationalSoftware.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsQuizById(Long id);
}
