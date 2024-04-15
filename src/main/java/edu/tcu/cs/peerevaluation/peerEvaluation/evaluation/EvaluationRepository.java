package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.tcu.cs.peerevaluation.student.Student;


@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

  List<Evaluation> findByEvaluated(Student student);

}
