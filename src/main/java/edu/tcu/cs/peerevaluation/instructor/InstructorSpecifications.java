package edu.tcu.cs.peerevaluation.instructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.criteria.Join;

@Component
public class InstructorSpecifications {

    public static Specification<Instructor> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Instructor> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Instructor> inAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) -> {
            Join<Instructor, Team> instructorTeamJoin = root.join("teams");
            Join<Team, Section> teamSectionJoin = instructorTeamJoin.join("section");
            return criteriaBuilder.equal(teamSectionJoin.get("academicYear"), academicYear);
        };
    }

    public static Specification<Instructor> onTeam(String teamName) {
        return (root, query, criteriaBuilder) -> {
            Join<Instructor, Team> instructorTeamJoin = root.join("teams");
            return criteriaBuilder.equal(instructorTeamJoin.get("teamName"), teamName);
        };
    }
}
