package edu.tcu.cs.peerevaluation.peerEvaluation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationDtoToPeerEvaluationConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.converter.PeerEvaluationToPeerEvaluationDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvaluation.dto.PeerEvaluationDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




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
    PeerEvaluationDto savedDto = this.peerEvalToDtoConverter.convert(savedPeerEval);
    return new Result(true,StatusCode.SUCCESS,"add success", savedDto);
  }

  @GetMapping("/{peerEvalId}")
  public Result getPeerEvalById(@PathVariable Integer peerEvalId) {
    PeerEvaluation peerEval = this.peerEvalService.findById(peerEvalId);
    PeerEvaluationDto peerEvalDto = this.peerEvalToDtoConverter.convert(peerEval);
    return new Result(true,StatusCode.SUCCESS,"find success", peerEvalDto);
  }
  
}
