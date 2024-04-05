package edu.tcu.cs.peerevaluation.rubric.criterion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Criterion {

    @Id
    private String description;
    private String criterionName;
    private int maxScore;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCriterionName() {
        return this.criterionName;
    }

    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
    
}
