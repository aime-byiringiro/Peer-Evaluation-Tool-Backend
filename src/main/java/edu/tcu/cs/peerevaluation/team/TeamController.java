package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevaluation.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    private final TeamDtoToTeamConverter teamDtoToTeamConverter;

    public TeamController(TeamService teamService, TeamToTeamDtoConverter teamToTeamDtoConverter, TeamDtoToTeamConverter teamDtoToTeamConverter) {
        this.teamService = teamService;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
    }

    @GetMapping
    public Result findAllTeams() {
        List<Team> foundTeams = this.teamService.findAll();

        List<TeamDto> teamDtos = foundTeams.stream()
                .map(this.teamToTeamDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Find All Success", teamDtos);
    }

    @GetMapping("/search")
    public Result searchTeams(
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "sectionName", required = false) String sectionName,
            @RequestParam(value = "academicYear", required = false) String academicYear,
            @RequestParam(value = "instructorFirstName", required = false) String instructorFirstName,
            @RequestParam(value = "instructorLastName", required = false) String instructorLastName) {

        List<Team> foundTeams = this.teamService.searchTeams(teamName, sectionName, academicYear, instructorFirstName,
                instructorLastName);

        if (foundTeams.size() > 1) {
            Comparator<Team> sortByAcademicYear = Comparator.comparing(Team::getAcademicYear,
                    Comparator.reverseOrder());
            Comparator<Team> sortByTeamName = Comparator.comparing(Team::getTeamName);

            Comparator<Team> sortByBoth = sortByAcademicYear.thenComparing(sortByTeamName);

            foundTeams.sort(sortByBoth);
        }

        List<TeamDto> teamDtos = foundTeams.stream()
                .map(this.teamToTeamDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Search Success", teamDtos);
    }

    @GetMapping("/{teamId}")
    public Result findTeamById(@PathVariable Integer teamId) {
        Team foundTeam = this.teamService.findById(teamId);
        TeamDto teamDto = this.teamToTeamDtoConverter.convert(foundTeam);
        return new Result(true, StatusCode.SUCCESS, "Find One Team Success", teamDto);
    }

    @PostMapping
    public Result addTeam(@Valid @RequestBody TeamDto teamDto) {
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedTeamDto);
    }

    @PutMapping("/{teamId}")
    public Result updateTeam(@PathVariable Integer teamId, @Valid @RequestBody TeamDto teamDto) {
        Team update = this.teamDtoToTeamConverter.convert(teamDto);
        Team updatedTeam = this.teamService.update(teamId, update);
        TeamDto updatedTeamDto = this.teamToTeamDtoConverter.convert(updatedTeam);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedTeamDto);
    }

    @DeleteMapping("/{teamId}")
    public Result deleteTeam(@PathVariable Integer teamId) {
        this.teamService.delete(teamId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
