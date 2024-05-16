package com.petrosb.EducationalSoftware.attempt;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttemptDataAccessService {
    private final AttemptRepository attemptRepository;

    public AttemptDataAccessService(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    public List<Attempt> selectAllAttemptsByCustomerId(Long customerId){
        return attemptRepository.findByCustomerId(customerId);
    }

    public Optional<Attempt> selectAttemptByID(Long id) {
        return attemptRepository.findById(id);
    }

    public Attempt insertAttempt(Attempt attempt) {
        return attemptRepository.save(attempt);
    }

    public boolean existsAttemptWithId(Long id) {
        return attemptRepository.existsAttemptById(id);
    }

    public void deleteAttemptById(Long attemptId) {
        attemptRepository.deleteById(attemptId);
    }

    public void updateAttemptById(Attempt attempt) {
        attemptRepository.save(attempt);
    }

}
