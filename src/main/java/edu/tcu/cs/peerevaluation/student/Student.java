package edu.tcu.cs.peerevaluation.student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Student implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty(message = "first name is required.")
  private String firstName;

  private String middleInitial;

  @NotEmpty(message = "last name is required.")
  private String lastName;

  private String email;

  @ManyToOne
  private Team team;

  @OneToOne(mappedBy = "student",fetch = FetchType.EAGER)
  private PeerEvalUser user;

  public Student() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMiddleInitial() {
    return this.middleInitial;
  }

  public void setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
  }

  public Team getTeam() {
    return this.team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public void assignTeam(Team team) {
    this.team = team;
    team.addStudentToTeam(this);
  }

  public void unassignTeam() {
    if (this.team != null) {
      team.removeStudentFromTeam(this);
      this.team = null;
    }
  }

  public String getAcademicYear() {
    return this.team.getSection().getAcademicYear();
  }

  public PeerEvalUser getUser() {
    return this.user;
  }

  public void setUser(PeerEvalUser user) {
    this.user = user;
  }

  public String getFirstAndLastName() {
    return (this.firstName + " " + this.lastName);
  }

  public List<Student> getTeammates() {
    return this.team.getStudents();
  }

}
