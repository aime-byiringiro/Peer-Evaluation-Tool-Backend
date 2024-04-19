package edu.tcu.cs.peerevaluation.instructor;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstructorService {

  public final InstructorRepository instructorRepository;

  public InstructorService(InstructorRepository instructorRepository) {
    this.instructorRepository = instructorRepository;
  }

  public List<Instructor> findAll() {
    return this.instructorRepository.findAll();
  }

}
