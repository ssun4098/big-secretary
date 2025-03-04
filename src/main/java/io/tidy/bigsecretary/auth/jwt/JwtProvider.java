package io.tidy.bigsecretary.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

  private final Key secretKey;
  private static String USER_ID = "user";

  public JwtProvider(@Value("${login.key}") String key) {
    this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
  }

  public String createToken(String uuid, Long expired) {
    return Jwts.builder()
        .claim(USER_ID, uuid)
        .issuedAt(getIssuedDate())
        .expiration(createTokenExpiredDate(expired))
        .signWith(secretKey)
        .compact();
  }

  public String getUuidByToken(String token) {
    String id = null;
    try {
      Jws<Claims> claims =
              Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
      id = claims.getPayload().get(USER_ID, String.class);
    } catch (JwtException e) {
      log.error("JWT Validation ERROR: {}", e.getMessage());
    }
    return id;
  }

  private Date getIssuedDate() {
    return new Date();
  }

  private Date createTokenExpiredDate(Long expired) {
    Date date = new Date();
    date.setTime(expired + date.getTime());
    return date;
  }
}
