package com.petrosb.EducationalSoftware.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petrosb.EducationalSoftware.customer.Customer;
import com.petrosb.EducationalSoftware.quiz.Quiz;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "attempt")
public class Attempt {
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
    private Float score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Attempt() {
    }

    public Attempt(Float score, Customer customer, Quiz quiz) {
        this.score = score;
        this.customer = customer;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Attempt attempt = (Attempt) o;
        return score == attempt.score && Objects.equals(id, attempt.id) && Objects.equals(customer, attempt.customer) && Objects.equals(quiz, attempt.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, customer, quiz);
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", score=" + score +
                ", customer=" + customer +
                ", quiz=" + quiz +
                '}';
    }
}
