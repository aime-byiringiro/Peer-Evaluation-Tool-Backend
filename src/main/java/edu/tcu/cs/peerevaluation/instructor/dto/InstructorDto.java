package edu.tcu.cs.peerevaluation.instructor.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.validation.constraints.NotEmpty;

public record InstructorDto(Integer id,
                            @NotEmpty(message = "first name is required.")
                            String firstName,
                            String middleInitial,
                            @NotEmpty(message = "last name is required.")
                            String lastName,
                            String email,
                            List<String> teams) {


}
