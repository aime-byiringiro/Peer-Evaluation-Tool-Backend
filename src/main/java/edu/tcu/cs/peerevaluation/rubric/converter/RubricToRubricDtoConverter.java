package edu.tcu.cs.peerevaluation.rubric.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class RubricToRubricDtoConverter implements Converter<Rubric, RubricDto> {

  @Override
  public RubricDto convert(Rubric source) {
    RubricDto rubricDto = new RubricDto(source.getRubricName(),
                                        source.getCriterionList());
    return rubricDto;
  }

}
