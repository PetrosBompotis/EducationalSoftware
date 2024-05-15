package com.petrosb.EducationalSoftware.question;

import com.petrosb.EducationalSoftware.Level;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDataAccessService {
    private final QuestionRepository questionRepository;

    public QuestionDataAccessService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public List<Question> selectAllQuestionsByQuizId(Long quizId){
        return questionRepository.findByQuizId(quizId);
    }

    public List<Question> selectAllQuestionsByDifficultyLevel(Level difficultyLevel){
        return questionRepository.findByDifficultyLevel(difficultyLevel);
    }

    public Optional<Question> selectQuestionByID(Long id) {
        return questionRepository.findById(id);
    }

    public void insertQuestion(Question question) {
        questionRepository.save(question);
    }

    public boolean existsQuestionWithId(Long id) {
        return questionRepository.existsQuestionById(id);
    }

    public void deleteQuestionById(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    public void updateQuestionById(Question question) {
        questionRepository.save(question);
    }

}
