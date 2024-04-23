package edu.tcu.cs.peerevaluation.instructor;

import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import jakarta.validation.Valid;

public class InstructorUserCombined {

    @Valid
    private InstructorDto instructorDto;

    private PeerEvalUser user;

    public InstructorUserCombined() {
    }

    public InstructorUserCombined(InstructorDto instructorDto, PeerEvalUser user) {
        this.instructorDto = instructorDto;
        this.user = user;
    }

    public InstructorDto getInstructorDto() {
        return instructorDto;
    }

    public void setInstructorDto(InstructorDto instructorDto) {
        this.instructorDto = instructorDto;
    }

    public PeerEvalUser getUser() {
        return user;
    }

    public void setUser(PeerEvalUser user) {
        this.user = user;
    }
}
