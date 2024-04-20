package edu.tcu.cs.peerevaluation.war.converter;


import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.war.WAR;
import edu.tcu.cs.peerevaluation.war.dto.WARDto;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionDtoToSubmissionConverter;

@Component
public class WARDtoToWARConverter implements Converter<WARDto, WAR> {

  private final SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter;

  public WARDtoToWARConverter(SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter) {
    this.submissionDtoToSubmissionConverter = submissionDtoToSubmissionConverter;
  }

  @Override
  public WAR convert(WARDto source) {
    WAR war = new WAR();
      war.setWeek(source.week());
      war.setSubmissions(source.submissions().stream()
                              .map(this.submissionDtoToSubmissionConverter::convert)
                              .collect(Collectors.toList()));
    return war;
    
  }

}
