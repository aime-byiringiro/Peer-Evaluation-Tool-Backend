package edu.tcu.cs.peerevaluation.team;

import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.war.WAR;
import edu.tcu.cs.peerevaluation.war.WARRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public List<Team> searchTeams(String teamName, String sectionName, String academicYear, String instructorFirstName,
                                  String instructorLastName) {
        Specification<Team> spec = Specification.where(null);

        if (teamName != null && !teamName.isEmpty()) {
            spec = spec.and(TeamSpecifications.hasTeamName(teamName));
        }

        if (sectionName != null && !sectionName.isEmpty()) {
            spec = spec.and(TeamSpecifications.hasSectionName(sectionName));
        }

        if (academicYear != null && !academicYear.isEmpty()) {
            spec = spec.and(TeamSpecifications.hasAcademicYear(academicYear));
        }

        if (instructorFirstName != null && !instructorFirstName.isEmpty()) {
            spec = spec.and(TeamSpecifications.hasInstructorFirstName(instructorFirstName));
        }

        if (instructorLastName != null && !instructorLastName.isEmpty()) {
            spec = spec.and(TeamSpecifications.hasInstructorLastName(instructorLastName));
        }

        return teamRepository.findAll(spec);
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
