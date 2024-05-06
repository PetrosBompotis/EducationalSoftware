package com.petrosb.EducationalSoftware.answer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AnswerDataAccessService {
    private final AnswerRepository answerRepository;

    public AnswerDataAccessService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> selectAllAnswersByAttemptId(Long attemptId){
        return answerRepository.findByAttemptId(attemptId);
    }

    public Optional<Answer> selectAnswerByID(Long id) {
        return answerRepository.findById(id);
    }

    public void insertAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    public boolean existsAnswerWithId(Long id) {
        return answerRepository.existsAnswerById(id);
    }

    public void deleteAnswerById(Long answerId) {
        answerRepository.deleteById(answerId);
    }

    public void updateAnswerById(Answer answer) {
        answerRepository.save(answer);
    }

}
