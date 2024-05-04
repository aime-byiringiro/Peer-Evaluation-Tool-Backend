package edu.tcu.cs.peerevaluation.section.ActiveWeek.week;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.ActiveWeek;
import jakarta.persistence.*;

@Entity
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String weekName;

    private String startDate;


    @ManyToOne
    private ActiveWeek activeWeekId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public ActiveWeek getActiveWeekId() {
        return activeWeekId;
    }

    public void setActiveWeekId(ActiveWeek activeWeekId) {
        this.activeWeekId = activeWeekId;
    }
}
