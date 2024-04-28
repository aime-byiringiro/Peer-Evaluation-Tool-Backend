package edu.tcu.cs.peerevaluation.instructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.team.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Instructor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "first name is required.")
    private String firstName;

    private String middleInitial;

    @NotEmpty(message = "last name is required.")
    private String lastName;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "instructor_teams") // Specifies the table that stores the team names
    @Column(name = "team_name") // Specifies the column that will store the team names
    private List<Team> teams;

    @OneToOne(mappedBy = "instructor", fetch = FetchType.EAGER)
    private PeerEvalUser user;

    public Instructor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> list) {
        this.teams = list;
    }

    public PeerEvalUser getUser() {
        return user;
    }

    public void setUser(PeerEvalUser user) {
        this.user = user;
    }

    public void assignInstructorToTeam(Team team) {
        if (teams == null) {
            teams = new ArrayList<>();
        }
        this.teams.add(team);
    }

    public void removeInstructorFromTeam(Team team) {
        if (teams != null) {
            this.teams.remove(team);
        }
    }

    public List<String> getTeamNamesList() {
        List<String> teamNames = new ArrayList<>();
        if(this.teams != null) {
            teamNames = teams.stream()
                .map(foundteam -> foundteam.getTeamName())
                .collect(Collectors.toList());
        }
        return teamNames;

    }

}
