package edu.tcu.cs.peerevaluation.war.submission;

import java.io.Serializable;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.war.WAR;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Submission implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Student teamMember;

  private String taskCategory;

  private String plannedTask;

  private String description;

  private Double plannedHours;

  private Double actualHours;

  private Boolean status;

  @ManyToOne
  private WAR war;

  public Submission() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Student getTeamMember() {
    return this.teamMember;
  }

  public void setTeamMember(Student teamMember) {
    this.teamMember = teamMember;
  }

  public String getTaskCategory() {
    return this.taskCategory;
  }

  public void setTaskCategory(String taskCategories) {
    this.taskCategory = taskCategories;
  }

  public String getPlannedTask() {
    return this.plannedTask;
  }

  public void setPlannedTask(String plannedTask) {
    this.plannedTask = plannedTask;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPlannedHours() {
    return this.plannedHours;
  }

  public void setPlannedHours(Double plannedHours) {
    this.plannedHours = plannedHours;
  }

  public Double getActualHours() {
    return this.actualHours;
  }

  public void setActualHours(Double actualHours) {
    this.actualHours = actualHours;
  }

  public Boolean isStatus() {
    return this.status;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

}
