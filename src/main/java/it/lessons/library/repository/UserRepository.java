package it.lessons.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.library.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    public Optional<User> findByUsername(String username);
}
