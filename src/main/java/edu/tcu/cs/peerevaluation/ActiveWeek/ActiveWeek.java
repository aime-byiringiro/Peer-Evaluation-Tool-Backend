package edu.tcu.cs.peerevaluation.ActiveWeek;

import ch.qos.logback.core.model.INamedModel;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.section.Section;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ActiveWeek {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ActiveWeekName;


    @OneToOne
    private Section sectionName;


    @OneToMany(mappedBy ="activeWeekId", fetch = FetchType.EAGER)
    private List<Week> weeksList;

    public Section getSectionName() {
        return sectionName;
    }

    public void setSectionName(Section sectionName) {
        this.sectionName = sectionName;
    }


    public String getActiveWeekName() {
        return ActiveWeekName;
    }

    public void setActiveWeekName(String activeWeekName) {
        ActiveWeekName = activeWeekName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Week> getWeeksList() {
        return weeksList;
    }

    public void setWeeksList(List<Week> weeksList) {
        this.weeksList = weeksList;
    }
}
