package edu.tcu.cs.peerevaluation.instructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.instructor.converter.InstructorDtoToInstructorConverter;
import edu.tcu.cs.peerevaluation.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;

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
    List<InstructorDto> instructorDtos = foundInstructors.stream()
        .map(foundInstructor -> this.instructorToInstructorDtoConverter.convert(foundInstructor))
        .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Find All Success", instructorDtos);
  }

  @GetMapping("/search")
    public Result searchInstructors(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "academicYear", required = false) String academicYear,
        @RequestParam(value = "teamName", required = false) String teamName,
        @RequestParam(value = "enabled", required = false) Boolean enabled) {

        List<Instructor> foundInstructors = this.instructorService.searchInstructors(firstName, lastName, academicYear,
            teamName, enabled != null && enabled);

        if (foundInstructors.size() > 1) {
            Comparator<Instructor> sortByAcademicYear = Comparator
                .comparing(instructor -> instructor.getTeams().isEmpty() ? "" : instructor.getTeams().get(0).getSection().getAcademicYear(),
                    Comparator.nullsLast(Comparator.reverseOrder()));
            Comparator<Instructor> sortByLastName = Comparator.comparing(Instructor::getLastName);

            Comparator<Instructor> sortByBoth = sortByAcademicYear.thenComparing(sortByLastName);

            foundInstructors.sort(sortByBoth);
        }

        List<InstructorDto> instructorDtos = foundInstructors.stream()
            .map(foundInstructor -> this.instructorToInstructorDtoConverter.convert(foundInstructor))
            .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Search Success", instructorDtos);
    }


  


}
