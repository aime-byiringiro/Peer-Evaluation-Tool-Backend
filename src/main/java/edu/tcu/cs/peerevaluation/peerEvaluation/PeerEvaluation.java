package edu.tcu.cs.peerevaluation.peerEvaluation;

import java.io.Serializable;
import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class PeerEvaluation implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Student evaluator; 

  @OneToMany(mappedBy = "peerEvalId")
  private List<Evaluation> evaluations;

  private String week;

  public PeerEvaluation() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Student getEvaluator() {
    return this.evaluator;
  }

  public void setEvaluator(Student evaluator) {
    this.evaluator = evaluator;
  }

  public List<Evaluation> getEvaluations() {
    return this.evaluations;
  }

  public void setEvaluations(List<Evaluation> evaluations) {
    this.evaluations = evaluations;
  }

  public String getWeek() {
    return this.week;
  }

  public void setWeek(String week) {
    this.week = week;
  }



}
