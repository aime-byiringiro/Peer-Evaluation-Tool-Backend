package edu.tcu.cs.peerevaluation.rubric.criterion.dto;

import jakarta.validation.constraints.NotEmpty;

public record CriterionDto(Integer id,
                           @NotEmpty(message = "description is required")
                           String description,
                           @NotEmpty(message = "description is required")
                           String criterionName,
                           @NotEmpty(message = "description is required")
                           Integer maxScore,
                           Integer rubricId) {
}
