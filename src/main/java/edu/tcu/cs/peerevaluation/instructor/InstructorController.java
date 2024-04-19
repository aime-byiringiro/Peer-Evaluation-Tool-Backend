package edu.tcu.cs.peerevaluation.instructor;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.instructor.converter.InstructorDtoToInstructorConverter;
import edu.tcu.cs.peerevaluation.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevaluation.system.Result;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

  public final InstructorService instructorService;

  public final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

  public final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;

  public InstructorController(InstructorService instructorService, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter, InstructorDtoToInstructorConverter instructorDtoToInstructorConverter) {
    this.instructorService = instructorService;
    this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
  }

  @GetMapping()
  public Result findAllInstructors() {
    List<Instructor> foundInstructors = this.instructorService.findAll();
    return null;
  }


  


}
