package edu.tcu.cs.peerevaluation.peerEvalUser;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserDtoToUserConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserToUserDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private final UserDtoToUserConverter userDtoToUserConverter;

  private final UserToUserDtoConverter userToUserDtoConverter;

  public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter,
      UserToUserDtoConverter userToUserDtoConverter) {
    this.userService = userService;
    this.userDtoToUserConverter = userDtoToUserConverter;
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

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

  @GetMapping("/{userId}")
  public Result findUserById(@PathVariable Integer userId) {
    PeerEvalUser foundPeerEvalUser = this.userService.findById(userId);
    UserDto userDto = this.userToUserDtoConverter.convert(foundPeerEvalUser);
    return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
  }

  @PostMapping
  public Result addUser(@Valid @RequestBody PeerEvalUser newPeerEvalUser) {
    PeerEvalUser savedUser = this.userService.save(newPeerEvalUser);
    UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
    return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
  }

  // We are not using this to update password, need another changePassword method
  // in this class.
  @PutMapping("/{userId}")
  public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
    PeerEvalUser update = this.userDtoToUserConverter.convert(userDto);
    PeerEvalUser updatedPeerEvalUser = this.userService.update(userId, update);
    UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedPeerEvalUser);
    return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
  }

  @DeleteMapping("/{userId}")
  public Result deleteUser(@PathVariable Integer userId) {
    this.userService.delete(userId);
    return new Result(true, StatusCode.SUCCESS, "Delete Success");
  }

}
