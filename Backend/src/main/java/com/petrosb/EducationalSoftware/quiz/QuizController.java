package com.petrosb.EducationalSoftware.quiz;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("{quizId}")
    public Quiz getQuiz(@PathVariable("quizId") Long quizId) {
        return quizService.getQuiz(quizId);
    }

    @PostMapping
    public void createQuiz(@RequestBody QuizCreationRequest request){
        quizService.addQuiz(request);
    }

    @DeleteMapping("{quizId}")
    public void deleteQuiz(@PathVariable("quizId") Long quizId){
        quizService.deleteQuizById(quizId);
    }

    @PutMapping("{quizId}")
    public void updateQuiz(@RequestBody QuizUpdateRequest updateRequest, @PathVariable("quizId") Long quizId) {
        quizService.updateQuiz(updateRequest, quizId);
    }
}
