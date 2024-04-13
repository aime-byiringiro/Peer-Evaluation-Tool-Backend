package edu.tcu.cs.peerevaluation.rubric.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class RubricDtoToRubricConverter implements Converter<RubricDto, Rubric> {

  @Override
  public Rubric convert(RubricDto source) {
    Rubric rubric = new Rubric();
    rubric.setId(source.id());
    rubric.setCriterionList(source.criterion());
    rubric.setRubricName(source.rubricName());
    return rubric;
  }

}
