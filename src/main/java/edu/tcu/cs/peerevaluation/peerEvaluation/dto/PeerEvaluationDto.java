package edu.tcu.cs.peerevaluation.peerEvaluation.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

public record PeerEvaluationDto(String id,
                                StudentDto evaluator,
                                List<Evaluation> evaluations,
                                @NotEmpty(message = "Week is required.")
                                String week) {
}
