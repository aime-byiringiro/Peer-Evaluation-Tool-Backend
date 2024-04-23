package edu.tcu.cs.peerevaluation.team.converter;

import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {

    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;
    private final StudentService studentService;

    public TeamDtoToTeamConverter(SectionDtoToSectionConverter sectionDtoToSectionConverter, StudentService studentService) {
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
        this.studentService = studentService;
    }

    @Override
    public Team convert(TeamDto source) {

        // convert ids to Students
        List<Student> students = new ArrayList<>();
        for (Integer id : source.studentIds()) {
            Student s = studentService.findById(id);
            System.out.println(s.getFirstAndLastName());

            students.add(studentService.findById(id));
        }

        Team team = new Team();
        team.setId(source.id());
        team.setTeamName(source.teamName());
        team.setSection(this.sectionDtoToSectionConverter.convert(source.section()));
        team.setStudents(students);
        return team;
    }
}
