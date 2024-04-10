package edu.tcu.cs.peerevaluation.student.dto;

import jakarta.validation.constraints.NotEmpty;

public record StudentDto(Integer id,
                         @NotEmpty(message = "first name is required.")
                         String firstName,
                         String middleInital,
                         @NotEmpty(message = "last name is required.")
                         String lastName,
                         @NotEmpty(message = "email is required.")
                         String email,
                         String teamName) {
}