package com.formal.math.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {
  private String email;
  private String name;
  private boolean del;
  private Map<String, Object> attr;
  public AuthMemberDTO(
          String username,
          String password,
          String name,
          boolean del,
          Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.email = username;
    this.name = name;
    this.del = del;
  }
  public AuthMemberDTO(
          String email,
          String password,
          String name,
          boolean del,
          Collection<? extends GrantedAuthority> authorities,
          Map<String, Object> attr) {
    this(email, password, name, del, authorities);
    this.attr = attr;
  }
  @Override
  public Map<String, Object> getAttributes() {
    return this.attr;
  }
}
