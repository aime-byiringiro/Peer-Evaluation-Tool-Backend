package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation;

import java.io.Serializable;
import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
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

  @ManyToOne
  private PeerEvaluation peerEvaluation;

  private List<Integer> scores;

  private Integer totalScore;

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

  public PeerEvaluation getPeerEvaluation() {
    return this.peerEvaluation;
  }

  public void setPeerEvaluation(PeerEvaluation peerEvaluation) {
    this.peerEvaluation = peerEvaluation;
  }

  public Integer getTotalScore() {
    return this.totalScore;
  }

  public void setTotalScore(Integer totalScore) {
    this.totalScore = totalScore;
  }


}
