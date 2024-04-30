package org.example.userservice.repository;

import org.example.userservice.model.Token;
import org.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String token);
    Token save(Token token);
    Optional<Token> findByUser(User user);

    Optional<Token> findByValueAndIfDeletedAndExpiryAtGreaterThan(String value, boolean deleted, Date expiryAt);

}
