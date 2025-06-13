package com.example.corpCartServer.controller.auth;

import com.example.corpCartServer.dto.*;
import com.example.corpCartServer.mapper.UserMapper;
import com.example.corpCartServer.models.auth.UserPrincipal;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.service.user.AdminService;
import com.example.corpCartServer.service.user.CustomerService;
import com.example.corpCartServer.service.user.UserService;
import com.example.corpCartServer.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final CustomerService customerService;
    private final AdminService adminService;

    public UserController(UserService userService, CustomerService customerService, AdminService adminService) {
        this.userService = userService;
        this.customerService = customerService;
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRequest) {
        customerService.registerUser(userRequest);
        return ResponseEntity.ok("User created successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/registerAdmin")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid UserRegisterRequest userRequest, @AuthenticationPrincipal UserPrincipal userDetails) {
        adminService.registerAdmin(userRequest, userDetails);
        return ResponseEntity.ok("admin created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userRequest, HttpServletResponse response) {
        customerService.loginCustomer(userRequest, response);
        return ResponseEntity.ok("login successfully");
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody @Valid AdminLoginRequest userRequest, HttpServletResponse response) {
        adminService.loginAdmin(userRequest, response);
        return ResponseEntity.ok("login successfully");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        CookieUtil.clearJwtCookie(response);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(UserMapper.getProfileFromUser(user));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePassDto changePassDto, @AuthenticationPrincipal UserPrincipal userDetails) {
        userService.changePassword(changePassDto, userDetails);
        return ResponseEntity.ok("password changed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserByUser(HttpServletResponse response, @AuthenticationPrincipal UserPrincipal userDetails) {
        userService.deactivateCurrentUser(response, userDetails);
        return ResponseEntity.ok("user deleted");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteByAdmin")
    public ResponseEntity<String> deleteUserByAdmin(HttpServletResponse response, DeleteUserByAdminRequest request) {
        userService.deleteUserByAdmin(response, request.getEmail());
        return ResponseEntity.ok("user deleted");
    }

    @GetMapping("/getUserType")
    public ResponseEntity<?> getUserType() {
        return ResponseEntity.ok(userService.getCurrentUserRole());
    }
}