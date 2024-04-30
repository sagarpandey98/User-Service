package org.example.userservice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.exception.IncorrectPasswordException;
import org.example.userservice.exception.MailAlreadyExistException;
import org.example.userservice.exception.TokenNotFoundException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.model.Token;
import org.example.userservice.model.User;
import org.example.userservice.repository.TokenRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Token login(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with email '" + email + "' not found");
        }
        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        Optional<Token> optionalToken = tokenRepository.findByUser(user);
        if(optionalToken.isPresent()){
            optionalToken.get().setValue(optionalToken.get().getValue() + "-> (already exists)");
            return optionalToken.get();
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime oneMonthAfter = currentTime.plusMonths(1);
        Date oneMonthAfterDate = Date.from(oneMonthAfter.atZone(ZoneId.systemDefault()).toInstant());


        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(oneMonthAfterDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        return tokenRepository.save(token);
    }

    public User signUp(String name, String email, String password) throws MailAlreadyExistException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new MailAlreadyExistException(email + " already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);

    }

    public boolean logout(String tokenValue) throws TokenNotFoundException {
        Optional<Token> optionalToken = tokenRepository.findByValue(tokenValue);
        if (optionalToken.isEmpty()) {
            throw new TokenNotFoundException("Token doesnt exists");
        }
        Token token = optionalToken.get();
        token.setIfDeleted(true);
        tokenRepository.save(token);
        return true;

    }

    public User validateToken(String token) throws TokenNotFoundException {
        Optional<Token> optionalToken = tokenRepository.
                findByValueAndIfDeletedAndExpiryAtGreaterThan
                        (token, false, new Date());
        if (optionalToken.isEmpty()){
            throw new TokenNotFoundException("Token doesnt Exist");
        }
        return optionalToken.get().getUser();

    }
}


