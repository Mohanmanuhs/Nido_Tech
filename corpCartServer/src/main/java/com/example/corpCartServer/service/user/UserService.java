package com.example.corpCartServer.service.user;


import com.example.corpCartServer.dto.ChangePassDto;
import com.example.corpCartServer.exception.UserNotActiveException;
import com.example.corpCartServer.models.auth.UserPrincipal;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.UserRepo;
import com.example.corpCartServer.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    public void changePassword(ChangePassDto changePassDto, UserDetails userDetails) {
        String email = userDetails.getUsername();
        changePassDto.setEmail(email);
        User user = userRepo.findByEmail(changePassDto.getEmail());
        if (user == null || !user.isActive()) {
            throw new UserNotActiveException("user with this email not found");
        } else if (!encoder.matches(changePassDto.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("password don't match old password");
        }
        user.setPassword(encoder.encode(changePassDto.getNewPassword()));
        userRepo.save(user);
    }

    public void deactivateCurrentUser(HttpServletResponse response, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = findByEmail(email);

        if (user == null || !user.isActive()) throw new UserNotActiveException("User not found with email: " + email);

        user.setActive(false);
        userRepo.save(user);
        CookieUtil.clearJwtCookie(response);
    }

    public void deleteUserByAdmin(HttpServletResponse response, String email) {
        User user = findByEmail(email);

        if (user == null || !user.isActive()) throw new UserNotActiveException("User not found with email: " + email);

        userRepo.delete(user);
        CookieUtil.clearJwtCookie(response);
        userRepo.delete(user);
    }

    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserPrincipal userDetails) {
            String email = userDetails.getUsername();
            User user = findByEmail(email);

            if (user == null || !user.isActive())
                throw new UserNotActiveException("User not found with email: " + email);

            return user.getRole().name();
        }
        return "USER";
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void deleteUserByEmail(String email) {
        userRepo.deleteByEmail(email);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

}