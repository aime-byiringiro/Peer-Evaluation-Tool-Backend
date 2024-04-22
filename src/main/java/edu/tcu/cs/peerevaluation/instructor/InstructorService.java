package edu.tcu.cs.peerevaluation.instructor;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstructorService {

  public final InstructorRepository instructorRepository;

  public InstructorService(InstructorRepository instructorRepository) {
    this.instructorRepository = instructorRepository;
  }

  public Instructor findById(Integer instructorId) {
    return this.instructorRepository.findById(instructorId)
        .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
  }

}
