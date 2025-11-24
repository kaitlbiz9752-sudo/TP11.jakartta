package ma.ens.security.spring_jwt_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "MySuperSecretKeyForJwtAuthentication123456";
    private final long expiration = 3600000L; // 1h

    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    // Génération du token
    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    // Extraction username
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (JwtException e) {
            System.out.println("Invalid JWT in extractUsername: " + e.getMessage());
            return null;
        }
    }

    // Validation token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT in validateToken: " + e.getMessage());
            return false;
        }
    }
}
