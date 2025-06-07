package com.example.corpCartServer.dto;

import com.example.corpCartServer.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Email may not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "address may not be blank")
    private String address;

    @NotBlank(message = "Password may not be blank")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String password;

    private String phone;

    @NotNull(message = "Please select role")
    private Role role;

}