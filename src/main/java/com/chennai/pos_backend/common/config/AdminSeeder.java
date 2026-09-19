package com.chennai.pos_backend.common.config;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String adminUsername = "admin";
        String adminEmail = "admin@123.com";

        if (userRepository.existsByUsername(adminUsername)) {
            System.out.println("Admin user already exists.");
            return;
        }

        User admin = new User();

        admin.setUsername(adminUsername);
        admin.setFullName("System Administrator");
        admin.setEmail(adminEmail);
        admin.setPhone("9999999999");

        admin.setPasswordHash(
                passwordEncoder.encode("admin@123")
        );

        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        userRepository.save(admin);
    }
}
