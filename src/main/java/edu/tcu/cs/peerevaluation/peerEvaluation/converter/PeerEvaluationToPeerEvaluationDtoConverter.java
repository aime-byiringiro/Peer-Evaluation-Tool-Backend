package edu.tcu.cs.peerevaluation.peerEvaluation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;

@Component
public class PeerEvaluationToPeerEvaluationDtoConverter implements Converter<PeerEvaluation, PeerEvaluationDto> {

  private final StudentToStudentDtoConverter studentToStudentDtoConverter;


  public PeerEvaluationToPeerEvaluationDtoConverter(StudentToStudentDtoConverter studentToStudentDtoConverter) {
    this.studentToStudentDtoConverter = studentToStudentDtoConverter;
  }

  @Override
  public PeerEvaluationDto convert(PeerEvaluation source) {
      PeerEvaluationDto peerEvalDto = new PeerEvaluationDto(source.getId(),
                                                            source.getEvaluator() != null
                                                                    ? this.studentToStudentDtoConverter.convert(source.getEvaluator())
                                                                    : null,
                                                            source.getEvaluations(),
                                                            source.getWeek());
      return peerEvalDto;
  }
}
