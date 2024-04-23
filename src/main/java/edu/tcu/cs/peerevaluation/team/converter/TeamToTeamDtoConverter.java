package edu.tcu.cs.peerevaluation.team.converter;


import edu.tcu.cs.peerevaluation.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamToTeamDtoConverter implements Converter<Team, TeamDto> {

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    public TeamToTeamDtoConverter(SectionToSectionDtoConverter sectionToSectionDtoConverter, StudentToStudentDtoConverter studentToStudentDtoConverter, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter) {
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    }

    @Override
    public TeamDto convert(Team source) {

        // student array -> array of student ids
        List<Integer> studentIds = new ArrayList<>();
        for (Student student : source.getStudents()) {
            studentIds.add(student.getId());
        }

        TeamDto teamDto = new TeamDto(
                source.getId(),
                source.getTeamName(),
                source.getAcademicYear(),
                this.sectionToSectionDtoConverter.convert(source.getSection()),
                studentIds,
                this.instructorToInstructorDtoConverter.convert(source.getInstructor()));
        return teamDto;
    }
}
