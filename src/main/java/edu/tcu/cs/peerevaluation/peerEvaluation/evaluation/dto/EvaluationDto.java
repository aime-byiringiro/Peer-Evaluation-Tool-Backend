package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

public record EvaluationDto(@NotEmpty(message = "evaluated is required.")
                            StudentDto evaluated,
                            @NotEmpty(message = "scores is required.")
                            List<Integer> scores,
                            String privateComments,
                            String publicComments) {

}
