package com.ra.md05ss09bt.security.jwt;

import com.ra.md05ss09bt.security.authentication.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${expired}")
    private Long EXPIRED;
    @Value("${secret_key}")
    private String SECRET_KEY;
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(UserDetailsImpl userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Failed -> Invalid Token Format {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Failed -> Unsupported Token Format {}", e.getMessage());

        } catch (ExpiredJwtException e) {
            logger.error("Failed -> Expired Token {}", e.getMessage());

        } catch (SignatureException e) {
            logger.error("Failed -> Invalid Token Signature {}", e.getMessage());

        } catch (IllegalArgumentException e) {
            logger.error("Failed -> Empty Token {}", e.getMessage());

        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}
