package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationDtoToPeerEvaluationConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationToPeerEvaluationDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.converter.EvaluationToEvalutionDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.dto.EvaluationDto;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/peerEval")
public class PeerEvaluationController {

  private final PeerEvaluationService peerEvalService;

  private final PeerEvaluationToPeerEvaluationDtoConverter peerEvalToDtoConverter;

  private final PeerEvaluationDtoToPeerEvaluationConverter dtoToPeerEvalConverter;

  private final EvaluationToEvalutionDtoConverter evalutionDtoConverter;

  private final RubricToRubricDtoConverter rubricToRubricDtoConverter;

  public PeerEvaluationController(PeerEvaluationService peerEvalService, PeerEvaluationToPeerEvaluationDtoConverter peerEvalToDtoConverter, PeerEvaluationDtoToPeerEvaluationConverter dtoToPeerEvalConverter, EvaluationToEvalutionDtoConverter evalutionDtoConverter, RubricToRubricDtoConverter rubricToRubricDtoConverter) {
    this.peerEvalService = peerEvalService;
    this.peerEvalToDtoConverter = peerEvalToDtoConverter;
    this.dtoToPeerEvalConverter = dtoToPeerEvalConverter;
    this.evalutionDtoConverter = evalutionDtoConverter;
    this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
  }

  @PostMapping
  public Result newPeerEvaluation(@Valid @RequestBody PeerEvaluationDto peerEvalDto) {
    PeerEvaluation newPeerEval = this.dtoToPeerEvalConverter.convert(peerEvalDto);
    PeerEvaluation savedPeerEval = this.peerEvalService.save(newPeerEval);
    savedPeerEval.getEvaluations().forEach(eval -> {
      eval.setPeerEvaluation(newPeerEval);
    });
    savedPeerEval = this.peerEvalService.save(newPeerEval);
    PeerEvaluationDto savedDto = this.peerEvalToDtoConverter.convert(savedPeerEval);
    return new Result(true,StatusCode.SUCCESS,"add success", savedDto);
  }

  @GetMapping("/{peerEvalId}")
  public Result getPeerEvalById(@PathVariable Integer peerEvalId) {
    PeerEvaluation peerEval = this.peerEvalService.findById(peerEvalId);
    PeerEvaluationDto peerEvalDto = this.peerEvalToDtoConverter.convert(peerEval);
    return new Result(true,StatusCode.SUCCESS,"find success", peerEvalDto);
  }

  @GetMapping("/peerEvalReportStudent")
  public Result generatePeerEvalReportStudent() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Student loggedInStudent = principal.getPeerEvalUser().getStudent();


    /*
     * TODO, how does the program know what week it is perhaps,
     * like how the baseUrl is set in the hogwarts program, we
     * could set a currentWeek variable, and then have a endpoint 
     * to change the currentWeek, and here i can just retreive 
     * that variable and subract one
     * 
     */

     /*TODO,
      * would it be valid to create a evalReportDto object, that is used
      * to build to object sent to the frontend 
      */


    return new Result(true,StatusCode.SUCCESS,"generate success",loggedInStudent.getFirstName());
  }

  @GetMapping("/byWeek/{week}")
  public Result getEvalsOfByWeek() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Student loggedInStudent = principal.getPeerEvalUser().getStudent();
    Rubric rubric = loggedInStudent.getTeam().getSection().getRubric();
    List<Evaluation> evals = this.peerEvalService.findByEvaluatedAndWeek("week 4",loggedInStudent);

    List<EvaluationDto> evalDtos = evals.stream()
        .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
        .collect(Collectors.toList());
    Report newReport = new Report(evalDtos,this.rubricToRubricDtoConverter.convert(rubric));

    return new Result(true,StatusCode.SUCCESS,"generate success",newReport);
  }

  @GetMapping("/getEvals")
  public Result getEvalsByEvaluated(){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Student loggedInStudent = principal.getPeerEvalUser().getStudent();
    Rubric rubric = loggedInStudent.getTeam().getSection().getRubric();
    List<Evaluation> evals = this.peerEvalService.getEvaluationsById(loggedInStudent);
    List<EvaluationDto> evalDtos = evals.stream()
         .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
         .collect(Collectors.toList());
    Report newReport = new Report(evalDtos,this.rubricToRubricDtoConverter.convert(rubric));
    return new Result(true,StatusCode.SUCCESS,"generate success",newReport);
  }
  
}

class Report {
  List<EvaluationDto> evals;
  RubricDto rubricDto;

  public Report(List<EvaluationDto> evals, RubricDto rubricDto) {
    this.evals = evals;
    this.rubricDto = rubricDto;
  }
  
  public List<EvaluationDto> getEvals() {
    return this.evals;
  }

  public void setEvals(List<EvaluationDto> evals) {
    this.evals = evals;
  }

  public RubricDto getRubric() {
    return this.rubricDto;
  }

  public void setRubric(RubricDto rubricDto) {
    this.rubricDto = rubricDto;
  }

}
