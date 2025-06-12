package com.example.corpCartServer.service.user;

import com.example.corpCartServer.constants.Role;
import com.example.corpCartServer.dto.AdminLoginRequest;
import com.example.corpCartServer.dto.UserRegisterRequest;
import com.example.corpCartServer.exception.SecurityKeyNotMatchingException;
import com.example.corpCartServer.exception.UserAlreadyExistsException;
import com.example.corpCartServer.exception.UserNotActiveException;
import com.example.corpCartServer.mapper.UserMapper;
import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.AdminRepo;
import com.example.corpCartServer.service.auth.JwtService;
import com.example.corpCartServer.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.corpCartServer.utils.AppConstants.BCRYPT_PASS_STRENGTH;


@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCRYPT_PASS_STRENGTH);

    public void registerAdmin(UserRegisterRequest userRequest, UserDetails userDetails){
        if (findByEmail(userRequest.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + userRequest.getEmail());
        }

        if (userRequest.getRole() == null) {
            throw new IllegalArgumentException("User role must be provided");
        }

        switch (userRequest.getRole()) {
            case CUSTOMER -> throw new IllegalArgumentException("admin can register only new admin");
            case ADMIN -> createAdmin(userRequest,userDetails);
            default -> throw new IllegalArgumentException("Invalid role: " + userRequest.getRole());
        }
    }

    private void createAdmin(UserRegisterRequest userRequest,UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = findByEmail(email);
        Admin oldAdmin = (Admin) user;
        if(!userRequest.getSecurityKey().equals(oldAdmin.getSecurityKey())){
            throw new SecurityKeyNotMatchingException("enter security key correctly");
        }
        Admin admin = UserMapper.getAdmin(userRequest);
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminRepo.save(admin);
    }

    public void loginAdmin(AdminLoginRequest userRequest, HttpServletResponse response) {
        User user = findByEmail(userRequest.getEmail());

        if (user == null || !user.isActive()) {
            throw new UserNotActiveException("User not found or inactive");
        }

        if(user.getRole()!= Role.ADMIN){
            throw new IllegalArgumentException("only for admin");
        }

        Admin admin = (Admin) user;
        if(!admin.getSecurityKey().equals(userRequest.getSecurityKey())){
            throw new SecurityKeyNotMatchingException("enter security key correctly");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(userRequest.getEmail());
        CookieUtil.saveTokenToCookie(token,response);
    }

    public User findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

}