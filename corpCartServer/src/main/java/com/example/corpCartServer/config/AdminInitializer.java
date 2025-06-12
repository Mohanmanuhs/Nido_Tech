package com.example.corpCartServer.config;

import com.example.corpCartServer.constants.Role;
import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.corpCartServer.utils.AppConstants.BCRYPT_PASS_STRENGTH;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCRYPT_PASS_STRENGTH);

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepo.findByEmail(adminEmail)==null) {;
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            adminRepo.save(admin);
        }
    }
}
