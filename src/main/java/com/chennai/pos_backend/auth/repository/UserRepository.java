package com.chennai.pos_backend.auth.repository;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    List<User> findByRole(Role role);
}
