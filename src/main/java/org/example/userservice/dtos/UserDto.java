package org.example.userservice.dtos;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private boolean isEmailVerified;
}
