package edu.tcu.cs.peerevaluation.instructor.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.instructor.Instructor;


import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;

import org.springframework.core.convert.converter.Converter;

@Component
public class InstructorDtoToInstructorConverter implements Converter<InstructorDto, Instructor> {

  @Override
  public Instructor convert(InstructorDto source) {
    Instructor instructor = new Instructor();
    instructor.setId(source.id());
    instructor.setFirstName(source.firstName());
    instructor.setMiddleInitial(source.middleInitial());
    instructor.setLastName(source.lastName());
    instructor.setEmail(source.email());
    return instructor;

  }
}
