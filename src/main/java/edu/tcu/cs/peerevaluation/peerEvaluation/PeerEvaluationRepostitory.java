package edu.tcu.cs.peerevaluation.peerEvaluation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.tcu.cs.peerevaluation.student.Student;

@Repository
public interface PeerEvaluationRepostitory extends JpaRepository<PeerEvaluation, Integer> {

  List<PeerEvaluation> findByEvaluator(Student student);
  

}
