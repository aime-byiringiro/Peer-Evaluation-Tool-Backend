package edu.tcu.cs.peerevaluation.instructor.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class InstructorToInstructorDtoConverter implements Converter<Instructor, InstructorDto> {



  @Override
    public InstructorDto convert(Instructor source) {
      InstructorDto instructorDto = new InstructorDto(
                source.getId(),
                source.getFirstName(),
                source.getMiddleInitial(),
                source.getLastName(),
                source.getEmail(),
                source.getTeamNamesList());
        return instructorDto;
    }

}
