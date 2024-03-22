package edu.tcu.cs.peerevaluation.student;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student implements Serializable {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String email;

  //@OnetoMany
  //private designTeam team; 

  //@OnetoMany
  //private designSection section; 

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




  

}
