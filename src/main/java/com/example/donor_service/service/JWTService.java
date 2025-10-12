package com.example.donor_service.service;

import com.example.donor_service.config.BloodBankUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
@Service
public class JWTService {
    @Value("${jwt-secret}")
    public static String SECRET;

    /**
     * Generate the new token by providing the email
     */
    public String generateToken(String email){
        HashMap<String, Object> claim = new HashMap<>();
        return createToken(claim,email);
    }

    /**
     * Create the token helps to set claim, email, subject, issued date, expiration date and algorithm
     * */
    private String createToken(HashMap<String, Object> claim, String email) {
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 30 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiryDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean tokenExpiry(String token){
        return extractExpiryDate(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String extUsername = extractUsername(token);
        return (extUsername.equals(userDetails.getUsername()) && !tokenExpiry(token));
    }
}
