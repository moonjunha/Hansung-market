package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByAuthToken(String token);

    Optional<User> findByUsernameAndEmail(String username, String email);
}
