package edu.tcu.cs.peerevaluation.peerEvaluation.evaluation;

import java.io.Serializable;
import java.util.List;

import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Evaluation implements Serializable{

  @Id
  Student evaluated;

  List<Integer> scores;
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
  String privateComments;

  String publicComments;

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
