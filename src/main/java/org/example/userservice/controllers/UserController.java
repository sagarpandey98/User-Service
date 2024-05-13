package org.example.userservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.userservice.dtos.LoginRequestDto;
import org.example.userservice.dtos.SendEmailEventDto;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public UserController(UserService userService, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper){
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException, IncorrectPasswordException {
        Token token = (userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        return new ResponseEntity<>(token.getValue(), HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws MailAlreadyExistException, JsonProcessingException {
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(),
                                        signUpRequestDto.getPassword());
        if(user==null){return new ResponseEntity<>("Signup Unsuccessful", HttpStatus.NOT_ACCEPTABLE);}
        else{
            SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
            sendEmailEventDto.setTo(signUpRequestDto.getEmail());
            sendEmailEventDto.setFrom("sagarbvmdelhi@gmail.com");
            sendEmailEventDto.setSubject("Welcome to UserProductNexus");
            sendEmailEventDto.setBody("Welcome to UserProductNexus. Your account has been created successfully.");

            kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(sendEmailEventDto));
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);}


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
