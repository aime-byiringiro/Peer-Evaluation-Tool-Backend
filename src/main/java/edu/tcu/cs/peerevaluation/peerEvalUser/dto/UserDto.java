package edu.tcu.cs.peerevaluation.peerEvalUser.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(Integer id,
                      @NotEmpty(message = "username is required.")
                      String username,
                      @NotEmpty(message = "password is required.")
                      String password,
                      boolean enabled,
                      @NotEmpty(message = "roles are required.")
                      String roles) {
}
