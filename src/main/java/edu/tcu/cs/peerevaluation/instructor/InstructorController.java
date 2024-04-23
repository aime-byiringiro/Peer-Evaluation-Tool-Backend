package edu.tcu.cs.peerevaluation.instructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

  public final InstructorService instructorService;

  public final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

  public final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;

  private final UserService userService;

  private final UserRepository userRepository;

  
  public InstructorController(InstructorService instructorService,
      InstructorToInstructorDtoConverter instructorToInstructorDtoConverter,
      InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, UserService userService,
      UserRepository userRepository) {
    this.instructorService = instructorService;
    this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
    this.userService = userService;
    this.userRepository = userRepository;
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
  public ResponseEntity<Result> addInstructor(@Valid @RequestBody InstructorUserCombined instructorUserCombined) {
    Instructor newInstructor = instructorDtoToInstructorConverter.convert(instructorUserCombined.getInstructorDto());
    Instructor savedInstructor = instructorService.save(newInstructor);

    PeerEvalUser newUser = new PeerEvalUser(); // Assuming you have similar setup or manually set properties
    newUser.setUsername(instructorUserCombined.getUser().getUsername());
    newUser.setPassword(instructorUserCombined.getUser().getPassword()); // Consider hashing the password
    newUser.setInstructor(savedInstructor); // Link user to the newly created instructor

    PeerEvalUser savedUser = userService.save(newUser);
    savedInstructor.setUser(savedUser); // Link instructor back to the newly created user
    savedInstructor = instructorService.save(savedInstructor); // Save the instructor again if necessary

    InstructorDto savedInstructorDto = instructorToInstructorDtoConverter.convert(savedInstructor);
    return ResponseEntity.ok(new Result(true, StatusCode.SUCCESS, "Instructor added successfully", savedInstructorDto));
  }

}
