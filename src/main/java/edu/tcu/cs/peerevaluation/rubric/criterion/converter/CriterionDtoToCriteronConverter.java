package edu.tcu.cs.peerevaluation.rubric.criterion.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class CriterionDtoToCriteronConverter implements Converter<CriterionDto,Criterion> {

  @Override
  public Criterion convert(CriterionDto source) {
    Criterion criterion = new Criterion();
    criterion.setCriterionName(source.criterionName());
    criterion.setDescription(source.description());
    criterion.setId(source.id());
    criterion.setMaxScore(source.maxScore());
    return criterion;
  }

}
