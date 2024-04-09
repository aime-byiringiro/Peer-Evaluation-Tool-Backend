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
  private Long id;

  @ManyToOne
  private Student evaluated;

  private List<Integer> scores;
  /*TODO the only possible issue with this way is the 
    scores being out of order as there is no check, at 
    least as a list. it may be better to use a map or 
    something to pair the score to criterion. for example
    the criterion object could have an id, and since there
    would never be that many, just do: 1,2,3..., and then use
    that id to map to the score value. however if there isn't 
    really a way for the scores to get out of order than this 
    isn't an issue 
  */
  private String privateComments;

  private String publicComments;


  public String getId(){
    return this.id.toString();
  }

  public void setId(String id){
    //this.id = id;
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




}
