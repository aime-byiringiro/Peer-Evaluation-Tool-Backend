package edu.tcu.cs.peerevaluation.instructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.criteria.Join;

@Component
public class InstructorSpecifications {
    
    public static Specification<Instructor> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
      }
    
      public static Specification<Instructor> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
      }

      public static Specification<Instructor> inAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) -> {
          // Join `Instructor` with `Team`
          Join<Instructor, Team> instructorTeamJoin = root.join("team");
          // Further join `Team` with `Section`
          Join<Team, Section> teamSectionJoin = instructorTeamJoin.join("section");
          // Compare the `sectionName` in `Section` with the passed parameter
          return criteriaBuilder.equal(teamSectionJoin.get("academicYear"), academicYear);
        };
      }

      public static Specification<Instructor> onTeam(String teamName) {
        return (root, query, criteriaBuilder) -> {
          // Join `Instructor` with `Team`
          Join<Instructor, Team> instructorTeamJoin = root.join("team");
          // Compare the `sectionName` in `Section` with the passed parameter
          return criteriaBuilder.equal(instructorTeamJoin.get("teamName"), teamName);
        };
      }

      public static Specification<Instructor> isPeerEvalUserEnabled(boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            // Join `Instructor` with `PeerEvalUser`
            Join<Instructor, PeerEvalUser> instructorUserJoin = root.join("user");
            // Compare the `enabled` in `PeerEvalUser` with the passed boolean parameter
            return criteriaBuilder.equal(instructorUserJoin.get("enabled"), enabled);
        };
      }
}
