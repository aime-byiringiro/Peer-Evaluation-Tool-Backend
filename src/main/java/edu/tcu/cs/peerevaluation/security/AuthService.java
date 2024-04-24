package edu.tcu.cs.peerevaluation.security;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.converter.UserToUserDtoConverter;
import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.student.Student;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

  private final JwtProvider jwtProvider;

  private final UserToUserDtoConverter userToUserDtoConverter;

  private final UserRepository userRepository;

  public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter, UserRepository userRepository) {
    this.jwtProvider = jwtProvider;
    this.userToUserDtoConverter = userToUserDtoConverter;
    this.userRepository = userRepository;
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

  public Student getLoggedInStudent() throws UsernameNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
      return principal.getPeerEvalUser().getStudent();
    } else {
      JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext()
          .getAuthentication();
      Jwt jwt = (Jwt) authenticationToken.getCredentials();
      PeerEvalUser user = this.userRepository.findByUsername(jwt.getSubject())
          .orElseThrow(() -> new UsernameNotFoundException("username " + jwt.getSubject() + " is not found."));
      return user.getStudent();
    }
  }

}
