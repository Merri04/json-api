package com.example.demo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/*
 *
 * @Author Elise Strand Bråtveit
 * @Version 10.03.2025
 */
/*
 * Denne klassen håndterer forespørsel relatert til bruker.
 * Den gir en API-endepunkt for å hente informasjon om den nåværende brukeren.
 */
@RestController
@RequestMapping("/api/user")
public class UserController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/current")
    public Map<String, String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error(" Ingen bruker er autentisert!");
            throw new IllegalStateException("Ingen bruker er logget inn.");
        }
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("fodselsnummer", authentication.getName());
        return userInfo;
    }
}
