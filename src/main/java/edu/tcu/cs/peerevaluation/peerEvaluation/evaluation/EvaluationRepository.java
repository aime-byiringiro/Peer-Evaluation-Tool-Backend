package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.tcu.cs.peerevaluation.student.Student;



@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

  List<Evaluation> findByEvaluated(Student student);

  @Query("SELECT e FROM Evaluation e WHERE e.evaluated.id = :studentId AND e.peerEvaluation.week = :week")
  List<Evaluation> findByWeekAndEvaluated(Integer studentId, Integer week);

  // Query to fetch all evaluations for a specific week and section
  @Query("SELECT e FROM Evaluation e WHERE e.evaluated.team.section.sectionName = :sectionName AND e.peerEvaluation.week = :week")
  List<Evaluation> findByWeekAndSection(@Param("week") Integer week, @Param("sectionName") String sectionName);
  
}