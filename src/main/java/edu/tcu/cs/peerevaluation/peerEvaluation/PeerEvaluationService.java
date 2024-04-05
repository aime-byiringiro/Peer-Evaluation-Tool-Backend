package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeerEvaluationService {

  private final PeerEvaluationRepostitory peerEvalRepository;

  public PeerEvaluationService(PeerEvaluationRepostitory peerEvalRepository) {
    this.peerEvalRepository = peerEvalRepository;
  }


}
