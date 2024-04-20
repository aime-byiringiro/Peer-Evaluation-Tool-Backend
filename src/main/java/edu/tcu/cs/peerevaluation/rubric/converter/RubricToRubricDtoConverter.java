package edu.tcu.cs.peerevaluation.rubric.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.converter.CriterionToCriterionDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class RubricToRubricDtoConverter implements Converter<Rubric, RubricDto> {

  private final CriterionToCriterionDtoConverter criterionToCriterionDtoConverter;

  public RubricToRubricDtoConverter(CriterionToCriterionDtoConverter criterionToCriterionDtoConverter) {
    this.criterionToCriterionDtoConverter = criterionToCriterionDtoConverter;
  }

  @Override
  public RubricDto convert(Rubric source) {
    RubricDto rubricDto = new RubricDto(source.getId(),
                                        source.getRubricName(),
                                        criterionConverter(source.getCriterionList()));
    return rubricDto;
  }

  private List<CriterionDto> criterionConverter(List<Criterion> dtos) {
    return dtos.stream() 
               .map(this.criterionToCriterionDtoConverter::convert)
               .collect(Collectors.toList());
}

}
