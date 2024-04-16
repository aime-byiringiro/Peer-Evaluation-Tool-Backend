package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.stereotype.Service;

import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeerEvaluationService {

  private final PeerEvaluationRepostitory peerEvalRepository;

  public PeerEvaluationService(PeerEvaluationRepostitory peerEvalRepository) {
    this.peerEvalRepository = peerEvalRepository;
  }

  public PeerEvaluation save(PeerEvaluation newPeerEval) {
    return this.peerEvalRepository.save(newPeerEval);
  }

  public PeerEvaluation findById(Integer peerEvalId) {
    return this.peerEvalRepository.findById(peerEvalId)
    .orElseThrow(() -> new ObjectNotFoundException("peer evaluation", peerEvalId));
  }


}
