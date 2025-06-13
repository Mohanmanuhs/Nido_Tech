package com.example.corpCartServer.mapper;

import com.example.corpCartServer.constants.Role;
import com.example.corpCartServer.dto.ProfileDto;
import com.example.corpCartServer.dto.UserRegisterRequest;
import com.example.corpCartServer.models.user.Admin;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;

public class UserMapper {

    public static ProfileDto getProfileFromUser(User user) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setEmail(user.getEmail());
        profileDto.setName(user.getName());
        profileDto.setRole(user.getRole());
        return profileDto;
    }

    public static Admin getAdmin(UserRegisterRequest userRequest) {
        Admin admin = new Admin();
        admin.setEmail(userRequest.getEmail());
        admin.setName(userRequest.getName());
        admin.setPassword(userRequest.getPassword());
        admin.setRole(Role.ADMIN);
        return admin;
    }

    public static Customer getCustomer(UserRegisterRequest userRequest) {
        Customer customer = new Customer();
        customer.setEmail(userRequest.getEmail());
        customer.setName(userRequest.getName());
        customer.setPassword(userRequest.getPassword());
        customer.setPhone(userRequest.getPhone());
        customer.setRole(Role.CUSTOMER);
        customer.setCompanyName(userRequest.getCompanyName());
        customer.setAddress(userRequest.getAddress());
        return customer;
    }
}
