package com.petrosb.EducationalSoftware.answer;

import com.petrosb.EducationalSoftware.attempt.Attempt;
import com.petrosb.EducationalSoftware.attempt.AttemptDataAccessService;
import com.petrosb.EducationalSoftware.exception.RequestValidationException;
import com.petrosb.EducationalSoftware.exception.ResourceNotFoundException;
import com.petrosb.EducationalSoftware.question.Question;
import com.petrosb.EducationalSoftware.question.QuestionDataAccessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerDataAccessService answerDataAccessService;
    private final AttemptDataAccessService attemptDataAccessService;
    private final QuestionDataAccessService questionDataAccessService;

    public AnswerService(AnswerDataAccessService answerDataAccessService, AttemptDataAccessService attemptDataAccessService, QuestionDataAccessService questionDataAccessService) {
        this.answerDataAccessService = answerDataAccessService;
        this.attemptDataAccessService = attemptDataAccessService;
        this.questionDataAccessService = questionDataAccessService;
    }

    public List<Answer> getAllAnswersByAttemptId(Long attemptId){
        if(!attemptDataAccessService.existsAttemptWithId(attemptId)){
            throw new ResourceNotFoundException("Attempt with id [%s] not found".formatted(attemptId));
        }
        return answerDataAccessService.selectAllAnswersByAttemptId(attemptId);
    }

    public void addAnswer(AnswerCreationRequest answerCreationRequest, Long attemptId, Long questionId){
        Attempt attempt = attemptDataAccessService.selectAttemptByID(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attempt with id [%s] not found".formatted(attemptId)
                ));

        Question question = questionDataAccessService.selectQuestionByID(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Question with id [%s] not found".formatted(questionId)
        ));

        Answer answer = new Answer(
                answerCreationRequest.userAnswer(),
                answerCreationRequest.isCorrect(),
                attempt,
                question
        );

        answerDataAccessService.insertAnswer(answer);
    }

    public void deleteAnswerById(Long answerId){
        //check if id exists
        if(!answerDataAccessService.existsAnswerWithId(answerId)){
            throw new ResourceNotFoundException("Answer with id [%s] not found".formatted(answerId));
        }

        //otherwise remove
        answerDataAccessService.deleteAnswerById(answerId);

    }

    public void updateAnswer(AnswerUpdateRequest updateRequest, Long answerId){

        Answer answer = answerDataAccessService.selectAnswerByID(answerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Answer with id [%s] not found".formatted(answerId)
                ));
        boolean changes = false;
        //check if attributes need change exists
        if (updateRequest.isCorrect() != null && !updateRequest.isCorrect().equals(answer.getCorrect())){
            answer.setCorrect(updateRequest.isCorrect());
            changes = true;
        }
        //otherwise update
        if (!changes){
            throw new RequestValidationException("no data changes found");
        }

        answerDataAccessService.updateAnswerById(answer);
    }
}
