package com.petrosb.EducationalSoftware.attempt;

import com.petrosb.EducationalSoftware.customer.Customer;
import com.petrosb.EducationalSoftware.customer.CustomerDataAccessService;
import com.petrosb.EducationalSoftware.exception.RequestValidationException;
import com.petrosb.EducationalSoftware.exception.ResourceNotFoundException;
import com.petrosb.EducationalSoftware.quiz.Quiz;
import com.petrosb.EducationalSoftware.quiz.QuizDataAccessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptService {
    private final AttemptDataAccessService attemptDataAccessService;
    private final CustomerDataAccessService customerDataAccessService;
    private final QuizDataAccessService quizDataAccessService;

    public AttemptService(AttemptDataAccessService attemptDataAccessService, CustomerDataAccessService customerDataAccessService, QuizDataAccessService quizDataAccessService) {
        this.attemptDataAccessService = attemptDataAccessService;
        this.customerDataAccessService = customerDataAccessService;
        this.quizDataAccessService = quizDataAccessService;
    }

    public List<Attempt> getAllAttemptsByCustomerId(Long customerId){
        if(!customerDataAccessService.existsPersonWithId(customerId)){
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId));
        }
        return attemptDataAccessService.selectAllAttemptsByCustomerId(customerId);
    }

    public void addAttempt(AttemptCreationRequest attemptCreationRequest, Long customerId, Long quizId){
        Customer customer = customerDataAccessService.selectCustomerByID(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(customerId)
                ));

        Quiz quiz = quizDataAccessService.selectQuizByID(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Quiz with id [%s] not found".formatted(quizId)
        ));

        Attempt attempt = new Attempt(
                attemptCreationRequest.score(),
                customer,
                quiz
        );

        attemptDataAccessService.insertAttempt(attempt);
    }

    public void deleteAttemptById(Long attemptId){
        //check if id exists
        if(!attemptDataAccessService.existsAttemptWithId(attemptId)){
            throw new ResourceNotFoundException("Attempt with id [%s] not found".formatted(attemptId));
        }

        //otherwise remove
        attemptDataAccessService.deleteAttemptById(attemptId);

    }

    public void updateAttempt(AttemptUpdateRequest updateRequest, Long attemptId){

        Attempt attempt = attemptDataAccessService.selectAttemptByID(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attempt with id [%s] not found".formatted(attemptId)
                ));
        boolean changes = false;
        //check if attributes need change exists
        if (updateRequest.score() != null && !updateRequest.score().equals(attempt.getScore())){
            attempt.setScore(updateRequest.score());
            changes = true;
        }

        //otherwise update
        if (!changes){
            throw new RequestValidationException("no data changes found");
        }

        attemptDataAccessService.updateAttemptById(attempt);
    }
}
