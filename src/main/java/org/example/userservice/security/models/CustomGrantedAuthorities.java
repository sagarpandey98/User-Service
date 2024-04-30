package org.example.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.userservice.model.Role;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
public class CustomGrantedAuthorities implements GrantedAuthority{
    private String authority;
    public CustomGrantedAuthorities(){}
    public CustomGrantedAuthorities(Role role){
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
