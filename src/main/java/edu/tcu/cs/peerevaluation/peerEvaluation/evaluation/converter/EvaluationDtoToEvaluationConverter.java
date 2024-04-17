package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentDtoToStudentConverter;

@Component
public class EvaluationDtoToEvaluationConverter implements Converter<EvaluationDto, Evaluation> {

  private final StudentDtoToStudentConverter studentDtoToStudentConverter;

  public EvaluationDtoToEvaluationConverter(StudentDtoToStudentConverter studentDtoToStudentConverter) {
    this.studentDtoToStudentConverter = studentDtoToStudentConverter;
  }

  @Override
  public Evaluation convert(EvaluationDto source) {
    Evaluation eval = new Evaluation();
    eval.setId(source.id());
    eval.setEvaluated(this.studentDtoToStudentConverter.convert(source.evaluated()));
    //eval.setPeerEvalId(source.peerEvalId());
    eval.setScores(source.scores());
    eval.setPrivateComments(source.privateComments());
    eval.setPublicComments(source.publicComments());
    return eval;
  }

}
