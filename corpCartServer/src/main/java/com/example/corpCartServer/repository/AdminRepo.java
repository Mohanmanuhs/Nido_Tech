package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {

    User findByEmail(String email);
}