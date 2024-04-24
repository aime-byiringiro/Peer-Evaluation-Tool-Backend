package edu.tcu.cs.peerevaluation.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.war.WAR;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties("team")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String teamName;

    private String academicYear;

    @ManyToOne
    private Section section;

    @OneToMany
    private List<Student> students;

    @ManyToOne
    private Instructor instructor;

    // JSON ignore this property for now
    @OneToMany(mappedBy = "team")
    private List<WAR> wars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    public Team() {
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
            students = new ArrayList<>();
        }
        this.students.add(student);
    }

    public void removeStudentFromTeam(Student student) {
        if (students != null) {
            this.students.remove(student);
        }
    }

    public void removeAllStudentsFromTeam() {
        if (this.students != null) {
            for (Student student : students) {
                student.setTeam(null);
            }
            this.students.clear();
        }
    }

    public void removeSectionFromTeam() {
//        this.section.removeTeam()
        this.section = null;
    }

    public void removeInstructorFromTeam() {
//        this.instructor.removeTeam()
        this.instructor = null;
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
