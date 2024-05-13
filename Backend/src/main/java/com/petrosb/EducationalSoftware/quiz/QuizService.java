package com.petrosb.EducationalSoftware.quiz;

import com.petrosb.EducationalSoftware.exception.RequestValidationException;
import com.petrosb.EducationalSoftware.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizDataAccessService quizDataAccessService;

    public QuizService(QuizDataAccessService quizDataAccessService) {
        this.quizDataAccessService = quizDataAccessService;
    }

    public List<Quiz> getAllQuizzes(){
        return quizDataAccessService.selectAllQuizzes();
    }

    public Quiz getQuiz(Long id){
        return quizDataAccessService.selectQuizByID(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Quiz with id [%s] not found".formatted(id)
                ));
    }

    public void addQuiz(QuizCreationRequest quizCreationRequest){

        Quiz quiz = new Quiz(
                quizCreationRequest.title(),
                quizCreationRequest.description()
        );
        quizDataAccessService.insertQuiz(quiz);

    }

    public void deleteQuizById(Long quizId){

        //check if id exists
        if(!quizDataAccessService.existsQuizWithId(quizId)){
            throw new ResourceNotFoundException("Quiz with id [%s] not found".formatted(quizId));
        }

        //otherwise remove
        quizDataAccessService.deleteQuizById(quizId);

    }

    public void updateQuiz(QuizUpdateRequest updateRequest, Long quizId){

        Quiz quiz = quizDataAccessService.selectQuizByID(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Quiz with id [%s] not found".formatted(quizId)
                ));
        boolean changes = false;
        //check if attributes need change exists
        if (updateRequest.title() != null && !updateRequest.title().equals(quiz.getTitle())){
            quiz.setTitle(updateRequest.title());
            changes = true;
        }

        if (updateRequest.description() != null && !updateRequest.description().equals(quiz.getDescription())){
            quiz.setDescription(updateRequest.description());
            changes = true;
        }
        //otherwise update

        if (!changes){
            throw new RequestValidationException("no data changes found");
        }

        quizDataAccessService.updateQuizById(quiz);
    }
}
