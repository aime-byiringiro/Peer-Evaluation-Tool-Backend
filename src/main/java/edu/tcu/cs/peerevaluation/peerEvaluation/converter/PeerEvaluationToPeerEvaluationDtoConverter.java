package edu.tcu.cs.peerevaluation.peerEvaluation.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter.EvaluationToEvalutionDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
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
                                                            evaluationConverter(source.getEvaluations()),
                                                            source.getWeek());
      return peerEvalDto;
  }

  private List<EvaluationDto> evaluationConverter(List<Evaluation> dtos) {
    return dtos.stream()
        .map(this.evalutionDtoConverter::convert)
        .collect(Collectors.toList());
  }


}
