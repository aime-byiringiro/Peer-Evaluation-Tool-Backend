package edu.tcu.cs.peerevaluation.war;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/war")
public class WARController {

  private final WARService warService;

  private final WARDtoToWARConverter warDtoToWARConverter;

  private final WARToWARDtoConverter warToWARDtoConverter;

  private final SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter;

  private final SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter;

  public WARController(WARService warService, WARDtoToWARConverter warDtoToWARConverter, WARToWARDtoConverter warToWARDtoConverter, SubmissionDtoToSubmissionConverter submissionDtoToSubmissionConverter, SubmissionToSubmissionDtoConverter submissionToSubmissionDtoConverter) {
    this.warService = warService;
    this.warDtoToWARConverter = warDtoToWARConverter;
    this.warToWARDtoConverter = warToWARDtoConverter;
    this.submissionDtoToSubmissionConverter = submissionDtoToSubmissionConverter;
    this.submissionToSubmissionDtoConverter = submissionToSubmissionDtoConverter;
  }

  //find submission by ID
  @GetMapping("/{submissionId}")
  public Result findSubmissionById(@PathVariable Integer submissionId){
    Submission foundSubmission = this.warService.findById(submissionId);
    SubmissionDto foundDto = this.submissionToSubmissionDtoConverter.convert(foundSubmission);
    return new Result(true,StatusCode.SUCCESS,"find by id success", foundDto);
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Student loggedInStudent = principal.getPeerEvalUser().getStudent(); 

    List<Submission> foundSubmissions = this.warService.findByWeekAndStudent(loggedInStudent.getId(), activeWeek);
    List<SubmissionDto> foundDtos = foundSubmissions.stream()
            .map(foundSubmission -> this.submissionToSubmissionDtoConverter.convert(foundSubmission))
            .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "find by week success", foundDtos);
  }

  //addSubmission
  @PostMapping("/{week}")
  public Result newSubmission(@PathVariable Integer week, @Valid @RequestBody SubmissionDto submissionDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
    Student loggedInStudent = principal.getPeerEvalUser().getStudent();
    
    Submission newSubmission = this.submissionDtoToSubmissionConverter.convert(submissionDto);
    newSubmission.setWar(this.warService.findByWeekAndTeam(loggedInStudent.getTeam().getId(), week));
    newSubmission.setTeamMember(loggedInStudent);
    Submission savedSubmission = this.warService.saveSubmission(newSubmission);
    SubmissionDto savedDto = this.submissionToSubmissionDtoConverter.convert(savedSubmission);
    return new Result(true,StatusCode.SUCCESS,"update success", savedDto);
  }

  //editSubmission
  @PutMapping
  public Result updateSubmission(@Valid @RequestBody SubmissionDto submissionDto){
    Submission update = this.submissionDtoToSubmissionConverter.convert(submissionDto);
    Submission updatedSubmission = this.warService.update(submissionDto.id(),update);
    SubmissionDto updatedDto = this.submissionToSubmissionDtoConverter.convert(updatedSubmission);
    return new Result(true,StatusCode.SUCCESS,"add success", updatedDto);
  }

  //deleteSubmission
  @DeleteMapping("/{submissionId}")
  public Result deleteSubmission(@PathVariable Integer submissionId){
    this.warService.deleteSubmission(submissionId);
    return new Result(true,StatusCode.SUCCESS,"delete success");
  }

}
