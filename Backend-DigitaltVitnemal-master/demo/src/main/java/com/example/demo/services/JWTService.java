package com.example.demo.services;


import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.DTO.StudentVitnemaalDTO;

import com.auth0.jwt.JWT;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 28.01.2025*/


public class JWTService {

    private StudentVitnemaalDTO studentVitnemaalDTO;
    private static final String SECRET_KEY = "MyKey";

    public JWTService (StudentVitnemaalDTO studentVitnemaalDTO){
        this.studentVitnemaalDTO = studentVitnemaalDTO;
    }

    public static String generateToken(String fodselsnummer){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(fodselsnummer)
                .sign(algorithm);

    }

    //For å sjekke om den ser riktig ut
    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

}