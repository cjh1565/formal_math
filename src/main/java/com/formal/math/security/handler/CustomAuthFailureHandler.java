package com.formal.math.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
    throws IOException, ServletException {
    String errorMessage;

    if(exception instanceof BadCredentialsException) {
      errorMessage = "The given email address or password is not correct!";
    } else if (exception instanceof InternalAuthenticationServiceException) {
      errorMessage = "The login request could not be processed due to an internal system issue. Contact your administrator.";
//    } else if (exception instanceof UsernameNotFoundException) {
//      errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
    } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
      errorMessage = "Authentication request denied. Contact your administrator.";
    } else {
      errorMessage = "The login request could not be processed with an unknown error. Contact your administrator.";
    }

    errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
    setDefaultFailureUrl("/member/prelogin?msg="+errorMessage);
    super.onAuthenticationFailure(request, response, exception);
  }
}
