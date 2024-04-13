package edu.tcu.cs.peerevaluation.team.converter;


import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamDtoConverter implements Converter<Team, TeamDto> {

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public TeamToTeamDtoConverter(SectionToSectionDtoConverter sectionToSectionDtoConverter, StudentToStudentDtoConverter studentToStudentDtoConverter) {
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }

    @Override
    public TeamDto convert(Team source) {
        TeamDto teamDto = new TeamDto(
                source.getId(),
                source.getTeamName(),
                this.sectionToSectionDtoConverter.convert(source.getSection()),
                source.getStudents());
        return teamDto;
    }
}
