package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevaluation.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.TemporalAmount;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamDtoToTeamConverter teamDtoToTeamConverter;
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;


    public TeamController(TeamService teamService, TeamDtoToTeamConverter teamDtoToTeamConverter, TeamToTeamDtoConverter teamToTeamDtoConverter) {
        this.teamService = teamService;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
    }



    @PostMapping
    public Result createNewTeam(@RequestBody TeamDto teamDto){
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return  new Result(true, StatusCode.SUCCESS,"Create Success", savedTeamDto );
    }
}
