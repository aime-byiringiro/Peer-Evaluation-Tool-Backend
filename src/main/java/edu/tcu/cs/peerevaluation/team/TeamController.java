package edu.tcu.cs.peerevaluation.team;


import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import edu.tcu.cs.peerevaluation.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevaluation.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


import java.sql.Statement;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/team")
public class TeamController {


    private final TeamService teamService;

    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    private final TeamDtoToTeamConverter teamDtoToTeamConverter;


    public TeamController(TeamService teamService, TeamToTeamDtoConverter teamToTeamDtoConverter, TeamDtoToTeamConverter teamDtoToTeamConverter) {
        this.teamService = teamService;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
    }



    @PostMapping
    public Result addTeam(@Valid @RequestBody TeamDto teamDto){
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return  new Result(true, StatusCode.SUCCESS, "Add Success", savedTeamDto);
    }
}
