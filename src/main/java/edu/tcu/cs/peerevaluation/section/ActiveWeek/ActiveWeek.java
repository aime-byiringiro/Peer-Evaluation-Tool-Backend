package edu.tcu.cs.peerevaluation.section.ActiveWeek;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.section.Section;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ActiveWeek {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ActiveWeekName;


   @ManyToOne
    private Section section;


    @OneToMany(mappedBy ="activeWeekId", fetch = FetchType.EAGER)
    private List<Week> weeksList;




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

   public List<Week> getWeeksList() {
       return weeksList;
    }

   public void setWeeksList(List<Week> weeksList) {
       this.weeksList = weeksList;
   }
}
