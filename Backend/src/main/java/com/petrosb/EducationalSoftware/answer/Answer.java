package com.petrosb.EducationalSoftware.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petrosb.EducationalSoftware.attempt.Attempt;
import com.petrosb.EducationalSoftware.question.Question;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @SequenceGenerator(
            name = "routine_id_seq",
            sequenceName = "routine_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "routine_id_seq"
    )
    @Column(
            name = "id",
            updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String userAnswer;

    @Column
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Attempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() {
    }

    public Answer(String userAnswer, Boolean isCorrect, Attempt attempt, Question question) {
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
        this.attempt = attempt;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) && Objects.equals(userAnswer, answer.userAnswer) && Objects.equals(isCorrect, answer.isCorrect) && Objects.equals(attempt, answer.attempt) && Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAnswer, isCorrect, attempt, question);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", userAnswer='" + userAnswer + '\'' +
                ", isCorrect=" + isCorrect +
                ", attempt=" + attempt +
                ", question=" + question +
                '}';
    }
}
