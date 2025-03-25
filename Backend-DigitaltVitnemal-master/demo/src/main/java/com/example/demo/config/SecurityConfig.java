package com.example.demo.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import org.springframework.security.web.SecurityFilterChain;



/**
 *
 * @Author Elise Strand Bråtveit
 * @Version 22.01.2025
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService studentService;

    @Autowired
    public SecurityConfig(@Lazy UserDetailsService studentService){
        this.studentService = studentService;
    }



@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for the application
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**", "/auth/login", "/api/diplomas/my-diplomas/*").permitAll()
                    .anyRequest().authenticated()
            )
            .headers(headers -> headers
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )
            .formLogin(form -> form
                    .defaultSuccessUrl("/home.html", true) // ✅ Redirect to home.html after login
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home.html") // ✅ Redirect to home page after logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
            )
            .httpBasic(Customizer.withDefaults()) // Enable basic auth
            .build();
}



// validering av password ved innlogging

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(studentService);
        return provider;
    }

    @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
