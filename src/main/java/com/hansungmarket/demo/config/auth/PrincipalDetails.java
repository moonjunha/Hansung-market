package com.hansungmarket.demo.config.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class PrincipalDetails implements UserDetails {
    private final Long userId;
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final Boolean enabled;
    private final Collection<GrantedAuthority> authorities;

    @Builder
    private PrincipalDetails(Long userId, String username, String password, String email, String nickname, Boolean enabled, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.enabled = enabled;
        this.authorities = authorities;
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
        return enabled;
    }
}
