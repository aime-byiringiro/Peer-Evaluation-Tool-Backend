package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Section_ID", unique = true)
    private Integer id;

    private String sectionName;

    private String academicYear;

    //08/21/2023
    private LocalDate firstDay;

    //05/01/2024
    private LocalDate lastDay;

    @ManyToOne
    private Rubric rubric;

    @JsonIgnoreProperties("section")
    @OneToMany(mappedBy = "section", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Team> teams;



    public Section() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getFirstDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return this.firstDay.format(formatter);
    }

    public void setFirstDay(String firstDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.firstDay = LocalDate.parse(firstDay, formatter);
    }

    public String getLastDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return this.lastDay.format(formatter);
    }

    public void setLastDay(String lastDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.lastDay = LocalDate.parse(lastDay, formatter);
    }

    public Rubric getRubric() {
        return this.rubric;
    }

    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }

    public String getCurrentWeek() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate pastMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return pastMonday.format(formatter);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }
}
