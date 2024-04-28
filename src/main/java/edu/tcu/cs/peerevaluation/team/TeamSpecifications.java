package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeamSpecifications {

    public static Specification<Team> hasTeamName(String teamName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("teamName"), "%" + teamName + "%");
    }

}
