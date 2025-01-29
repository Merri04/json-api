package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @Author Elise Strand Bråtveit
 * @Version 22.01.2025
 */

public class StudentPrincipal implements UserDetails {

    private final Student student;

    public StudentPrincipal(Student student) {

        this.student = student;
    }
    public Long getStudentId() {
        return student.getStudentId();
    }
    public String getNavn() {
        return student.getNavn();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return student.getPassword();

    }

    @Override
    public String getUsername() {
        return student.getStudentnummer();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
