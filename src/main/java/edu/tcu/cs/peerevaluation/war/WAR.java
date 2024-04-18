package edu.tcu.cs.peerevaluation.war;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.war.submission.Submission;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class WAR implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "Team")
  private Team team;

  private Integer week;

  @OneToMany(mappedBy = "war", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Submission> submissions;

  public WAR() {

  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Team getTeam() {
    return this.team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Integer getWeek() {
    return this.week;
  }

  public void setWeek(Integer week) {
    this.week = week;
  }

  public List<Submission> getSubmissions() {
    return this.submissions;
  }

  public void setSubmissions(List<Submission> submissions) {
    this.submissions = submissions;
  }

  public void addSubmission(Submission submission) {
    if (submissions == null) {
      submissions = new ArrayList<Submission>();
      } 
      submissions.add(submission);
  }

  public void removeSubmission(Submission submission) {
    if (submissions != null) {
      submissions.remove(submission);
  }
  }

}