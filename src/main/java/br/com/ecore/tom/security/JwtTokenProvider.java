package br.com.ecore.tom.security;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import br.com.ecore.tom.authentication.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationInMs}")
  private int jwtExpirationInMs;

  public String generateToken(Authentication authentication) {
    User userPrincipal = (User) authentication.getPrincipal();

    OffsetDateTime expiryDate = OffsetDateTime.now().plus(jwtExpirationInMs, ChronoUnit.MILLIS);

    Map<String, Object> claims = new HashMap<>();

    claims.put("id", userPrincipal.getId());
    claims.put("nome", userPrincipal.getNome());
    claims.put("login", userPrincipal.getLogin());
    claims.put("email", userPrincipal.getEmail());

    return Jwts.builder().setSubject(userPrincipal.getNome()).setClaims(claims)
        .setIssuedAt(Date.from(OffsetDateTime.now().toInstant()))
        .setExpiration(Date.from(expiryDate.toInstant()))
        .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

  }

  String getUsernameFromJwt(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    return String.valueOf(claims.get("login"));
  }

  boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException
        | IllegalArgumentException | SignatureException e) {
      e.getMessage();
    }
    return false;
  }

}
