package edu.tcu.cs.peerevaluation.war.submission.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.war.submission.Submission;
import edu.tcu.cs.peerevaluation.war.submission.dto.SubmissionDto;

@Component
public class SubmissionDtoToSubmissionConverter implements Converter<SubmissionDto, Submission> {

  @Override
  public Submission convert(SubmissionDto source) {
    Submission submission = new Submission();
      submission.setId(source.id());
      submission.setTaskCategory(source.taskCategory());
      submission.setPlannedTask(source.plannedTask());
      submission.setDescription(source.description());
      submission.setPlannedHours(source.plannedHours());
      submission.setActualHours(source.actualHours());
      submission.setStatus(source.status());
    return submission;
  }

}
