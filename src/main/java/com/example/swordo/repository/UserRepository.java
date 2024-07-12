package com.example.swordo.repository;

import com.example.swordo.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
