package com.example.corpCartServer.service.user;

import com.example.corpCartServer.dto.CartDto;
import com.example.corpCartServer.dto.UserLoginRequest;
import com.example.corpCartServer.dto.UserRegisterRequest;
import com.example.corpCartServer.exception.ResourceNotFoundException;
import com.example.corpCartServer.exception.UserAlreadyExistsException;
import com.example.corpCartServer.exception.UserNotActiveException;
import com.example.corpCartServer.mapper.CartMapper;
import com.example.corpCartServer.mapper.UserMapper;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.CustomerRepo;
import com.example.corpCartServer.service.auth.JwtService;
import com.example.corpCartServer.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    private void createCustomer(UserRegisterRequest userRequest) {
        Customer customer = UserMapper.getCustomer(userRequest);
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
        customer.setPassword(encoder.encode(customer.getPassword()));
        customerRepo.save(customer);
    }

    public void loginCustomer(UserLoginRequest userRequest, HttpServletResponse response) {
        User user = findByEmail(userRequest.getEmail());
        if (user == null || !user.getIsActive()) {
            throw new UserNotActiveException("User not found or inactive");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(userRequest.getEmail());
        CookieUtil.saveTokenToCookie(token, response);
    }

    public void registerUser(UserRegisterRequest userRequest) {
        if (findByEmail(userRequest.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + userRequest.getEmail());
        }
        createCustomer(userRequest);
    }

    public Customer findByEmail(String email) {
        System.out.println(email);
        Customer customer = customerRepo.findByEmail(email);
        if (customer == null) {
            System.out.println("No customer found with email: " + email);
        } else {
            System.out.println("Found customer: " + customer.getName());
        }
        return customer;
    }

    public CartDto getCartByUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        Optional<Cart> cart = customerRepo.findCartByEmail(email);
        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("cart is not exist for this user");
        }
        return CartMapper.cartToDto(cart.get(), new CartDto());
    }
}