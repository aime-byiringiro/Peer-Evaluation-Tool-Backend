package edu.tcu.cs.peerevaluation.student;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StudentSpecifications {

  public static Specification<Student> hasFirstName(String firstName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
  }

  public static Specification<Student> hasLastName(String lastName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
  }

  public static Specification<Student> inSection(String section) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("section"), section);
  }

  public static Specification<Student> inAcademicYear(String academicYear) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("academicYear"), academicYear);
  }

  public static Specification<Student> onTeam(String teamName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("team").get("name"), teamName);
  }

}
