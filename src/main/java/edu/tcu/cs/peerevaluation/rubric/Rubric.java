package edu.tcu.cs.peerevaluation.rubric;

import java.util.List;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rubric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rubricName;
    
    @OneToMany
    private List<Criterion> criterionList;

    public List<Criterion> getCriterionList() {
        return this.criterionList;
    }

    public void setCriterionList(List<Criterion> criterionList) {
        this.criterionList = criterionList;
    }

    public String getRubricName() {
        return this.rubricName;
    }

    public void setRubricName(String rubricName) {
        this.rubricName = rubricName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
