package com.jayesh.resourcePrj.security;

import com.jayesh.resourcePrj.entities.Employee;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secrete.key}")
    private String jwtSecretKey;

    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getKey())
                .compact();
        log.info("Generated JWT token: {}", token);
        return token;
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken =  request.getHeader("Authorization");
        log.info("Bearer Token: {}", bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public Boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        }
        catch (ExpiredJwtException e)
        {
            System.out.println("Token Expired "+e.getMessage());
            return false;
        }
        catch (SignatureException e)
        {
            System.out.println("Invalid JWT Signature "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            System.out.println("JWT exception "+e.getMessage());
            return false;
        }
    }
}
