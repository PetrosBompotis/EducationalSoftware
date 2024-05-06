package com.petrosb.EducationalSoftware.question;

import com.petrosb.EducationalSoftware.exception.RequestValidationException;
import com.petrosb.EducationalSoftware.exception.ResourceNotFoundException;
import com.petrosb.EducationalSoftware.quiz.Quiz;
import com.petrosb.EducationalSoftware.quiz.QuizDataAccessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionDataAccessService questionDataAccessService;
    private final QuizDataAccessService quizDataAccessService;

    public QuestionService(QuestionDataAccessService questionDataAccessService, QuizDataAccessService quizDataAccessService) {
        this.questionDataAccessService = questionDataAccessService;
        this.quizDataAccessService = quizDataAccessService;
    }

    public List<Question> getAllQuestionsByQuizId(Long quizId){
        if(!quizDataAccessService.existsQuizWithId(quizId)){
            throw new ResourceNotFoundException("Quiz with id [%s] not found".formatted(quizId));
        }
        return questionDataAccessService.selectAllQuestionsByQuizId(quizId);
    }

    public void addQuestion(QuestionCreationRequest questionCreationRequest, Long quizId){
        Quiz quiz = quizDataAccessService.selectQuizByID(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Quiz with id [%s] not found".formatted(quizId)
                ));

        Question question = new Question(
                questionCreationRequest.questionText(),
                questionCreationRequest.correctAnswer(),
                questionCreationRequest.questionType(),
                questionCreationRequest.difficultyLevel(),
                questionCreationRequest.choice1(),
                questionCreationRequest.choice2(),
                questionCreationRequest.choice3(),
                quiz
        );

        questionDataAccessService.insertQuestion(question);
    }

    public void deleteQuestionById(Long questionId){
        //check if id exists
        if(!questionDataAccessService.existsQuestionWithId(questionId)){
            throw new ResourceNotFoundException("Question with id [%s] not found".formatted(questionId));
        }

        //otherwise remove
        questionDataAccessService.deleteQuestionById(questionId);

    }

    public void updateQuestion(QuestionUpdateRequest updateRequest, Long questionId){

        Question question = questionDataAccessService.selectQuestionByID(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question with id [%s] not found".formatted(questionId)
                ));
        boolean changes = false;
        //check if attributes need change exists
        if (updateRequest.questionText() != null && !updateRequest.questionText().equals(question.getQuestionText())){
            question.setQuestionText(updateRequest.questionText());
            changes = true;
        }

        if (updateRequest.correctAnswer() != null && !updateRequest.correctAnswer().equals(question.getCorrectAnswer())){
            question.setCorrectAnswer(updateRequest.correctAnswer());
            changes = true;
        }
        //otherwise update
        if (!changes){
            throw new RequestValidationException("no data changes found");
        }

        questionDataAccessService.updateQuestionById(question);
    }
}
