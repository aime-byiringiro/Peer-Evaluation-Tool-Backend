package edu.tcu.cs.peerevaluation.student;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.student.utils.IdWorker;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> findAll() {
    return this.studentRepository.findAll();
  }

}
