package com.petrosb.EducationalSoftware.attempt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByCustomerId(Long customerId);
    boolean existsAttemptById(Long id);
}
