package edu.tcu.cs.peerevaluation.rubric;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/rubric")
public class RubricController {

  private final RubricService rubricService;

  private final RubricToRubricDtoConverter rubricToRubricDtoConverter;

  private final RubricDtoToRubricConverter rubricDtoToRubricConverter;

  public RubricController(RubricService rubricService, RubricToRubricDtoConverter rubricToRubricDtoConverter,
      RubricDtoToRubricConverter rubricDtoToRubricConverter) {
    this.rubricService = rubricService;
    this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
    this.rubricDtoToRubricConverter = rubricDtoToRubricConverter;
  }

  @GetMapping()
  public Result getRubricName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Rubric rubric = principal.getPeerEvalUser().getStudent().getTeam().getSection().getRubric();
    RubricDto rubricDto = this.rubricToRubricDtoConverter.convert(rubric);
    return new Result(true, StatusCode.SUCCESS, "rubric success",rubricDto);
  }

  @PostMapping
  public Result newRubric(@Valid @RequestBody RubricDto rubricDto) {
    Rubric newRubric = this.rubricDtoToRubricConverter.convert(rubricDto);
    Rubric savedRubric = this.rubricService.save(newRubric);
    savedRubric.getCriterionList().forEach(criterion -> {
      criterion.setRubricId(newRubric);
    });
    savedRubric = this.rubricService.save(newRubric);
    RubricDto savedDto = this.rubricToRubricDtoConverter.convert(savedRubric);
    return new Result(true, StatusCode.SUCCESS, "add success", savedDto);
  }

}

// peer eval= rubric
// evalutaion= criterion
