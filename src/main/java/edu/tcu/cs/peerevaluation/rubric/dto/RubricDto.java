package edu.tcu.cs.peerevaluation.rubric.dto;

import java.util.List;
import java.util.Optional;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import jakarta.validation.constraints.*;


public record RubricDto(


        Integer id,
                        @NotEmpty(message = "rubric name required")
                        String rubricName,

                        @NotEmpty(message =  "criterion required")
                        List<Criterion> criterion) {
}
