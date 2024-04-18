package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

public record EvaluationDto(Integer id,
                            @NotEmpty(message = "evaluated is required.")
                            StudentDto evaluated,
                            Integer peerEvalId,
                            @NotEmpty(message = "scores is required.")
                            List<Integer> scores,
                            Integer totalScore,
                            String privateComments,
                            String publicComments,
                            String evaluator) {

}
