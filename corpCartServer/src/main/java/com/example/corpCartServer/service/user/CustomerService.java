package com.example.corpCartServer.service.user;

import com.example.corpCartServer.dto.UserLoginRequest;
import com.example.corpCartServer.dto.UserRegisterRequest;
import com.example.corpCartServer.exception.UserAlreadyExistsException;
import com.example.corpCartServer.exception.UserNotActiveException;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.CustomerRepo;
import com.example.corpCartServer.service.auth.JwtService;
import com.example.corpCartServer.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.corpCartServer.mapper.UserMapper.getCustomer;
import static com.example.corpCartServer.utils.AppConstants.BCRYPT_PASS_STRENGTH;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCRYPT_PASS_STRENGTH);

    private void createCustomer(UserRegisterRequest userRequest) {
        Customer customer = getCustomer(userRequest);
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
        customer.setPassword(encoder.encode(customer.getPassword()));
        customerRepo.save(customer);
    }

    public void loginCustomer(UserLoginRequest userRequest, HttpServletResponse response) {
        User user = findByEmail(userRequest.getEmail());

        if (user == null || !user.isActive()) {
            throw new UserNotActiveException("User not found or inactive");
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

    public void registerUser(UserRegisterRequest userRequest) {
        if (findByEmail(userRequest.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + userRequest.getEmail());
        }

        if (userRequest.getRole() == null) {
            throw new IllegalArgumentException("User role must be provided");
        }

        switch (userRequest.getRole()) {
            case CUSTOMER -> createCustomer(userRequest);
            case ADMIN -> throw new IllegalArgumentException("Only customers can register themselves");
            default -> throw new IllegalArgumentException("Invalid role: " + userRequest.getRole());
        }
    }

    public User findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }


}