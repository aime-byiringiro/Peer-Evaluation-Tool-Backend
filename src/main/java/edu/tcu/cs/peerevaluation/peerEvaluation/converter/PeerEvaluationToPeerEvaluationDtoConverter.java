package edu.tcu.cs.peerevaluation.peerEvaluation.converter;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter.EvaluationToEvalutionDtoConverter;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;

@Component
public class PeerEvaluationToPeerEvaluationDtoConverter implements Converter<PeerEvaluation, PeerEvaluationDto> {

  private final StudentToStudentDtoConverter studentToStudentDtoConverter;

  private final EvaluationToEvalutionDtoConverter evalutionDtoConverter;

  public PeerEvaluationToPeerEvaluationDtoConverter(StudentToStudentDtoConverter studentToStudentDtoConverter, EvaluationToEvalutionDtoConverter evalutionDtoConverter) {
    this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    this.evalutionDtoConverter = evalutionDtoConverter;
  }

  @Override
  public PeerEvaluationDto convert(PeerEvaluation source) {
      PeerEvaluationDto peerEvalDto = new PeerEvaluationDto(source.getId(),
                                                            source.getEvaluator() != null
                                                                    ? this.studentToStudentDtoConverter.convert(source.getEvaluator())
                                                                    : null,
                                                            source.getEvaluations().stream()
                                                                .map(this.evalutionDtoConverter::convert)
                                                                .collect(Collectors.toList()),
                                                            source.getWeek());
      return peerEvalDto;
  }
}
