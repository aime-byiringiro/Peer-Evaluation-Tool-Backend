package edu.tcu.cs.peerevaluation.rubric.criterion;

import java.io.Serializable;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
public class Criterion implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "description is required")
    private String description;

    @NotEmpty(message = "name is required")
    private String criterionName;

    @Positive(message = "max score is must positive")
    private Integer maxScore;

    @ManyToOne
    private Rubric rubricId;

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

    public Integer getMaxScore() {
        return this.maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rubric getRubricId() {
        return this.rubricId;
    }

    public void setRubricId(Rubric rubricId) {
        this.rubricId = rubricId;
    }

}
