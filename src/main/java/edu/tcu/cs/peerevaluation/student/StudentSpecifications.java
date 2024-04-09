package edu.tcu.cs.peerevaluation.student;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.criteria.Join;

@Component
public class StudentSpecifications {

  public static Specification<Student> hasFirstName(String firstName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
  }

  public static Specification<Student> hasLastName(String lastName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
  }

  public static Specification<Student> inSection(String sectionName) {
    return (root, query, criteriaBuilder) -> {
      // Join `Student` with `Team`
      Join<Student, Team> studentTeamJoin = root.join("team");
      // Further join `Team` with `Section`
      Join<Team, Section> teamSectionJoin = studentTeamJoin.join("section");
      // Compare the `sectionName` in `Section` with the passed parameter
      return criteriaBuilder.equal(teamSectionJoin.get("sectionName"), sectionName);
    };
  }

  public static Specification<Student> inAcademicYear(String academicYear) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("academicYear"), academicYear);
  }

  public static Specification<Student> onTeam(String teamName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("team").get("name"), teamName);
  }

}

/*
  public static Specification<Student> inSection(String sectionName) {
    return (root, query, criteriaBuilder) -> {
      // Perform a join with the Section entity
      Join<Student, Section> sectionJoin = root.join("section");
      // Specify the criteria to match the sectionName field within the Section entity
      return criteriaBuilder.equal(sectionJoin.get("sectionName"), sectionName);
    };
  }
 */
