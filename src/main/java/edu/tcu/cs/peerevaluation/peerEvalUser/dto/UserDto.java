package edu.tcu.cs.peerevaluation.peerEvalUser.dto;

import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(Integer id,
                      @NotEmpty(message = "username is required.")
                      String username,
                      boolean enabled,
                      @NotEmpty(message = "roles are required.")
                      String roles,
                      StudentDto student) {
}
