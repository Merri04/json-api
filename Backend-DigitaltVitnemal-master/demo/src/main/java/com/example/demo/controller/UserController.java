package com.example.demo.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/current")
    public Map<String, String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("🚨 Ingen bruker er autentisert!");
            throw new IllegalStateException("Ingen bruker er logget inn.");
        }

        String username = authentication.getName();
        logger.info("✅ Pålogget bruker: {}", username); // LOGGING 🚀

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("fodselsnummer", authentication.getName()); // Spring Security returnerer vanligvis username her

        return userInfo;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Tømmer session
        }
        return ResponseEntity.ok("Logged out successfully");
    }

}
