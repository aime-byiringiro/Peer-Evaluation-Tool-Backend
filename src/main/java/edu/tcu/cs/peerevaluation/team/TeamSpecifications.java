package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.section.Section;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeamSpecifications {

    public static Specification<Team> hasTeamName(String teamName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("teamName"), "%" + teamName + "%");
    }

    public static Specification<Team> hasSectionName(String sectionName) {
        return (root, query, criteriaBuilder) -> {
            Join<Team, Section> sectionJoin = root.join("section");
            return criteriaBuilder.equal(sectionJoin.get("sectionName"), sectionName);
        };
    }

    public static Specification<Team> hasAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("academicYear"), "%" + academicYear + "%");
    }

    public static Specification<Team> hasInstructorFirstName(String instructorFirstName) {
        return (root, query, criteriaBuilder) -> {
            Join<Team, Instructor> instructorJoin = root.join("instructor");
            return criteriaBuilder.equal(instructorJoin.get("firstName"), instructorFirstName);
        };
    }

    public static Specification<Team> hasInstructorLastName(String instructorLastName) {
        return (root, query, criteriaBuilder) -> {
            Join<Team, Instructor> instructorJoin = root.join("instructor");
            return criteriaBuilder.equal(instructorJoin.get("lastName"), instructorLastName);
        };
    }
}
