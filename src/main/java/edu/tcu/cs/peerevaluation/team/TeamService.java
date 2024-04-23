package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
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

    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository, SectionRepository sectionRepository, InstructorRepository instructorRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
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
        teamToBeDeleted.removeAllStudentsFromTeam();
        teamToBeDeleted.removeInstructorFromTeam();
        teamToBeDeleted.removeSectionFromTeam();
        this.teamRepository.delete(teamToBeDeleted);

    }
}
