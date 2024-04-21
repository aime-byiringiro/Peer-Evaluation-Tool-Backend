package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.war.WAR;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Team_ID", unique = true)
    private Integer id;

    private String teamName;

    @ManyToOne
     private Section section;

    @JsonIgnoreProperties("team")
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private List<Student> students;

    @OneToMany(mappedBy = "team")
    private List<WAR> wars;

    @ManyToOne
    private Instructor instructor;

    

    public Team() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void addStudentToTeam(Student student) {
        if (students == null) {
        students = new ArrayList<Student>();
        } 
        students.add(student);
    }

    public void removeStudentFromTeam(Student student) {
        if (students != null) {
            students.remove(student);
        }
    }

    public List<WAR> getWars() {
        return this.wars;
    }

    public void setWars(List<WAR> wars) {
        this.wars = wars;
    }
    
    public void addWAR(WAR war) {
        if (wars == null) {
        wars = new ArrayList<WAR>();
        } 
        wars.add(war);
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    

}
