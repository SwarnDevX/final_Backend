package com.buildledger.iam.bootstrap;

import com.buildledger.iam.entity.User;
import com.buildledger.iam.enums.Role;
import com.buildledger.iam.enums.UserStatus;
import com.buildledger.iam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("Admin@1234"))
                .name("System Administrator")
                .role(Role.ADMIN)
                .email("admin@buildledger.com")
                .status(UserStatus.ACTIVE)
                .build();
            userRepository.save(admin);
            log.info("Default admin user created: username=admin, password=Admin@1234");
        } else {
            log.info("Admin user already exists, skipping bootstrap.");
        }
    }
}

