package edu.tcu.cs.peerevaluation.team.converter;

import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {

    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public TeamDtoToTeamConverter(SectionDtoToSectionConverter sectionDtoToSectionConverter) {
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }

    @Override
    public Team convert(TeamDto source) {
        Team team = new Team();
        team.setId(source.id());
        team.setSection(this.sectionDtoToSectionConverter.convert(source.section()));
        team.setStudents(source.students());
        return team;
    }
}
