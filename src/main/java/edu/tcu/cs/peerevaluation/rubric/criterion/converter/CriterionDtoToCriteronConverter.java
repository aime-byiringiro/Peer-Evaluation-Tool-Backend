package edu.tcu.cs.peerevaluation.rubric.criterion.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;



@Component
public class CriterionDtoToCriteronConverter implements Converter<CriterionDto,Criterion> {

  @Override
  public Criterion convert(CriterionDto source) {
    Criterion criterion = new Criterion();
    criterion.setCriterionName(source.criterionName());
    criterion.setDescription(source.description());
    criterion.setId(source.id());
    criterion.setMaxScore(source.maxScore());
    criterion.setRubricId(source.rubricId());
    return criterion;
  }

}
