package com.example.corpCartServer.controller.auth;

import com.example.corpCartServer.constants.Role;
import com.example.corpCartServer.dto.ChangePassDto;
import com.example.corpCartServer.dto.ProfileDto;
import com.example.corpCartServer.dto.UserLoginRequest;
import com.example.corpCartServer.dto.UserRegisterRequest;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.auth.UserPrincipal;
import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.service.auth.JwtService;
import com.example.corpCartServer.service.user.AdminService;
import com.example.corpCartServer.service.user.CustomerService;
import com.example.corpCartServer.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtService jwtService;

    private static ProfileDto getProfileFromUser(User user) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setEmail(user.getEmail());
        profileDto.setName(user.getName());
        profileDto.setRole(user.getRole());
        return profileDto;
    }

    private static Admin getAdmin(UserRegisterRequest userRequest) {
        Admin admin = new Admin();
        admin.setEmail(userRequest.getEmail());
        admin.setName(userRequest.getName());
        admin.setPassword(userRequest.getPassword());
        admin.setRole(userRequest.getRole());
        return admin;
    }

    private static Customer getCustomer(UserRegisterRequest userRequest) {
        Customer customer = new Customer();
        customer.setEmail(userRequest.getEmail());
        customer.setName(userRequest.getName());
        customer.setPassword(userRequest.getPassword());
        customer.setPhone(userRequest.getPhone());
        customer.setRole(userRequest.getRole());
        customer.setAddress(userRequest.getAddress());
        return customer;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRequest) {
        if (userService.findByEmail(userRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already registered user");
        }

        try {
            User user = null;
            switch (userRequest.getRole()) {
                case ADMIN -> {
                    Admin admin = getAdmin(userRequest);
                    user = adminService.createAdmin(admin);
                }
                case CUSTOMER -> {
                    Customer customer = getCustomer(userRequest);
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    customer.setCart(cart);
                    user = customerService.createCustomer(customer);
                }
            }
            userService.createUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userRequest, HttpServletResponse response) {
        User user = userService.findByEmail(userRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(userRequest.getEmail());
                Cookie cookie = new Cookie("jwt", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie);
                return ResponseEntity.ok(Map.of("role", user.getRole()));
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error: " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password not match");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("logout successful");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(getProfileFromUser(user));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePassDto changePassDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        String email = userDetails.getUsername();
        changePassDto.setEmail(email);
        try {
            userService.changePassword(changePassDto);
            return ResponseEntity.ok("password changed");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
        } catch (InputMismatchException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password do not match");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        String email = userDetails.getUsername();

        userService.deleteUserByEmail(email);
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("user deleted");
    }

    @GetMapping("/getUserType")
    public ResponseEntity<?> getUserType() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserPrincipal userDetails) {
            String email = userDetails.getUsername();
            User user = userService.findByEmail(email);

            if (user != null) {
                if (user.getRole() == Role.CUSTOMER) {
                    return ResponseEntity.ok("CUSTOMER");
                } else if (user.getRole() == Role.ADMIN) {
                    return ResponseEntity.ok("ADMIN");
                }
            }
        } else {
            return ResponseEntity.ok("USER");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bad request");
    }
}