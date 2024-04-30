package org.example.userservice.controllers;
import org.example.userservice.dtos.LoginRequestDto;
import org.example.userservice.dtos.SignUpRequestDto;
import org.example.userservice.dtos.UserDto;
import org.example.userservice.exception.IncorrectPasswordException;
import org.example.userservice.exception.MailAlreadyExistException;
import org.example.userservice.exception.TokenNotFoundException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.model.Token;
import org.example.userservice.model.User;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException, IncorrectPasswordException {
        Token token = (userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        return new ResponseEntity<>(token.getValue(), HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws MailAlreadyExistException {
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(),
                                        signUpRequestDto.getPassword());
        if(user==null){return new ResponseEntity<>("Signup Unsuccessful", HttpStatus.NOT_ACCEPTABLE);}
        else{return new ResponseEntity<>("Signup successful", HttpStatus.OK);}

    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<String> logout(@PathVariable("token") String token) throws TokenNotFoundException {
        System.out.println("controller logout");
        if(userService.logout(token)){
            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Logout Unsuccessful", HttpStatus.OK);
        }
    }
    @PostMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable("token") String token) throws TokenNotFoundException {
        User user = userService.validateToken(token);
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setEmailVerified(user.isEmailVerified());
        userDto.setRoles(user.getRoles());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }



}
