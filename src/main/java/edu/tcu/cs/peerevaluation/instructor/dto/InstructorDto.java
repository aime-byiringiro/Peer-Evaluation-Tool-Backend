package edu.tcu.cs.peerevaluation.instructor.dto;

import jakarta.validation.constraints.NotEmpty;

public record InstructorDto(String id,
                            @NotEmpty(message = "first name is required.")
                            String firstName,
                            String middleInitial,
                            @NotEmpty(message = "last name is required.")
                            String lastName,
                            String email) {
}
