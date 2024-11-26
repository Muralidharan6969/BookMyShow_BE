package com.example.bookmyshow_be.Utils;

import com.example.bookmyshow_be.Models.AdminTokenPayload;
import com.example.bookmyshow_be.Models.OutletTokenPayload;
import com.example.bookmyshow_be.Models.UserTokenPayload;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private String jwtExpirationInMs;

    public String generateUserToken(UserTokenPayload payload){
        long expirationInMs = Long.parseLong(jwtExpirationInMs);

        // Calculate expiration time using java.time.Instant
        Instant expirationInstant = Instant.now().plusMillis(expirationInMs);
        Date expirationDate = Date.from(expirationInstant); // Convert to Date object

        System.out.println("User ID: " + payload.getUserId());
        System.out.println("Name: " + payload.getName());
        System.out.println("Role: " + payload.getRole());
        System.out.println("Expiration in ms: " + jwtExpirationInMs);
        System.out.println("Issued At: " + new Date());
        System.out.println("Current Time: " + System.currentTimeMillis());
        System.out.println("Expiration At: " + expirationDate);

        return Jwts.builder()
                .setSubject(payload.getUserId().toString())
                .claim("name", payload.getName())
                .claim("role", payload.getRole())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateOutletToken(OutletTokenPayload payload){
        return Jwts.builder()
                .setSubject(payload.getOutletId().toString())
                .claim("outletName", payload.getOutletOwnershipName())
                .claim("role", payload.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateAdminToken(AdminTokenPayload payload){
        return Jwts.builder()
                .setSubject(payload.getAdminId().toString())
                .claim("registrationId", payload.getRegistrationId())
                .claim("role", payload.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Generic Method to Extract Claims
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Specific Method: Extract User ID
    public String extractEntityId(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Specific Method: Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
