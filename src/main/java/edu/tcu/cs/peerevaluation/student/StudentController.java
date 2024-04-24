package edu.tcu.cs.peerevaluation.student;

import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserService;
import edu.tcu.cs.peerevaluation.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  private final StudentToStudentDtoConverter studentToStudentDtoConverter;

  private final StudentDtoToStudentConverter studentDtoToStudentConverter;

  private final UserService userService;

  private final UserRepository userRepository;

  public StudentController(StudentService studentService, StudentToStudentDtoConverter studentToStudentDtoConverter, StudentDtoToStudentConverter studentDtoToStudentConverter, UserService userService, UserRepository userRepository) {
    this.studentService = studentService;
    this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping
  public Result findAllStudents() {
    List<Student> foundStudents = this.studentService.findAll();
    List<StudentDto> studentDtos = foundStudents.stream()
        .map(foundStudent -> this.studentToStudentDtoConverter.convert(foundStudent))
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Find All Success", studentDtos);

  }

  /*
   * returns a list of students who match the requested criteria
   * /students/search?firstName=John&lastName=Doe&section=A&academicYear=2023&teamName=RedDragons 
   */
  @GetMapping("/search")
  public Result searchStudents(
      @RequestParam(value = "firstName", required = false) String firstName,
      @RequestParam(value = "lastName", required = false) String lastName,
      @RequestParam(value = "section", required = false) String section,
      @RequestParam(value = "academicYear", required = false) String academicYear,
      @RequestParam(value = "teamName", required = false) String teamName) {

    List<Student> foundStudents = this.studentService.searchStudents(firstName, lastName, section, academicYear,
        teamName);
    if (foundStudents.size() > 1) {
      Comparator<Student> sortByAcademicYear = Comparator.comparing(Student::getAcademicYear,
          Comparator.reverseOrder());
      Comparator<Student> sortByLastName = Comparator.comparing(Student::getLastName);

      Comparator<Student> sortByBoth = sortByAcademicYear.thenComparing(sortByLastName);

      foundStudents.sort(sortByBoth);
    }
    List<StudentDto> studentDtos = foundStudents.stream()
        .map(foundStudent -> this.studentToStudentDtoConverter.convert(foundStudent))
        .collect(Collectors.toList());

    return new Result(true, StatusCode.SUCCESS, "Search Success", studentDtos);
  }

  /*
   * returns a student based on their ID
   */
  @GetMapping("/{studentId}")
  public Result findStudentById(@PathVariable Integer studentId) {
    Student foundStudent = this.studentService.findById(studentId);
    StudentDto studentDto = this.studentToStudentDtoConverter.convert(foundStudent);
    return new Result(true, StatusCode.SUCCESS, "Find One Success", studentDto);
  }

  /*
   * creates both a student object as well as the associated user object
   */
  @PostMapping
  public Result addStudent(@Valid @RequestBody StudentUserCombined studentUserCombined) {
    Student newStudent = this.studentDtoToStudentConverter.convert(studentUserCombined.getStudentDto());
    Student savedStudent = this.studentService.save(newStudent);
    PeerEvalUser savedUser = this.userService.save(studentUserCombined.getUser());
    savedUser.setStudent(savedStudent);
    savedStudent.setUser(savedUser);
    savedStudent = this.studentService.save(savedStudent);
    savedUser = this.userService.update(savedUser.getId(),savedUser);
    StudentDto savedStudentDto = this.studentToStudentDtoConverter.convert(savedStudent);
    return new Result(true, StatusCode.SUCCESS, "Add Success", savedStudentDto);
  }

  /*
   * updates the student object 
   */
  @PutMapping("/{studentId}")
  public Result updateStudent(@PathVariable Integer studentId, @Valid @RequestBody StudentDto studentDto) {
    Student update = this.studentDtoToStudentConverter.convert(studentDto);
    Student updatedStudent = this.studentService.update(studentId, update);
    StudentDto updatedStudentDto = this.studentToStudentDtoConverter.convert(updatedStudent);
    return new Result(true, StatusCode.SUCCESS, "Update Success", updatedStudentDto);
  }

  /*
   * deletes the student object, as well as anything that relies on it
   */
  @DeleteMapping("/{studentId}")
  public Result deleteStudent(@PathVariable Integer studentId) {
    this.studentService.delete(studentId);
    return new Result(true, StatusCode.SUCCESS, "Delete Success");
  }

  /*
   * will change the password
   */
  @PutMapping("/changePassword")
  public Result changePassword(@Valid @RequestBody String newPass){
    PeerEvalUser currentUser = getLoggedInStudent().getUser();
    currentUser.setPassword(newPass);
    this.userService.updatePass(currentUser);
    return new Result(true, StatusCode.SUCCESS, "Change Password Success",newPass);
  }

  @GetMapping("/teammates")
  public Result getTeammates(){
    Student currentStudent = getLoggedInStudent();
    List<Student> teammates = currentStudent.getTeammates();
    List<StudentDto> studentDtos = teammates.stream()
        .map(foundStudent -> this.studentToStudentDtoConverter.convert(foundStudent))
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Get Teammate Success", studentDtos);
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
