package edu.tcu.cs.peerevaluation.instructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.instructor.converter.InstructorDtoToInstructorConverter;
import edu.tcu.cs.peerevaluation.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserService;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

  public final InstructorService instructorService;

  public final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

  public final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;

  private final UserService userService;

  private final UserRepository userRepository;

  private final TeamService teamService;

  public InstructorController(InstructorService instructorService, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter, InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, UserService userService, UserRepository userRepository, TeamService teamService) {
    this.instructorService = instructorService;
    this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
    this.userService = userService;
    this.userRepository = userRepository;
    this.teamService = teamService;
  }
  

  @GetMapping("/{instructorId}")
  public Result findInstructorById(@PathVariable Integer instructorId) {
    Instructor foundInstructor = this.instructorService.findById(instructorId);
    InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(foundInstructor);
    return new Result(true, StatusCode.SUCCESS, "Find One Success", instructorDto);
  }

  @GetMapping("/search")
  public Result searchInstructors(@RequestParam(required = false) String firstName,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) String academicYear,
      @RequestParam(required = false) String teamName) {
    List<Instructor> instructors = instructorService.search(firstName, lastName, academicYear, teamName);
    List<InstructorDto> instructorDtos = instructors.stream().map(instructorToInstructorDtoConverter::convert)
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Search Success", instructorDtos);
  }

  @PostMapping
  public Result addInstructor(@Valid @RequestBody InstructorUserCombined instructorUserCombined) {
    Instructor newInstructor = this.instructorDtoToInstructorConverter.convert(instructorUserCombined.getInstructorDto());
    Instructor savedInstructor = this.instructorService.save(newInstructor);
    PeerEvalUser savedUser = this.userService.save(instructorUserCombined.getUser());
    savedUser.setInstructor(savedInstructor);
    savedInstructor.setUser(savedUser);
    savedInstructor = this.instructorService.save(savedInstructor);
    savedUser = this.userService.update(savedUser.getId(),savedUser);
    InstructorDto savedInstructorDto = this.instructorToInstructorDtoConverter.convert(savedInstructor);
    return new Result(true, StatusCode.SUCCESS, "Add Success", savedInstructorDto);
  }

  @PutMapping("/assign/{instructorId}/{teamId}")
  public Result assignInstructor(@PathVariable Integer instructorId, @PathVariable Integer teamId) {
    Instructor foundInstructor = this.instructorService.findById(instructorId);
    Team foundTeam = this.teamService.findById(teamId);
    if(foundTeam.getInstructor() != null) {
      return new Result(false, StatusCode.FORBIDDEN, "Instructor already assigned");
    }
    foundInstructor.assignInstructorToTeam(foundTeam);
    this.instructorService.save(foundInstructor);
    InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(foundInstructor);
    return new Result(true, StatusCode.SUCCESS, "Instructor assigned to team successfully", instructorDto);
}

@PutMapping("/remove/{instructorId}/{teamId}")
public Result removeInstructorFromTeam(@PathVariable Integer teamId) {
    Team foundTeam = this.teamService.findById(teamId);
    System.out.println(foundTeam.getInstructor());
    if(foundTeam.getInstructor() == null) {
      return new Result(true, StatusCode.SUCCESS, "no intructor to remove");
    } else {
      foundTeam.removeInstructor();
      this.teamService.save(foundTeam);
      return new Result(true, StatusCode.SUCCESS, "Instructor removed from team successfully");
    }
}
}
