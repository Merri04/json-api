package com.example.demo.services;


import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.DTO.StudentVitnemaalDTO;

import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 28.01.2025*/

@Service
public class JWTService {


    public String secretkey;


    public  JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public String generateToken(String fodselsnummer) {
        Map<String, Object> clamis = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(clamis)
                .subject(fodselsnummer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .and()
                .signWith(getKey())
                .compact();

    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // Henter "sub" fra token
    }
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            System.out.println("Token er gyldig. Bruker: " + claims.getSubject());
            return true;
        } catch (Exception e) {
            System.out.println("Token er ugyldig: " + e.getMessage());
            return false;
        }
    }
}

