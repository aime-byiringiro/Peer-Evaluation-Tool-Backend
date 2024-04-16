package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationDtoToPeerEvaluationConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationToPeerEvaluationDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;

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


  public PeerEvaluationController(PeerEvaluationService peerEvalService, PeerEvaluationToPeerEvaluationDtoConverter peerEvalToDtoConverter, PeerEvaluationDtoToPeerEvaluationConverter dtoToPeerEvalConverter) {
    this.peerEvalService = peerEvalService;
    this.peerEvalToDtoConverter = peerEvalToDtoConverter;
    this.dtoToPeerEvalConverter = dtoToPeerEvalConverter;
  }

  @PostMapping
  public Result newPeerEvaluation(@Valid @RequestBody PeerEvaluationDto peerEvalDto) {
    PeerEvaluation newPeerEval = this.dtoToPeerEvalConverter.convert(peerEvalDto);
    PeerEvaluation savedPeerEval = this.peerEvalService.save(newPeerEval);
    savedPeerEval.getEvaluations().forEach(eval -> {
      eval.setPeerEvalId(newPeerEval.getId());
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
  
}
