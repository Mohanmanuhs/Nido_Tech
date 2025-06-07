package com.example.corpCartServer.service.user;


import com.example.corpCartServer.dto.ChangePassDto;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    //private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public void createUser(User user) {
        //user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void changePassword(ChangePassDto changePassDto) {
        User user = userRepo.findByEmail(changePassDto.getEmail());
        /*if (user == null) {
            throw new UsernameNotFoundException("user with this email not found");
        } else if (!encoder.matches(changePassDto.getOldPassword(), user.getPassword())) {
            throw new InputMismatchException("password don't match old password");
        }
        user.setPassword(encoder.encode(changePassDto.getNewPassword()));*/
        user.setPassword(changePassDto.getNewPassword());
        userRepo.save(user);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void deleteUserByEmail(String email) {
        userRepo.deleteByEmail(email);
    }
}