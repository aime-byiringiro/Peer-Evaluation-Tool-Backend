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

  public List<Instructor> findAll() {
    return this.instructorRepository.findAll();
  }

  public Instructor findById(Integer instructorId) {
    return this.instructorRepository.findById(instructorId)
        .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
  }

  public List<Instructor> search(String firstName, String lastName, String academicYear, String teamName) {
    Specification<Instructor> spec = Specification.where(null);

    if (firstName != null && !firstName.isEmpty()) {
        spec = spec.and(InstructorSpecifications.hasFirstName(firstName));
    }
    if (lastName != null && !lastName.isEmpty()) {
        spec = spec.and(InstructorSpecifications.hasLastName(lastName));
    }
    if (academicYear != null && !academicYear.isEmpty()) {
        spec = spec.and(InstructorSpecifications.inAcademicYear(academicYear));
    }
    if (teamName != null && !teamName.isEmpty()) {
        spec = spec.and(InstructorSpecifications.onTeam(teamName));
    }

    return instructorRepository.findAll(spec);
}

  public Instructor save(Instructor instructor) {
    return this.instructorRepository.save(instructor);
}
}
