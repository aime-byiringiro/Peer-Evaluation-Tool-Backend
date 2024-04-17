package edu.tcu.cs.peerevaluation.peerEvalUser.converter;


import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<PeerEvalUser, UserDto> {

    @Override
    public UserDto convert(PeerEvalUser source) {
        // We are not setting password in DTO.
        final UserDto userDto = new UserDto(source.getId(),
                                            source.getUsername(),
                                            source.getPassword(),
                                            source.isEnabled(),
                                            source.getRoles());
        return userDto;
    }

}
