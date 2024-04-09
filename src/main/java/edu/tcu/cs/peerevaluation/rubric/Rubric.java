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
    
    /* TODO
     * so in this case we have OneToMany, but he put ManyToMany,
     * im not sure why, but to map them together and remove the extra
     * table, assuming we leave it OneToMany, then we need to add an
     * attribute to Criterion that will correspond to the rubric it is
     * a part of, we could just use the rubric id
     */
    @OneToMany(mappedBy = "rubricId")
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
