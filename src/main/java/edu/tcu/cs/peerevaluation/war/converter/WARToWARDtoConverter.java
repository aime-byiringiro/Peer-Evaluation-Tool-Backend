package edu.tcu.cs.peerevaluation.war.converter;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.war.WAR;
import edu.tcu.cs.peerevaluation.war.dto.WARDto;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionToSubmissionDtoConverter;

@Component
public class WARToWARDtoConverter implements Converter<WAR,WARDto> {

  private final SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter;

  public WARToWARDtoConverter(SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter) {
    this.submissionToSubmissionDtoConverter = submissionToSubmissionDtoConverter;
  }

  @Override
  public WARDto convert(WAR source) {
    WARDto warDto = new WARDto(
      source.getId(),
      source.getTeam().getId(),
      source.getWeek(),
      source.getSubmissions().stream()
        .map(this.submissionToSubmissionDtoConverter::convert)
        .collect(Collectors.toList())
    );

    return warDto;
  }

}
