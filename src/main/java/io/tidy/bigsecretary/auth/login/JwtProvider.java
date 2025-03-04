package io.tidy.bigsecretary.auth.login;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtProvider {


    private final Key secretKey;
    private final Long expired;
    private static String USER_ID = "userId";

    public JwtProvider(@Value("${login.key}") String key, @Value("${login.expired}") Long expired) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.expired = expired;
    }

    public String createToken(String uuid) {
        return Jwts.builder()
            .claim(USER_ID, uuid)
            .issuedAt(getIssuedDate())
            .expiration(createTokenExpiredDate())
            .signWith(secretKey)
            .compact();
    }

    public String getUuidByToken(String token) {
        Jws<Claims> claims  = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload().get(USER_ID, String.class);
    }

    private Date getIssuedDate() {
        return new Date();
    }

    private Date createTokenExpiredDate() {
        Date date = new Date();
        date.setTime(expired + date.getTime());
        return date;
    }
}
