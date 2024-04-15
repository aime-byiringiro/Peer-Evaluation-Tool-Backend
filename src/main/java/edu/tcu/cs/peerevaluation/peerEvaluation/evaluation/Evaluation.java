package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation;

import java.io.Serializable;
import java.util.List;
import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Evaluation implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Student evaluated;

  private Integer peerEvalId;

  private List<Integer> scores;

  private String privateComments;

  private String publicComments;

  public Integer getId(){
    return this.id;
  }

  public void setId(Integer id){
    this.id = id;
  }

  public Student getEvaluated() {
    return this.evaluated;
  }

  public void setEvaluated(Student evaluated) {
    this.evaluated = evaluated;
  }

  public List<Integer> getScores() {
    return this.scores;
  }

  public void setScores(List<Integer> scores) {
    this.scores = scores;
  }

  public String getPrivateComments() {
    return this.privateComments;
  }

  public void setPrivateComments(String privateComment) {
    this.privateComments = privateComment;
  }

  public String getPublicComments() {
    return this.publicComments;
  }

  public void setPublicComments(String publicComments) {
    this.publicComments = publicComments;
  }

  public Integer getPeerEvalId() {
    return this.peerEvalId;
  }

  public void setPeerEvalId(Integer peerEvalId) {
    this.peerEvalId = peerEvalId;
  }

}
