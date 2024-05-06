package com.petrosb.EducationalSoftware.answer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/attempts/{attemptId}/answers")
    public List<Answer> getAllAnswersByAttemptId(@PathVariable(value = "attemptId") Long attemptId) {
        return answerService.getAllAnswersByAttemptId(attemptId);
    }

    @PostMapping("/attempts/{attemptId}/questions/{questionId}/answers")
    public void createAnswer(@PathVariable(value = "attemptId") Long attemptId,
                              @PathVariable(value = "questionId") Long questionId,
                              @RequestBody AnswerCreationRequest answerCreationRequest) {
        answerService.addAnswer(answerCreationRequest, attemptId, questionId);
    }

    @DeleteMapping("/answers/{answerId}")
    public void deleteAnswer(@PathVariable("answerId") Long answerId) {
        answerService.deleteAnswerById(answerId);
    }

    @PutMapping("/answers/{answerId}")
    public void updateAnswer(@RequestBody AnswerUpdateRequest updateRequest,
                              @PathVariable("answerId") Long answerId) {
        answerService.updateAnswer(updateRequest, answerId);
    }
}
