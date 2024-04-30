package org.example.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private List<CustomGrantedAuthorities> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public CustomUserDetails(){}
    public CustomUserDetails(User user){
        ArrayList<CustomGrantedAuthorities> grantedAuthorities = new ArrayList<>();
        for (Role role: user.getRoles()) {
            grantedAuthorities.add(new CustomGrantedAuthorities(role));
        }
        this.authorities = grantedAuthorities;
        this.password = user.getHashedPassword();
        this.username = user.getEmail();
        this.name = user.getName();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
