package edu.tcu.cs.peerevaluation.peerEvalUser.converter;


import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<PeerEvalUser, UserDto> {

    private final StudentToStudentDtoConverter studentToStudentDtoConverter;

    public UserToUserDtoConverter(StudentToStudentDtoConverter studentToStudentDtoConverter) {
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    }

    @Override
    public UserDto convert(PeerEvalUser source) {
        // We are not setting password in DTO.
        final UserDto userDto = new UserDto(source.getId(),
                                            source.getUsername(),
                                            source.isEnabled(),
                                            source.getRoles(),
                                            source.getStudent() != null
                                                    ? this.studentToStudentDtoConverter.convert(source.getStudent())
                                                    : null);
        return userDto;
    }

}
