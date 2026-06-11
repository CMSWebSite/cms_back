package cms_back.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessTokenExpMin;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-exp-min}") long accessTokenExpMin
    ) {
        // secret은 최소 32자 이상 권장 (짧으면 런타임에서 예외 발생 가능)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpMin = accessTokenExpMin;
    }

    public TokenWithExpiry createAccessToken(String subjectEmail, String role) {
        Instant now = Instant.now();
        Instant exp = now.plus(accessTokenExpMin, ChronoUnit.MINUTES);

        String token = Jwts.builder()
                .subject(subjectEmail)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("role", role)
                .signWith(key)
                .compact();

        return new TokenWithExpiry(token, exp);
    }

    public void validateOrThrow(String token) {
        // 유효하면 예외 없음, 유효하지 않으면 예외 발생
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getRole(String token) {
        Object role = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
        return role == null ? null : role.toString();
    }

    public record TokenWithExpiry(String token, Instant expiresAt) {}
}
