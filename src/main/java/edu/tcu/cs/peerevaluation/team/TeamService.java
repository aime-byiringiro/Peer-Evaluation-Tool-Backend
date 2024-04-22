package edu.tcu.cs.peerevaluation.team;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team save (Team newTeam){
       return this.teamRepository.save(newTeam);
    }
}
