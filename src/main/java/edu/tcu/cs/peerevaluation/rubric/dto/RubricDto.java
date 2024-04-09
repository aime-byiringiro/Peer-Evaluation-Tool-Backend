package edu.tcu.cs.peerevaluation.rubric.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;


public record RubricDto(String rubricName,
                        List<CriterionDto> criterionDto) {
}
