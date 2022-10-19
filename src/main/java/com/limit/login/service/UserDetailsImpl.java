package com.limit.login.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limit.login.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private final boolean accountNonLocked;
    private Long id;

    private String username;

    private String email;

    private Boolean isEnabled;

    @JsonIgnore
    private String password;

    private User user;

    public UserDetailsImpl(Long id, String username, String password,
                           Boolean isEnabled, boolean accountNonLocked, User user) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.accountNonLocked = accountNonLocked;
        this.user = user;
    }
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
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
