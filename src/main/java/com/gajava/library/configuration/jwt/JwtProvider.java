package com.gajava.library.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    private final Integer EXPIRATION_DAYS_COUNT = 15;

    public String generateToken(final String login) {
        Date date = Date.from(LocalDate
                .now()
                .plusDays(EXPIRATION_DAYS_COUNT)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
        );

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //TODO: clean it
//    public boolean validateToken(final String token) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException expEx) {
//            throw new InvalidTokenException("Token expired");
//        } catch (UnsupportedJwtException unsEx) {
//            throw new InvalidTokenException("Unsupported jwt");
//        } catch (MalformedJwtException mjEx) {
//            throw new InvalidTokenException("Malformed jwt");
//        } catch (SignatureException sEx) {
//            throw new InvalidTokenException("Invalid signature");
//        } catch (Exception e) {
//            throw new InvalidTokenException("Invalid token");
//        }
//    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //TODO: create logs
            //log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(final String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}