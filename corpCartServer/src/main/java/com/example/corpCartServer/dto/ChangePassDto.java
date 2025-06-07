package com.example.corpCartServer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDto {

    private String email;

    @NotBlank(message = "Password may not be blank")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String oldPassword;

    @NotBlank(message = "Password may not be blank")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String newPassword;

}