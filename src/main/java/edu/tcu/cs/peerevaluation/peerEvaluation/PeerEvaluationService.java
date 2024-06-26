package edu.tcu.cs.peerevaluation.peerEvaluation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.EvaluationRepository;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeerEvaluationService {

  private final PeerEvaluationRepostitory peerEvalRepository;

  private final EvaluationRepository evalRepository;

  private final StudentRepository studentRepository;

  public PeerEvaluationService(PeerEvaluationRepostitory peerEvalRepository, EvaluationRepository evalRepository, StudentRepository studentRepository) {
    this.peerEvalRepository = peerEvalRepository;
    this.evalRepository = evalRepository;
    this.studentRepository = studentRepository;
  }

  public List<PeerEvaluation> findAll() {
    return this.peerEvalRepository.findAll();
  }

  public PeerEvaluation save(PeerEvaluation newPeerEval) {
    return this.peerEvalRepository.save(newPeerEval);
  }

  public PeerEvaluation findById(Integer peerEvalId) {
    return this.peerEvalRepository.findById(peerEvalId)
    .orElseThrow(() -> new ObjectNotFoundException("peer evaluation", peerEvalId));
  }

  public List<Evaluation> findByEvaluatedAndWeek(String week, Student evaluated) {
    return this.evalRepository.findByWeekAndEvaluated(evaluated.getId(), week);
  }

  public List<Evaluation> getEvaluationsById(Student student) {
    return this.evalRepository.findByEvaluated(student);
  }

  public List<PeerEvaluation> findAllByStudentId(Integer studentId) {
    Student evaluator = this.studentRepository.findById(studentId)
        .orElseThrow(() -> new ObjectNotFoundException("student", studentId));
    return this.peerEvalRepository.findByEvaluator(evaluator);
    
  } 

  public List<Evaluation> findByWeekAndSection(String week, String sectionName) {
    if (week == null || sectionName == null || sectionName.isEmpty()) {
        throw new IllegalArgumentException("Week and section name must not be null or empty.");
    }
    return evalRepository.findByWeekAndSection(week, sectionName);
}

public List<Evaluation> findByWeekAndStudentId(String week, Integer studentId) {
  if (week == null || studentId == null) {
      throw new IllegalArgumentException("Week and student ID must not be null.");
  }
  return this.evalRepository.findByWeekAndEvaluated(studentId, week);
}


}
