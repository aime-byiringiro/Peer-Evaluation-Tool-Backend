package edu.tcu.cs.peerevaluation.rubric;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

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

    public List<Rubric> findAll() {
        return this.rubricRepository.findAll();
    }
    
}
