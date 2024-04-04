package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Team implements Serializable {

    @Id
    private String id;

    @OneToOne
     private Section section;

    @OneToMany
    private List<Student> students;

    public Team() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
