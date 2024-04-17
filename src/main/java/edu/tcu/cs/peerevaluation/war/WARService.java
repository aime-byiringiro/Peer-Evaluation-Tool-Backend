package edu.tcu.cs.peerevaluation.war;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.war.submission.SubmissionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class WARService {

  private final WARRepository warRepository;

  private final SubmissionRepository submissionRepository;

  public WARService(WARRepository warRepository, SubmissionRepository submissionRepository) {
    this.warRepository = warRepository;
    this.submissionRepository = submissionRepository;
  }

}
