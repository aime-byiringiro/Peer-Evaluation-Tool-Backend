package edu.tcu.cs.peerevaluation.section.ActiveWeek;

import edu.tcu.cs.peerevaluation.section.Section;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ActiveWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ActiveWeekName;

    @ElementCollection
    private List<Date> activeWeekList;

   @ManyToOne
    private Section section;





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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Date> getActiveWeekList() {
        return activeWeekList;
    }

    public void setActiveWeekList(List<Date> activeWeekList) {
        this.activeWeekList = activeWeekList;
    }
}
