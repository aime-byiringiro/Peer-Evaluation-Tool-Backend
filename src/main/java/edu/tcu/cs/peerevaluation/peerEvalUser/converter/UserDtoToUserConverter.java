package edu.tcu.cs.peerevaluation.peerEvalUser.converter;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, PeerEvalUser> {

    @Override
    public PeerEvalUser convert(UserDto source) {
      PeerEvalUser peerEvalUser = new PeerEvalUser();
      peerEvalUser.setUsername(source.username());
      peerEvalUser.setEnabled(source.enabled());
      peerEvalUser.setRoles(source.roles());
      return peerEvalUser;
    }

}
