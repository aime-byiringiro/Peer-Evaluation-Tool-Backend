package edu.tcu.cs.peerevaluation.rubric;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RubricService {

    private final RubricRepository rubricRepository;

    public RubricService(RubricRepository rubricRepository) {
        this.rubricRepository = rubricRepository;
    }

    public Rubric save(Rubric newRubric) {
        return this.rubricRepository.save(newRubric);
    }
    
}
