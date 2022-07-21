package com.apirestful.eventos.repository;

import com.apirestful.eventos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u from User u JOIN FETCH u.roles where u.username = :username")
    User findByUsernameFetchRoles(@Param("username") String username);
}

