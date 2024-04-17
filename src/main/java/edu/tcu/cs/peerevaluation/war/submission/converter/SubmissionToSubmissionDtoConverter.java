package edu.tcu.cs.peerevaluation.war.submission.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.war.submission.Submission;
import edu.tcu.cs.peerevaluation.war.submission.dto.SubmissionDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class SubmissionToSubmissionDtoConverter implements Converter<Submission, SubmissionDto> {

  @Override
  public SubmissionDto convert(Submission source) {
    SubmissionDto submissionDto = new SubmissionDto(
          source.getId(), 
          source.getTeamMember().getFirstAndLastName(), 
          source.getTaskCategory(), 
          source.getPlannedTask(), 
          source.getDescription(), 
          source.getPlannedHours(), 
          source.getActualHours(), 
          source.getStatus());
    return submissionDto;
  }

}
