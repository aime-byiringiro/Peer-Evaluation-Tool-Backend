package edu.tcu.cs.peerevaluation.peerEvalUser;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.InstructorService;
import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserDtoToUserConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserToUserDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private final UserDtoToUserConverter userDtoToUserConverter;

  private final UserToUserDtoConverter userToUserDtoConverter;

  private final InstructorService instructorService;

  public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter, InstructorService instructorService) {
    this.userService = userService;
    this.userDtoToUserConverter = userDtoToUserConverter;
    this.userToUserDtoConverter = userToUserDtoConverter;
    this.instructorService = instructorService;
  }

  /*
   * returns a list of all users 
   */
  @GetMapping
  public Result findAllUsers() {
    List<PeerEvalUser> foundPeerEvalUsers = this.userService.findAll();

    // Convert foundUsers to a list of UserDtos.
    List<UserDto> userDtos = foundPeerEvalUsers.stream()
        .map(this.userToUserDtoConverter::convert)
        .collect(Collectors.toList());

    // Note that UserDto does not contain password field.
    return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
  }

  /*
   * returns a specific user based on ID
   */
  @GetMapping("/{userId}")
  public Result findUserById(@PathVariable Integer userId) {
    PeerEvalUser foundPeerEvalUser = this.userService.findById(userId);
    UserDto userDto = this.userToUserDtoConverter.convert(foundPeerEvalUser);
    return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
  }

  /*
   * adds a user, not used
   */
  @PostMapping
  public Result addUser(@Valid @RequestBody PeerEvalUser newPeerEvalUser) {
    PeerEvalUser savedUser = this.userService.save(newPeerEvalUser);
    UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
    return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
  }

  /*
   * updates a user, not password
   */
  @PutMapping("/{userId}")
  public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
    PeerEvalUser update = this.userDtoToUserConverter.convert(userDto);
    PeerEvalUser updatedPeerEvalUser = this.userService.update(userId, update);
    UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedPeerEvalUser);
    return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
  }

  /*
   * deletes a user, don't use, delete the associated object
   */
  @DeleteMapping("/{userId}")
  public Result deleteUser(@PathVariable Integer userId) {
    this.userService.delete(userId);
    return new Result(true, StatusCode.SUCCESS, "Delete Success");
  }

  @PutMapping("/deactivate/{instructorId}")
  public Result deactivateUser(@PathVariable Integer instructorId) {
    Instructor foundInstructor = this.instructorService.findById(instructorId);
    this.userService.deactivate(foundInstructor.getUser().getId());
    return new Result(true, StatusCode.SUCCESS, "Deactivate Success");
  }

  @PutMapping("/activate/{instructorId}")
  public Result activateUser(@PathVariable Integer instructorId) {
    Instructor foundInstructor = this.instructorService.findById(instructorId);
    this.userService.activate(foundInstructor.getUser().getId());
    return new Result(true, StatusCode.SUCCESS, "Activate Success");
  }

  @GetMapping("/invite/instructor/{instructorId}")
  public Result inviteInstructor(@PathVariable Integer instructorId) {
      return new Result(true, StatusCode.SUCCESS, "Email sent");
  }
  

}
