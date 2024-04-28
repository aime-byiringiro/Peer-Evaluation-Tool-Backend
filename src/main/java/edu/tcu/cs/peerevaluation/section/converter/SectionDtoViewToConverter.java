package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.section.dto.SectionDtoView;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamService;
import edu.tcu.cs.peerevaluation.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class SectionDtoViewToConverter  implements  Converter<SectionDtoView, Section> {

    private final RubricDtoToRubricConverter rubricDtoToRubricConverter;



    private final TeamService teamService;

    public SectionDtoViewToConverter(RubricDtoToRubricConverter rubricDtoToRubricConverter, TeamDtoToTeamConverter teamDtoToTeamConverter, TeamService teamService) {
        this.rubricDtoToRubricConverter = rubricDtoToRubricConverter;
        this.teamService = teamService;
    }


    @Override
    public Section convert(SectionDtoView source) {

        List<Team> teams = teamService.findAll();

        Section section = new Section();
        section.setId(source.id());
        section.setSectionName(source.sectionName());
        section.setAcademicYear(source.academicYear());
        section.setFirstDay(source.firstDay());
        section.setLastDay(source.lastDay());
        section.setRubric(this.rubricDtoToRubricConverter.convert(source.rubricDto()));
        section.setTeams(teams);

        return section;
    }
}
