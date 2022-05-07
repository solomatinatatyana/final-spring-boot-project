package ru.otus.finalproject.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class SecurityUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    private boolean active;

    public SecurityUserDetails(String username, String password, Set<GrantedAuthority> authorities, boolean active) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.active = active;
    }

    public SecurityUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return active;
    }
}
