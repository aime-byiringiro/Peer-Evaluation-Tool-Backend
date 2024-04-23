package edu.tcu.cs.peerevaluation.peerEvaluation.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;

public record PeerEvaluationDto(Integer id,
                                StudentDto evaluator,
                                List<EvaluationDto> evaluations,
                                Integer week) {
}
