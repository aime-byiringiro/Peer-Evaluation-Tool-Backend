package edu.tcu.cs.peerevaluation.student;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
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

  public List<Student> searchStudents(String firstName, String lastName, String section, String academicYear,
      String teamName) {
    Specification<Student> spec = Specification.where(null);

    if (firstName != null && !firstName.isEmpty()) {
      spec = spec.and(StudentSpecifications.hasFirstName(firstName));
    }

    if (lastName != null && !lastName.isEmpty()) {
      spec = spec.and(StudentSpecifications.hasLastName(lastName));
    }

    if (section != null && !section.isEmpty()) {
      spec = spec.and(StudentSpecifications.inSection(section));
    }

    if (academicYear != null && !academicYear.isEmpty()) {
      spec = spec.and(StudentSpecifications.inAcademicYear(academicYear));
    }

    if (teamName != null && !teamName.isEmpty()) {
      spec = spec.and(StudentSpecifications.onTeam(teamName));
    }

    return studentRepository.findAll(spec);
  }

  public Student findById(Integer studentId) {
    return this.studentRepository.findById(studentId)
            .orElseThrow(() -> new ObjectNotFoundException("student",studentId)); 
  }

  public Student save(Student newStudent) {
    return this.studentRepository.save(newStudent);
  }

  public Student update(Integer studentId, Student update) {
    return this.studentRepository.findById(studentId)
              .map(oldStudent -> {
                oldStudent.setFirstName(update.getFirstName());
                oldStudent.setLastName(update.getLastName());
                oldStudent.setMiddleInitial(update.getMiddleInitial());
                oldStudent.setEmail(update.getEmail());
                return this.studentRepository.save(oldStudent);
              })
              .orElseThrow(() -> new ObjectNotFoundException("student", studentId));
  }

  public void delete(Integer studentId) {
    this.studentRepository.findById(studentId)
              .orElseThrow(() -> new ObjectNotFoundException("student", studentId));
    this.studentRepository.deleteById(studentId);
  }

}
