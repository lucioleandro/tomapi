package br.com.ecore.tom.authentication.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.authentication.domain.LoginDTO;
import br.com.ecore.tom.security.JwtAuthenticationResponse;
import br.com.ecore.tom.security.JwtTokenProvider;

@RequestMapping("/authentication")
@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider tokenProvider;

  @PostMapping("/sigin")
  public ResponseEntity<JwtAuthenticationResponse> authUser(@Valid @RequestBody LoginDTO request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);

    HttpHeaders headers = new HttpHeaders();

    headers.add("x-access-token", jwt);

    List<String> allowedHeaders = new ArrayList<>();

    allowedHeaders.add("x-access-token");

    headers.setAccessControlAllowHeaders(allowedHeaders);

    return ResponseEntity.ok().headers(headers).body(new JwtAuthenticationResponse(jwt));
  }

}
