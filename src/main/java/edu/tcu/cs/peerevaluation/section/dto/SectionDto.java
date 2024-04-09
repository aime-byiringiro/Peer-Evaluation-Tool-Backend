package edu.tcu.cs.peerevaluation.section.dto;

import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SectionDto(String id,
                         @NotEmpty(message = "Academic Year is required")
                         String academicYear,
                         @NotEmpty(message = "Team is required")
                         List<Team> teams) {
}
