package edu.tcu.cs.peerevaluation.rubric;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rubric")
public class RubricController {

  private final RubricService rubricService;

  private final RubricToRubricDtoConverter rubricToRubricDtoConverter;

  private final RubricDtoToRubricConverter rubricDtoToRubricConverter;

  private final UserRepository userRepository;

  public RubricController(RubricService rubricService, RubricToRubricDtoConverter rubricToRubricDtoConverter, RubricDtoToRubricConverter rubricDtoToRubricConverter, UserRepository userRepository) {
    this.rubricService = rubricService;
    this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
    this.rubricDtoToRubricConverter = rubricDtoToRubricConverter;
    this.userRepository = userRepository;
  }

  /*
   * returns the rubric for peer evals based on the currently logged in student
   */
  @GetMapping()
  public Result getMethodName() {
    Rubric rubric = getLoggedInStudent().getTeam().getSection().getRubric();
    RubricDto rubricDto = this.rubricToRubricDtoConverter.convert(rubric);
    return new Result(true, StatusCode.SUCCESS, "rubric success",rubricDto);
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
