package edu.tcu.cs.peerevaluation.rubric.criterion.converter;


import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class CriterionToCriterionDtoConverter implements Converter<Criterion, CriterionDto>  {

  @Override
  public CriterionDto convert(Criterion source) {
    CriterionDto criterionDto = new CriterionDto(source.getId(), 
                                                 source.getDescription(), 
                                                 source.getCriterionName(), 
                                                 source.getMaxScore()
                                                 /*source.getRubricId() */);
    return criterionDto;
  }

}
