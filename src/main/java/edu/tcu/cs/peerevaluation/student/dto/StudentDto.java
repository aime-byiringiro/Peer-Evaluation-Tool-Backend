package edu.tcu.cs.peerevaluation.student.dto;

import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.validation.constraints.NotEmpty;

public record StudentDto(String id,
                         @NotEmpty(message = "first name is required.")
                         String firstName,
                         String middleInital,
                         @NotEmpty(message = "last name is required.")
                         String lastName,
                         @NotEmpty(message = "email is required.")
                         String email,
                         Team team) {
}
