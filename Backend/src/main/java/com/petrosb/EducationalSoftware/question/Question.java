package com.petrosb.EducationalSoftware.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petrosb.EducationalSoftware.Level;
import com.petrosb.EducationalSoftware.quiz.Quiz;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;


@Entity
@Table(name = "question")
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_id_seq",
            sequenceName = "question_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_id_seq"
    )
    private Long id;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false, length = 100)
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level difficultyLevel;

    @Column(length = 100)
    private String choice1;

    @Column(length = 100)
    private String choice2;

    @Column(length = 100)
    private String choice3;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Quiz quiz;

    public Question() {
    }

    public Question(String questionText, String correctAnswer, QuestionType questionType,
                    Level difficultyLevel, String choice1, String choice2, String choice3, Quiz quiz) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.difficultyLevel = difficultyLevel;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Level getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Level difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(questionText, question.questionText) && Objects.equals(correctAnswer, question.correctAnswer) && questionType == question.questionType && difficultyLevel == question.difficultyLevel && Objects.equals(choice1, question.choice1) && Objects.equals(choice2, question.choice2) && Objects.equals(choice3, question.choice3) && Objects.equals(quiz, question.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, correctAnswer, questionType, difficultyLevel, choice1, choice2, choice3, quiz);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", questionType=" + questionType +
                ", difficultyLevel=" + difficultyLevel +
                ", choice1='" + choice1 + '\'' +
                ", choice2='" + choice2 + '\'' +
                ", choice3='" + choice3 + '\'' +
                ", quiz=" + quiz +
                '}';
    }
}

