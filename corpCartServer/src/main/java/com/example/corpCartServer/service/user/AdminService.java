package com.example.corpCartServer.service.user;

import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public User createAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

}