package edu.tcu.cs.peerevaluation.rubric.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.converter.CriterionDtoToCriterionConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class RubricDtoToRubricConverter implements Converter<RubricDto, Rubric> {

  private final CriterionDtoToCriterionConverter criterionDtoToCriterionConverter;

  public RubricDtoToRubricConverter(CriterionDtoToCriterionConverter criterionDtoToCriterionConverter) {
    this.criterionDtoToCriterionConverter = criterionDtoToCriterionConverter;
  }

  @Override
  public Rubric convert(RubricDto source) {
    Rubric rubric = new Rubric();
    rubric.setId(source.id());
    rubric.setCriterionList(criterionConverter(source.criterion()));
    rubric.setRubricName(source.rubricName());
    return rubric;
  }

  private List<Criterion> criterionConverter(List<CriterionDto> dtos) {
      return dtos.stream()
                 .map(this.criterionDtoToCriterionConverter::convert)
                 .collect(Collectors.toList());
  }
  

}
