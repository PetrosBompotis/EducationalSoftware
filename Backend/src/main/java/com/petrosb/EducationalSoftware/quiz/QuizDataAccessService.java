package com.petrosb.EducationalSoftware.quiz;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizDataAccessService {
    private final QuizRepository quizRepository;

    public QuizDataAccessService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> selectAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> selectQuizByID(Long id) {
        return quizRepository.findById(id);
    }

    public void insertQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuizById(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    public boolean existsQuizWithId(Long id) {
        return quizRepository.existsQuizById(id);
    }

    public void updateQuizById(Quiz quiz) {
        quizRepository.save(quiz);
    }

}
