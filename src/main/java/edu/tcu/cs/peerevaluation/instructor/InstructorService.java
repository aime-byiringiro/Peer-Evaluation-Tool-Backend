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

  public List<Instructor> search(String firstName, String lastName, String academicYear, String teamName) {
    return instructorRepository.findAll(Specification.where(hasFirstName(firstName))
        .and(hasLastName(lastName))
        .and(inAcademicYear(academicYear))
        .and(inTeam(teamName)));
  }

  private Specification<Instructor> hasFirstName(String firstName) {
    return (root, query, criteriaBuilder) -> 
           criteriaBuilder.equal(root.get("firstName"), firstName);
  }

  private Specification<Instructor> hasLastName(String lastName) {
    return (root, query, criteriaBuilder) -> 
           criteriaBuilder.equal(root.get("lastName"), lastName);
  }

  private Specification<Instructor> inAcademicYear(String academicYear) {
    return (root, query, criteriaBuilder) -> 
           criteriaBuilder.equal(root.join("teams").join("section").get("academicYear"), academicYear);
  }

  private Specification<Instructor> inTeam(String teamName) {
    return (root, query, criteriaBuilder) -> 
           criteriaBuilder.equal(root.join("teams").get("teamName"), teamName);
  }

  public Instructor save(Instructor instructor) {
    return this.instructorRepository.save(instructor);
}
}
