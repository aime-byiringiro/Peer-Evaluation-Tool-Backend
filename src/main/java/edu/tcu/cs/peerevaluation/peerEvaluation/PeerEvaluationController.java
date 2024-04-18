package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

  private final UserRepository userRepository;

  public PeerEvaluationController(PeerEvaluationService peerEvalService, PeerEvaluationToPeerEvaluationDtoConverter peerEvalToDtoConverter, PeerEvaluationDtoToPeerEvaluationConverter dtoToPeerEvalConverter, EvaluationToEvalutionDtoConverter evalutionDtoConverter, RubricToRubricDtoConverter rubricToRubricDtoConverter, UserRepository userRepository) {
    this.peerEvalService = peerEvalService;
    this.peerEvalToDtoConverter = peerEvalToDtoConverter;
    this.dtoToPeerEvalConverter = dtoToPeerEvalConverter;
    this.evalutionDtoConverter = evalutionDtoConverter;
    this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
    this.userRepository = userRepository;
  }

  /*
   * this method creates new peer evaluation objects
   */
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

  /*
   * return a peerEvaluaiton based on its ID
   */
  @GetMapping("/{peerEvalId}")
  public Result getPeerEvalById(@PathVariable Integer peerEvalId) {
    PeerEvaluation peerEval = this.peerEvalService.findById(peerEvalId);
    PeerEvaluationDto peerEvalDto = this.peerEvalToDtoConverter.convert(peerEval);
    return new Result(true,StatusCode.SUCCESS,"find success", peerEvalDto);
  }

  /*
   * currently not sure what this is for, but it returns the first 
   * name of the logged in student
   */

  @GetMapping("/peerEvalReportStudent")
  public Result generatePeerEvalReportStudent() {
    Student loggedInStudent = getLoggedInStudent();
    return new Result(true,StatusCode.SUCCESS,"generate success",loggedInStudent.getFirstName());
  }

  /*
   * this method returns a list of all the evaluations of
   * a the current logged in student, based on a specific week
   */
  @GetMapping("/byWeek")
  public Result getEvalsOfByWeek() {
    Student loggedInStudent = getLoggedInStudent();
    Rubric rubric = loggedInStudent.getTeam().getSection().getRubric();
    List<Evaluation> evals = this.peerEvalService.findByEvaluatedAndWeek("week 4",loggedInStudent);

    List<EvaluationDto> evalDtos = evals.stream()
        .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
        .collect(Collectors.toList());
    Report newReport = new Report(evalDtos,this.rubricToRubricDtoConverter.convert(rubric));

    return new Result(true,StatusCode.SUCCESS,"generate success",newReport);
  }

  /*
   * this method return a list of all evaluations of
   * the current logged in user
   */
  @GetMapping("/getEvals")
  public Result getEvalsByEvaluated(){
    Student loggedInStudent = getLoggedInStudent();
    Rubric rubric = loggedInStudent.getTeam().getSection().getRubric();
    List<Evaluation> evals = this.peerEvalService.getEvaluationsById(loggedInStudent);
    List<EvaluationDto> evalDtos = evals.stream()
         .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
         .collect(Collectors.toList());
    Report newReport = new Report(evalDtos,this.rubricToRubricDtoConverter.convert(rubric));
    return new Result(true,StatusCode.SUCCESS,"generate success",newReport);
  }

  /*
   * Method that retrives the current logged in student 
   * object regardless of the authentication method
   * used.
   */
  private Student getLoggedInStudent() throws UsernameNotFoundException{
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
      return principal.getPeerEvalUser().getStudent();
    } else {
      JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
      Jwt jwt = (Jwt) authenticationToken.getCredentials();
      PeerEvalUser user =this.userRepository.findByUsername(jwt.getSubject())
            .orElseThrow(() -> new UsernameNotFoundException("username " + jwt.getSubject() + " is not found."));
      return user.getStudent();
    } 
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
