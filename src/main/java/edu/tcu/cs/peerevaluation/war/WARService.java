package edu.tcu.cs.peerevaluation.war;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.war.submission.Submission;
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

  public WAR saveWar(WAR war) {
    war.getSubmissions().forEach((submission) -> {
      submission.setWar(war);
    });
    return this.warRepository.save(war);
  }

  public List<Submission> findByWeekAndStudent(Integer studentId, String week) {
    return this.submissionRepository.findByWeekAndStudent(studentId, week);
  }

  public List<WAR> findWARsByWeekAndTeamName(String teamName, String week) {
    return warRepository.findByWeekAndTeamName(teamName, week);
  }

  public Submission saveSubmission(Submission submission) {
    return this.submissionRepository.save(submission);
  }

  public WAR findByWeekAndTeam(Integer teamId, String week) {
    return this.warRepository.findByWeekAndTeam(teamId, week);
  }

  public Submission update(Integer id, Submission update) {
    return this.submissionRepository.findById(id)
            .map(oldSubmission -> {
              oldSubmission.setTaskCategory(update.getTaskCategory());
              oldSubmission.setPlannedTask(update.getPlannedTask());
              oldSubmission.setDescription(update.getDescription());
              oldSubmission.setPlannedHours(update.getPlannedHours());
              oldSubmission.setActualHours(update.getActualHours());
              oldSubmission.setStatus(update.getStatus());
              return this.submissionRepository.save(oldSubmission);
            })
            .orElseThrow(() -> new ObjectNotFoundException("submission", id));
  }

  public void deleteSubmission(Integer submissionId) {
    Submission submissionToBeDeleted = this.submissionRepository.findById(submissionId)
            .orElseThrow(() -> new ObjectNotFoundException("submission", submissionId));
    submissionToBeDeleted.removeFromWAR();
    this.submissionRepository.deleteById(submissionId);
  }

  public Submission findById(Integer submissionId) {
    return this.submissionRepository.findById(submissionId)
            .orElseThrow(() -> new ObjectNotFoundException("submission", submissionId));
  }

  public List<Submission> findAll() {
    return this.submissionRepository.findAll();
  }

  public void delete(WAR war) {
    this.warRepository.delete(war);
  }
}