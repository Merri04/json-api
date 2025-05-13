package com.example.demo.service;

import com.example.demo.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceTests {

    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
    }

    @Test
    void testGenerateToken() {
        // Arrange
        String fodselsnummer = "12345678910";

        // Act
        String token = jwtService.generateToken(fodselsnummer);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractClaim() {
        // Arrange
        String fodselsnummer = "12345678910";
        String token = jwtService.generateToken(fodselsnummer);

        // Act
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtService.secretkey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Assert
        assertNotNull(claims);
        assertEquals(fodselsnummer, claims.getSubject());
    }

    @Test
    void testTokenExpiration() {
        // Arrange
        String fodselsnummer = "12345678910";
        String token = jwtService.generateToken(fodselsnummer);

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtService.secretkey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Date expiration = claims.getExpiration();

        // Act
        boolean isExpired = expiration.before(new Date());

        // Assert
        assertFalse(isExpired);
    }

    @Test
    void testIsTokenExpired() throws InterruptedException {
        // Arrange
        String fodselsnummer = "12345678910";
        String token = jwtService.generateToken(fodselsnummer);

        // Simuler utløp av token (kan justeres hvis du setter kortere tid)
        Thread.sleep(100);

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtService.secretkey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        boolean isExpired = claims.getExpiration().before(new Date());

        // Assert
        assertFalse(isExpired);
    }

    @Test
    void testValidateToken() {
        // Arrange
        String fodselsnummer = "12345678910";
        String token = jwtService.generateToken(fodselsnummer);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(fodselsnummer);

        // Act
        boolean isValid = fodselsnummer.equals(userDetails.getUsername()) &&
                !Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtService.secretkey)))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration()
                        .before(new Date());

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.string";

        // Act & Assert
        assertThrows(Exception.class, () ->
                Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtService.secretkey)))
                        .build()
                        .parseSignedClaims(invalidToken)
                        .getPayload());
    }
}

