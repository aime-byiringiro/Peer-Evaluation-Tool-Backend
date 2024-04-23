package edu.tcu.cs.peerevaluation.war;

import edu.tcu.cs.peerevaluation.war.dto.WARDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.war.converter.WARDtoToWARConverter;
import edu.tcu.cs.peerevaluation.war.converter.WARToWARDtoConverter;
import edu.tcu.cs.peerevaluation.war.submission.Submission;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionDtoToSubmissionConverter;
import edu.tcu.cs.peerevaluation.war.submission.converter.SubmissionToSubmissionDtoConverter;
import edu.tcu.cs.peerevaluation.war.submission.dto.SubmissionDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/war")
public class WARController {

  private final UserRepository userRepository;

  private final WARService warService;

  private final WARDtoToWARConverter warDtoToWARConverter;

  private final WARToWARDtoConverter warToWARDtoConverter;

  private final SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter;

  private final SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter;

  public WARController(UserRepository userRepository, WARService warService, WARDtoToWARConverter warDtoToWARConverter, WARToWARDtoConverter warToWARDtoConverter, SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter, SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter) {
    this.userRepository = userRepository;
    this.warService = warService;
    this.warDtoToWARConverter = warDtoToWARConverter;
    this.warToWARDtoConverter = warToWARDtoConverter;
    this.submissionDtoToSubmissionConverter = submissionDtoToSubmissionConverter;
    this.submissionToSubmissionDtoConverter = submissionToSubmissionDtoConverter;
  }




  //Admin set up active week for a senior design section
  @PostMapping
  public Result setUpWAR(@Valid @RequestBody WARDto warDto) {
    WAR newWar = this.warDtoToWARConverter.convert(warDto);
    WAR savedWar = this.warService.saveWar(newWar);
    WARDto savedWarDto = this.warToWARDtoConverter.convert(savedWar);
    return new Result(true, StatusCode.SUCCESS, "Set Up Success", savedWarDto);
  }


  //find submission by ID
  @GetMapping("/id/{submissionId}")
  public Result findSubmissionById(@PathVariable Integer submissionId){
    Submission foundSubmission = this.warService.findById(submissionId);
    SubmissionDto foundDto = this.submissionToSubmissionDtoConverter.convert(foundSubmission);
    return new Result(true,StatusCode.SUCCESS,"Find By ID Success", foundDto);
  }

  @GetMapping
  public Result findAllSubmissions() {
    List<Submission> foundSubmissions = this.warService.findAll();
    List<SubmissionDto> submissionDtos = foundSubmissions.stream()
        .map(foundSubmission -> this.submissionToSubmissionDtoConverter.convert(foundSubmission))
        .collect(Collectors.toList());
    return new Result(true,StatusCode.SUCCESS,"Find All Success", submissionDtos);
  }
  
  //findByActiveWeek
  @GetMapping("/{activeWeek}")
  public Result getByActiveWeek(@PathVariable String activeWeek) {
    Student loggedInStudent = getLoggedInStudent();
    List<Submission> foundSubmissions = this.warService.findByWeekAndStudent(loggedInStudent.getId(), activeWeek);
    List<SubmissionDto> foundDtos = foundSubmissions.stream()
            .map(foundSubmission -> this.submissionToSubmissionDtoConverter.convert(foundSubmission))
            .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Find By Week Success", foundDtos);
  }

  //addSubmission
  @PostMapping("/{week}")
  public Result newSubmission(@PathVariable Integer week, @Valid @RequestBody SubmissionDto submissionDto) {
    Student loggedInStudent = getLoggedInStudent();
    System.out.println(submissionDto);
    Submission newSubmission = this.submissionDtoToSubmissionConverter.convert(submissionDto);
    newSubmission.setWar(this.warService.findByWeekAndTeam(loggedInStudent.getTeam().getId(), week));
    newSubmission.setTeamMember(loggedInStudent);
    Submission savedSubmission = this.warService.saveSubmission(newSubmission);
    SubmissionDto savedDto = this.submissionToSubmissionDtoConverter.convert(savedSubmission);
    return new Result(true,StatusCode.SUCCESS,"Add Success", savedDto);
  }

  //editSubmission
  @PutMapping
  public Result updateSubmission(@Valid @RequestBody SubmissionDto submissionDto){
    Submission update = this.submissionDtoToSubmissionConverter.convert(submissionDto);
    Submission updatedSubmission = this.warService.update(submissionDto.id(),update);
    SubmissionDto updatedDto = this.submissionToSubmissionDtoConverter.convert(updatedSubmission);
    return new Result(true,StatusCode.SUCCESS,"Update Success", updatedDto);
  }

  //deleteSubmission
  @DeleteMapping("/{submissionId}")
  public Result deleteSubmission(@PathVariable Integer submissionId){
    this.warService.deleteSubmission(submissionId);
    return new Result(true,StatusCode.SUCCESS,"Delete Success");
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
