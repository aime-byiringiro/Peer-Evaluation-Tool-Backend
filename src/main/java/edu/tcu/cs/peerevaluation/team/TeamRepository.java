package edu.tcu.cs.peerevaluation.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @PostMapping
    public Result createNewTeam(@RequestBody TeamDto teamDto){
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return  new Result(true, StatusCode.SUCCESS,"Create Success", savedTeamDto );
    }

}
