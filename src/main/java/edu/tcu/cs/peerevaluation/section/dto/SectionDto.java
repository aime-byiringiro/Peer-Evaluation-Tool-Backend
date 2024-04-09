package edu.tcu.cs.peerevaluation.section.dto;

import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SectionDto(String id,
                        @NotEmpty(message = "Section Name is required")
                         String sectionName,
                         @NotEmpty(message = "Academic Year is required")
                         String academicYear,
                         @NotEmpty(message = "First Day is required")
                         String firstDay,
                         @NotEmpty(message = "Last Day is required")
                         String lastDay,
                         @NotEmpty(message = "Rubric is required")
                         RubricDto rubricDto,
                         @NotEmpty(message = "Team is required")
                         List<Team> teams) {
}
