package edu.tcu.cs.peerevaluation.instructor;

import java.io.Serializable;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private PeerEvalUser user;


    // TODO assign istructor to section or team

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

    public PeerEvalUser getUser() {
        return user;
    }

    public void setUser(PeerEvalUser user) {
        this.user = user;
    }

    

}
