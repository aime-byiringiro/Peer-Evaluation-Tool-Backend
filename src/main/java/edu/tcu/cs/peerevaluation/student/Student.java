package edu.tcu.cs.peerevaluation.student;

import java.io.Serializable;

import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Student implements Serializable {

  @Id
  private String id;

  private String firstName;

  private String middleInitial;

  private String lastName;

  private String email;

  private String password;

  @ManyToOne
  private Team team; 


  public Student() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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
}
