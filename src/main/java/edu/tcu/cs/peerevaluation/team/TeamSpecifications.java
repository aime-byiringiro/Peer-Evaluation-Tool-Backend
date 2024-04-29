package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeamSpecifications {

    public static Specification<Team> hasTeamName(String teamName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("teamName"), "%" + teamName + "%");
    }

    public static Specification<Team> hasSectionName(String sectionName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("sectionName"), "%" + sectionName + "%");
    }

    public static Specification<Team> hasAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("academicYear"), "%" + academicYear + "%");
    }

    public static Specification<Team> hasInstructorFirstName(String instructorFirstName) {
        return (root, query, criteriaBuilder) -> {
            Join<Team, Instructor> instructorJoin = root.join("instructors");
            return criteriaBuilder.equal(instructorJoin.get("last_name"), instructorFirstName);
        };
    }

    public static Specification<Team> hasInstructorLastName(String instructorLastName) {
        return (root, query, criteriaBuilder) -> {
            Join<Team, Instructor> instructorJoin = root.join("instructors");
            return criteriaBuilder.equal(instructorJoin.get("last_name"), instructorLastName);
        };
    }
}
