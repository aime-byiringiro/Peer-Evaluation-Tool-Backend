package edu.tcu.cs.peerevaluation.peerEvalUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class MyUserPrincipal implements UserDetails {

  private final PeerEvalUser peerEvalUser;

  public MyUserPrincipal(PeerEvalUser peerEvalUser) {
    this.peerEvalUser = peerEvalUser;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(StringUtils.tokenizeToStringArray(this.peerEvalUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
  }

  @Override
  public String getPassword() {
    return this.peerEvalUser.getPassword();
  }

  @Override
  public String getUsername() {
    return this.peerEvalUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
   return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.peerEvalUser.isEnabled();
  }

  public PeerEvalUser getPeerEvalUser() {
    return peerEvalUser;
  }


}
