package com.example.corpCartServer.dto;

import com.example.corpCartServer.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    @NotBlank(message = "Email may not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "address may not be blank")
    private String address;

    private String phone;

    @NotNull(message = "Please select role")
    private Role role;

}