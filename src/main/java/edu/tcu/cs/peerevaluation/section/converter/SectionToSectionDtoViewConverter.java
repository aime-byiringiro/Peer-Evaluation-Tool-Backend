package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDtoView;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamService;
import edu.tcu.cs.peerevaluation.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevaluation.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class SectionToSectionDtoViewConverter implements Converter<Section, SectionDtoView> {

    private final RubricToRubricDtoConverter rubricToRubricDtoConverter;

    private  final TeamDto  teamDto;

    private final Team team;

    private final Section section;

    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    private final TeamDtoToTeamConverter teamDtoToTeamConverter;



    private final TeamService teamService;

    public SectionToSectionDtoViewConverter(RubricToRubricDtoConverter rubricToRubricDtoConverter, TeamDto teamDto, Team team, Section section, TeamToTeamDtoConverter teamToTeamDtoConverter, TeamDtoToTeamConverter teamDtoToTeamConverter, TeamService teamService) {
        this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
        this.teamDto = teamDto;
        this.team = team;
        this.section = section;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
        this.teamService = teamService;
    }

    @Override
    public SectionDtoView convert(Section source) {
        return null;
    }

/*
    public SectionDtoView convert(Section source) {
            List<Team> teams = teamService.findAll();

            SectionDtoView sectionDtoView = new SectionDtoView(

                    source.getId(),
                    source.getSectionName(),
                    source.getAcademicYear(),
                    source.getFirstDay(),
                    source.getLastDay(),
                    this.rubricToRubricDtoConverter.convert(source.getRubric());
                    //this.teamToTeamDtoConverter.convert(source.getTeams());




            );

            return sectionDtoView;



    }


 */

}
