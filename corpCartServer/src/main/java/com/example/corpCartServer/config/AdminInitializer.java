package com.example.corpCartServer.config;

import com.example.corpCartServer.constants.Role;
import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.corpCartServer.utils.AppConstants.BCRYPT_PASS_STRENGTH;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepo adminRepo;
    private final String adminEmail;
    private final String adminPassword;
    private final BCryptPasswordEncoder encoder;

    public AdminInitializer(AdminRepo adminRepo, @Value("${ADMIN_EMAIL}") String adminEmail, @Value("${ADMIN_PASSWORD}") String adminPassword) {
        this.adminRepo = adminRepo;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        encoder = new BCryptPasswordEncoder(BCRYPT_PASS_STRENGTH);
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminRepo.findByEmail(adminEmail) == null) {
            Admin admin = new Admin();
            admin.setName("Mohan");
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setSecurityKey("KingKong");
            adminRepo.save(admin);
        }
    }
}
