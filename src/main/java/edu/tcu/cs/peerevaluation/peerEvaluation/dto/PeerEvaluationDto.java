package edu.tcu.cs.peerevaluation.peerEvaluation.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

public record PeerEvaluationDto(Integer id,
                                StudentDto evaluator,
                                List<EvaluationDto> evaluations,
                                @NotEmpty(message = "Week is required.")
                                String week) {
}
