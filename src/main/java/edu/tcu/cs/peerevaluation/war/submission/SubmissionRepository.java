package edu.tcu.cs.peerevaluation.war.submission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer>{


  @Query("SELECT s FROM Submission s WHERE s.teamMember.id = :studentId AND s.war.week = :week")
  List<Submission> findByWeekAndStudent(Integer studentId, String week);
  
}
