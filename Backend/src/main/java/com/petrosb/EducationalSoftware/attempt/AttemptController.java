package com.petrosb.EducationalSoftware.attempt;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AttemptController {
    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping("/customers/{customerId}/attempts")
    public List<Attempt> getAllAttemptsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return attemptService.getAllAttemptsByCustomerId(customerId);
    }

    @GetMapping("/customers/{customerId}/quizzes/{quizId}/attempts")
    public List<Attempt> getAttemptsByCustomerIdAndQuizId(@PathVariable(value = "customerId") Long customerId,
                                                          @PathVariable(value = "quizId") Long quizId) {
        return attemptService.getAttemptsByCustomerIdAndQuizId(customerId, quizId);
    }

    @PostMapping("/customers/{customerId}/quizzes/{quizId}/attempts")
    public Attempt createAttempt(@PathVariable(value = "customerId") Long customerId,
                                  @PathVariable(value = "quizId") Long quizId,
                                  @RequestBody AttemptCreationRequest attemptCreationRequest) {
        return attemptService.addAttempt(attemptCreationRequest, customerId, quizId);
    }

    @DeleteMapping("/attempts/{attemptId}")
    public void deleteAttempt(@PathVariable("attemptId") Long attemptId) {
        attemptService.deleteAttemptById(attemptId);
    }

    @PutMapping("/attempts/{attemptId}")
    public void updateAttempt(@RequestBody AttemptUpdateRequest updateRequest,
                                  @PathVariable("attemptId") Long attemptId) {
        attemptService.updateAttempt(updateRequest, attemptId);
    }
}
