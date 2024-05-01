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
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
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

  private final StudentRepository studentRepository;

  private final StudentService studentService;

  public PeerEvaluationController(PeerEvaluationService peerEvalService,
      PeerEvaluationToPeerEvaluationDtoConverter peerEvalToDtoConverter,
      PeerEvaluationDtoToPeerEvaluationConverter dtoToPeerEvalConverter,
      EvaluationToEvalutionDtoConverter evalutionDtoConverter, RubricToRubricDtoConverter rubricToRubricDtoConverter,
      UserRepository userRepository, StudentRepository studentRepository, StudentService studentService) {
    this.peerEvalService = peerEvalService;
    this.peerEvalToDtoConverter = peerEvalToDtoConverter;
    this.dtoToPeerEvalConverter = dtoToPeerEvalConverter;
    this.evalutionDtoConverter = evalutionDtoConverter;
    this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.studentService = studentService;
  }

  /*
   * Returns all peer evaluations
   */
  @GetMapping
  public Result getAllPeerEvaluations() {
    List<PeerEvaluation> peerEvals = this.peerEvalService.findAll();
    List<PeerEvaluationDto> peerEvalDtos = peerEvals.stream()
        .map(this.peerEvalToDtoConverter::convert)
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "find all success", peerEvalDtos);
  }

  /*
   * this method creates new peer evaluation objects
   */
  @PostMapping
  public Result newPeerEvaluation(@Valid @RequestBody PeerEvaluationDto peerEvalDto) {
    PeerEvaluation newPeerEval = this.dtoToPeerEvalConverter.convert(peerEvalDto);
    newPeerEval.setEvaluator(getLoggedInStudent());
    PeerEvaluation savedPeerEval = this.peerEvalService.save(newPeerEval);
    savedPeerEval.getEvaluations().forEach(eval -> {
      eval.setPeerEvaluation(newPeerEval);
    });
    savedPeerEval = this.peerEvalService.save(newPeerEval);
    PeerEvaluationDto savedDto = this.peerEvalToDtoConverter.convert(savedPeerEval);
    return new Result(true, StatusCode.SUCCESS, "add success", savedDto);
  }

  /*
   * return a peerEvaluaiton based on its ID
   */
  @GetMapping("/{peerEvalId}")
  public Result getPeerEvalById(@PathVariable Integer peerEvalId) {
    PeerEvaluation peerEval = this.peerEvalService.findById(peerEvalId);
    PeerEvaluationDto peerEvalDto = this.peerEvalToDtoConverter.convert(peerEval);
    return new Result(true, StatusCode.SUCCESS, "find success", peerEvalDto);
  }

  /*
   * Returns the rubric and evals of a the current student
   * based on the week
   */

  @GetMapping("/peerEvalReportStudent/{month}/{day}/{year}")
  public Result generatePeerEvalReportStudent(@PathVariable String month, @PathVariable String day,
      @PathVariable String year) {
    String week = month + "/" + day + "/" + year;
    // Retrieve the currently logged in user
    Student loggedInStudent = getLoggedInStudent();
    // Retrieve the rubric for that student
    RubricDto rubric = this.rubricToRubricDtoConverter.convert(loggedInStudent.getTeam().getSection().getRubric());
    // Get a list of evals from {week} and for loggedInStudent
    List<Evaluation> evals = this.peerEvalService.findByEvaluatedAndWeek(week, loggedInStudent);
    // Convert Evaluations to EvaluationDtos
    List<EvaluationDto> evalDtos = evals.stream()
        .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
        .collect(Collectors.toList());
    // Combine it all into a report object to send to the front end
    Report report = new Report(evalDtos, rubric);
    return new Result(true, StatusCode.SUCCESS, "generate success", report);
  }

  /*
   * Returns the rubric and evals of a student based on student ID
   */
  @GetMapping("/peerEvalReportForStudent/{studentId}/{month}/{day}/{year}")
  public Result generatePeerEvalReportForStudent(@PathVariable Integer studentId, @PathVariable String month,
      @PathVariable String day, @PathVariable String year) {
    String week = month + "/" + day + "/" + year;
    try {
      // Retrieve the student by ID
      Student student = studentService.findById(studentId);
      if (student == null) {
        return new Result(false, StatusCode.NOT_FOUND, "Student not found");
      }

      // Retrieve the rubric for that student's section
      RubricDto rubric = this.rubricToRubricDtoConverter.convert(student.getTeam().getSection().getRubric());

      // Get a list of evaluations for the specified week and student
      List<Evaluation> evals = this.peerEvalService.findByWeekAndStudentId(week, studentId);

      // Convert Evaluations to EvaluationDtos
      List<EvaluationDto> evalDtos = evals.stream()
          .map(this.evalutionDtoConverter::convert)
          .collect(Collectors.toList());

      // Combine it all into a report object to send to the front end
      Report report = new Report(evalDtos, rubric);
      return new Result(true, StatusCode.SUCCESS, "Report generated successfully", report);
    } catch (Exception e) {
      return new Result(false, StatusCode.NOT_FOUND, "Error generating report: " + e.getMessage());
    }
  }

  /*
   * this method return a list of all evaluations of
   * the current logged in user
   */
  @GetMapping("/getEvals")
  public Result getEvalsByEvaluated() {
    Student loggedInStudent = getLoggedInStudent();
    List<Evaluation> evals = this.peerEvalService.getEvaluationsById(loggedInStudent);
    List<EvaluationDto> evalDtos = evals.stream()
        .map(foundEval -> this.evalutionDtoConverter.convert(foundEval))
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "generate success", evalDtos);
  }

  @GetMapping("/getEvals/{studentId}")
  public Result getAllByStudent(@PathVariable Integer studentId) {
    List<PeerEvaluation> peerEvals = this.peerEvalService.findAllByStudentId(studentId);
    List<PeerEvaluationDto> peerEvalDtos = peerEvals.stream()
        .map(found -> this.peerEvalToDtoConverter.convert(found))
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "generate success", peerEvalDtos);
  }

  /*
   * Returns all evaluations for the given week and section
   */
  @GetMapping("/evaluations/{month}/{day}/{year}/{sectionName}")
  public Result getEvaluationsByWeekAndSection(@PathVariable String month, @PathVariable String day,
      @PathVariable String year, @PathVariable String sectionName) {
    String week = month + "/" + day + "/" + year;
    if (week.trim().isEmpty() || sectionName == null || sectionName.trim().isEmpty()) {
      return new Result(false, StatusCode.INVALID_ARGUMENT, "Week and section name parameters are required.", null);
    }

    try {
      List<Evaluation> evals = this.peerEvalService.findByWeekAndSection(week, sectionName);
      if (evals.isEmpty()) {
        return new Result(false, StatusCode.INVALID_ARGUMENT, "No evaluations found for the given week and section.",
            null);
      }

      List<EvaluationDto> evalDtos = evals.stream()
          .map(this.evalutionDtoConverter::convert)
          .collect(Collectors.toList());

      return new Result(true, StatusCode.SUCCESS, "Evaluations retrieved successfully.", evalDtos);
    } catch (NumberFormatException e) {
      return new Result(false, StatusCode.INVALID_ARGUMENT, "Week parameter must be a number.", null);
    }
  }

  @GetMapping("/peerEvalReportByStudent/{studentId}/{month}/{day}/{year}")
  public Result generatePeerEvalReportByStudent(@PathVariable Integer studentId, @PathVariable String month,
      @PathVariable String day, @PathVariable String year) {
    String week = month + "/" + day + "/" + year;
    try {
      // Retrieve the student by ID
      Student student = studentService.findById(studentId);
      if (student == null) {
        return new Result(false, StatusCode.NOT_FOUND, "Student not found");
      }

      // Retrieve the rubric for that student's section
      RubricDto rubric = this.rubricToRubricDtoConverter.convert(student.getTeam().getSection().getRubric());

      // Get a list of evaluations for the specified week and student
      List<Evaluation> evals = this.peerEvalService.findByWeekAndStudentId(week, studentId);

      // Convert Evaluations to EvaluationDtos
      List<EvaluationDto> evalDtos = evals.stream()
          .map(this.evalutionDtoConverter::convert)
          .collect(Collectors.toList());

      // Combine it all into a report object to send to the front end
      Report report = new Report(evalDtos, rubric);
      return new Result(true, StatusCode.SUCCESS, "Report generated successfully", report);
    } catch (Exception e) {
      return new Result(false, StatusCode.NOT_FOUND, "Error generating report: " + e.getMessage());
    }
  }

  /*
   * Method that retrives the current logged in student
   * object regardless of the authentication method
   * used.
   */
  private Student getLoggedInStudent() throws UsernameNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
      return principal.getPeerEvalUser().getStudent();
    } else {
      JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext()
          .getAuthentication();
      Jwt jwt = (Jwt) authenticationToken.getCredentials();
      PeerEvalUser user = this.userRepository.findByUsername(jwt.getSubject())
          .orElseThrow(() -> new UsernameNotFoundException("username " + jwt.getSubject() + " is not found."));
      return user.getStudent();
    }
  }

}

class Report {
  List<EvaluationDto> evals;
  RubricDto rubric;
  Integer totalMaxScore;

  public Report(List<EvaluationDto> evals, RubricDto rubric) {
    this.evals = evals;
    this.rubric = rubric;
    this.totalMaxScore = calculateMaxPossibleScore();
  }

  /*
   * calculating the max possbile score here, is becuase it requires
   * adding values from multiple lists, the reason this isn't done for
   * the actual scores is that they all exist within the same method, so
   * its much easier to calculate the sum, but also you need the individual scores
   */
  private Integer calculateMaxPossibleScore() {
    return this.rubric.criterion().stream()
        .mapToInt(crit -> crit.maxScore())
        .sum();
  }

  public List<EvaluationDto> getEvals() {
    return this.evals;
  }

  public void setEvals(List<EvaluationDto> evals) {
    this.evals = evals;
  }

  public RubricDto getRubric() {
    return this.rubric;
  }

  public void setRubric(RubricDto rubricDto) {
    this.rubric = rubricDto;
  }

  public Integer getTotalMaxScore() {
    return this.totalMaxScore;
  }

  public void setTotalMaxScore(Integer totalMaxScore) {
    this.totalMaxScore = totalMaxScore;
  }

}
