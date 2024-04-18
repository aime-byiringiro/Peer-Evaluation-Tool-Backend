package edu.tcu.cs.peerevaluation.peerEvaluation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.EvaluationRepository;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeerEvaluationService {

  private final PeerEvaluationRepostitory peerEvalRepository;

  private final EvaluationRepository evalRepository;

  public PeerEvaluationService(PeerEvaluationRepostitory peerEvalRepository, EvaluationRepository evalRepository) {
    this.peerEvalRepository = peerEvalRepository;
    this.evalRepository = evalRepository;
  }

  public PeerEvaluation save(PeerEvaluation newPeerEval) {
    return this.peerEvalRepository.save(newPeerEval);
  }

  public PeerEvaluation findById(Integer peerEvalId) {
    return this.peerEvalRepository.findById(peerEvalId)
    .orElseThrow(() -> new ObjectNotFoundException("peer evaluation", peerEvalId));
  }

  public List<Evaluation> findByEvaluatedAndWeek(Integer week, Student evaluated) {
    return this.evalRepository.findByWeekAndEvaluated(evaluated.getId(), week);
  }

  public List<Evaluation> getEvaluationsById(Student student) {
    return this.evalRepository.findByEvaluated(student);
  } 


}
