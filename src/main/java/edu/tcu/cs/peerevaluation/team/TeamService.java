package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.war.WAR;
import edu.tcu.cs.peerevaluation.war.WARRepository;
import edu.tcu.cs.peerevaluation.war.WARService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    private final StudentRepository studentRepository;

    private final SectionRepository sectionRepository;

    private final InstructorRepository instructorRepository;

    private final WARRepository warRepository;

    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository, SectionRepository sectionRepository, InstructorRepository instructorRepository, WARRepository warRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
        this.warRepository = warRepository;
    }

    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    public Team findById(int id) {
        return this.teamRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("team", id));
    }

    public Team save(Team team) {
        return this.teamRepository.save(team);
    }

    public Team update(Integer id, Team team) {
        return this.teamRepository.findById(id)
                .map(oldTeam -> {
                    oldTeam.setTeamName(team.getTeamName());
                    oldTeam.setAcademicYear(team.getAcademicYear());
                    oldTeam.setStudents(team.getStudents());
                    oldTeam.setInstructor(team.getInstructor());
                    return this.teamRepository.save(oldTeam);
                })
                .orElseThrow(() -> new ObjectNotFoundException("team", id));
    }

    public void delete(Integer id) {
        Team teamToBeDeleted = this.teamRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("team", id));

        teamToBeDeleted.removeAllStudents();
        teamToBeDeleted.removeInstructor();
        teamToBeDeleted.removeSectionFromTeam();
        this.deleteWARs(teamToBeDeleted);

        this.teamRepository.deleteById(id);
    }

    private void deleteWARs(Team team) {
        List<WAR> wars = team.getWars();

        wars.forEach((war) -> {
            this.warRepository.deleteById(war.getId());
        });
    }
}
