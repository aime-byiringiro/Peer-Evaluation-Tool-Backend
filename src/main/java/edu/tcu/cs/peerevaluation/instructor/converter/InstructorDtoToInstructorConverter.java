package edu.tcu.cs.peerevaluation.instructor.converter;

import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.instructor.Instructor;


import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamRepository;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

@Component
public class InstructorDtoToInstructorConverter implements Converter<InstructorDto, Instructor> {

  private final TeamRepository teamRepository;

  public InstructorDtoToInstructorConverter(TeamRepository teamRepository) {
      this.teamRepository = teamRepository;
  }

  @Override
  public Instructor convert(InstructorDto source) {
    Instructor instructor = new Instructor();
        instructor.setId(source.id());
        instructor.setFirstName(source.firstName());
        instructor.setMiddleInitial(source.middleInitial());
        instructor.setLastName(source.lastName());
        instructor.setEmail(source.email());
        instructor.setTeams(source.teams());

        return instructor;

  }
}
