package com.petrosb.EducationalSoftware.question;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/quizzes/{quizId}/questions")
    public List<Question> getAllQuestionsByQuizId(@PathVariable(value = "quizId") Long quizId) {
        return questionService.getAllQuestionsByQuizId(quizId);
    }

    @PostMapping("/quizzes/{quizId}/questions")
    public void createQuestion(@PathVariable(value = "quizId") Long quizId,
                              @RequestBody QuestionCreationRequest questionCreationRequest) {
        questionService.addQuestion(questionCreationRequest, quizId);
    }

    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteQuestionById(questionId);
    }

    @PutMapping("/questions/{questionId}")
    public void updateQuestion(@RequestBody QuestionUpdateRequest updateRequest,
                              @PathVariable("questionId") Long questionId) {
        questionService.updateQuestion(updateRequest, questionId);
    }
}
