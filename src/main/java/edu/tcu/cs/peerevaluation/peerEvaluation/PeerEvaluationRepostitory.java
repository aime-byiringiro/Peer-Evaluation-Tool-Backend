package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerEvaluationRepostitory extends JpaRepository<PeerEvaluation, String> {

}
