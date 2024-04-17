package edu.tcu.cs.peerevaluation.war.dto;

import java.util.List;

import edu.tcu.cs.peerevaluation.war.submission.dto.SubmissionDto;

public record WARDto(Integer id,
                     Integer teamId,
                     Integer week,
                     List<SubmissionDto> submissions) {
}
