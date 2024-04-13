package edu.tcu.cs.peerevaluation.security;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserToUserDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

  private final JwtProvider jwtProvider;

  private final UserToUserDtoConverter userToUserDtoConverter;

  public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
    this.jwtProvider = jwtProvider;
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

  public Map<String, Object> createLoginInfo(Authentication authentication) {
    // Create user info.
    MyUserPrincipal principal = (MyUserPrincipal)authentication.getPrincipal();
    PeerEvalUser peerEvalUser = principal.getPeerEvalUser();
    UserDto userDto = this.userToUserDtoConverter.convert(peerEvalUser);
    // Create a JWT.
    String token = this.jwtProvider.createToken(authentication);

    Map<String, Object> loginResultMap = new HashMap<>();

    loginResultMap.put("userInfo", userDto);
    loginResultMap.put("token", token);

    return loginResultMap;
}

}
