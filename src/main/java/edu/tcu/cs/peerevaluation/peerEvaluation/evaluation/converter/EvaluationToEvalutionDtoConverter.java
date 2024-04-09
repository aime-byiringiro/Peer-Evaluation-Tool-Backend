package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;

@Component
public class EvaluationToEvalutionDtoConverter implements Converter<Evaluation, EvaluationDto> {

  private final StudentToStudentDtoConverter studentToStudentDtoConverter;


  public EvaluationToEvalutionDtoConverter(StudentToStudentDtoConverter studentToStudentDtoConverter) {
    this.studentToStudentDtoConverter = studentToStudentDtoConverter;
  }

  @Override
  public EvaluationDto convert(Evaluation source) {
    EvaluationDto evalDto = new EvaluationDto(source.getId(),
                                              source.getEvaluated() != null
                                                      ? this.studentToStudentDtoConverter.convert(source.getEvaluated())
                                                      : null,
                                              source.getPeerEvalId(),
                                              source.getScores(),
                                              source.getPrivateComments(),
                                              source.getPublicComments());
    return evalDto;                                        
  }
}
