package edu.tcu.cs.peerevaluation.rubric;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

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
  public Result getMethodName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Rubric rubric = principal.getPeerEvalUser().getStudent().getTeam().getSection().getRubric();
    RubricDto rubricDto = this.rubricToRubricDtoConverter.convert(rubric);
    return new Result(true, StatusCode.SUCCESS, "rubric success",rubricDto);
  }

}
