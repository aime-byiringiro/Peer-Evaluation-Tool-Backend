package edu.tcu.cs.peerevaluation.rubric;

import java.util.List;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import jakarta.persistence.Id;

public class Rubric {

    @Id
    private String rubricName;
    
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

}
