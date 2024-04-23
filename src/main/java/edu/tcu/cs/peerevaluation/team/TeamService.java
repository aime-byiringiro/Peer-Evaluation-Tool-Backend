package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    private final StudentRepository studentRepository;

    private final SectionRepository sectionRepository;

    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository, SectionRepository sectionRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

//    public List<Team> searchTeams() {
//
//    }


}
