package edu.tcu.cs.peerevaluation.peerEvaluation.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentDtoToStudentConverter;

import org.springframework.core.convert.converter.Converter;

@Component
public class PeerEvaluationDtoToPeerEvaluationConverter implements Converter<PeerEvaluationDto, PeerEvaluation> {

  private final StudentDtoToStudentConverter studentDtoToStudentConverter;

  public PeerEvaluationDtoToPeerEvaluationConverter(StudentDtoToStudentConverter studentDtoToStudentConverter) {
    this.studentDtoToStudentConverter = studentDtoToStudentConverter;
  }

  @Override
  public PeerEvaluation convert(PeerEvaluationDto source){
    PeerEvaluation peerEval = new PeerEvaluation();
    peerEval.setId(source.id());
    peerEval.setEvaluator(this.studentDtoToStudentConverter.convert(source.evaluator()));
    peerEval.setEvaluations(source.evaluations());
    peerEval.setWeek(source.week());
    return peerEval;
  }
}
