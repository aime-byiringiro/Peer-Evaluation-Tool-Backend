package edu.tcu.cs.peerevaluation.peerEvaluation.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter.EvaluationDtoToEvaluationConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentDtoToStudentConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class PeerEvaluationDtoToPeerEvaluationConverter implements Converter<PeerEvaluationDto, PeerEvaluation> {

  private final StudentDtoToStudentConverter studentDtoToStudentConverter;

  private final EvaluationDtoToEvaluationConverter evaluationConverter;

  public PeerEvaluationDtoToPeerEvaluationConverter(StudentDtoToStudentConverter studentDtoToStudentConverter,
      EvaluationDtoToEvaluationConverter evaluationConverter) {
    this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    this.evaluationConverter = evaluationConverter;
  }

  @Override
  public PeerEvaluation convert(PeerEvaluationDto source) {
    PeerEvaluation peerEval = new PeerEvaluation();
    peerEval.setId(source.id());
    peerEval.setEvaluator(this.studentDtoToStudentConverter.convert(source.evaluator()));
    peerEval.setEvaluations(evaluationConverter(source.evaluations()));
    peerEval.setWeek(source.week());
    return peerEval;
  }

  private List<Evaluation> evaluationConverter(List<EvaluationDto> dtos) {
    return dtos.stream()
        .map(this.evaluationConverter::convert)
        .collect(Collectors.toList());
  }
}
