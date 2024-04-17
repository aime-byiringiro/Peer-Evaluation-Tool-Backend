package edu.tcu.cs.peerevaluation.war;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.war.converter.WARDtoToWARConverter;
import edu.tcu.cs.peerevaluation.war.converter.WARToWARDtoConverter;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionDtoToSubmissionConverter;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionToSubmissionDtoConverter;

@RestController
@RequestMapping("/war")
public class WARController {

  private final WARService warService;

  private final WARDtoToWARConverter warDtoToWARConverter;

  private final WARToWARDtoConverter warToWARDtoConverter;

  private final SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter;

  private final SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter;

  public WARController(WARService warService, WARDtoToWARConverter warDtoToWARConverter, WARToWARDtoConverter warToWARDtoConverter, SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter, SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter) {
    this.warService = warService;
    this.warDtoToWARConverter = warDtoToWARConverter;
    this.warToWARDtoConverter = warToWARDtoConverter;
    this.submissionDtoToSubmissionConverter = submissionDtoToSubmissionConverter;
    this.submissionToSubmissionDtoConverter = submissionToSubmissionDtoConverter;
  }

}
