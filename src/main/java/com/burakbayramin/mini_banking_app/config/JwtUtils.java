package com.burakbayramin.mini_banking_app.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    /**
     * Generates the signing key for JWT using the secret key.
     * The key is generated using the HMAC-SHA algorithm.
     *
     * @return The signing Key object
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token based on the provided user details.
     *
     * @param userDetails The user details to include in the token
     * @return A compact JWT token as a String
     */
    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                // Sets the subject of the JWT to the username of the user
                .setSubject((userDetails.getUsername()))
                // Sets the issue date of the JWT to the current date
                .setIssuedAt(new Date())
                // Sets the expiration date of the JWT based on the current time and expiration duration
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                // Signs the JWT with the signing key and specifies the signature algorithm
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                // Builds the JWT and serializes it to a compact, URL-safe string
                .compact();
    }

    /**
     * Extracts the username (subject) from a given JWT token.
     *
     * @param token The JWT token from which to extract the username
     * @return The username contained within the JWT
     */
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                // Sets the signing key to validate the JWT's signature
                .setSigningKey(getSigningKey())
                .build()
                // Parses the JWT and retrieves the claims
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param authToken The JWT token to validate
     * @return True if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Parses the JWT to ensure it's correctly signed and not expired
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            // Logs an error message if the token is invalid
            System.err.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}

