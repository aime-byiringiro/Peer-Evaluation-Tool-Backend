package edu.tcu.cs.peerevaluation.student;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.Valid;

public class StudentUserCombined {

  @Valid
  private StudentDto studentDto;

  private PeerEvalUser user;

  public StudentUserCombined() {

  }

  public StudentUserCombined(StudentDto studentDto, PeerEvalUser user) {
    this.studentDto = studentDto;
    this.user = user;
  }

  public StudentDto getStudentDto() {
    return this.studentDto;
  }

  public void setStudentDto(StudentDto studentDto) {
    this.studentDto = studentDto;
  }

  public PeerEvalUser getUser() {
    return this.user;
  }

  public void setUser(PeerEvalUser user) {
    this.user = user;
  }

}
