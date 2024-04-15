package edu.tcu.cs.peerevaluation.student;

import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;

public class StudentUserCombined {

  private StudentDto studentDto;

  private UserDto userDto;

  public StudentUserCombined() {

  }

  public StudentDto getStudentDto() {
    return this.studentDto;
  }

  public void setStudentDto(StudentDto studentDto) {
    this.studentDto = studentDto;
  }

  public UserDto getUserDto() {
    return this.userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

}
