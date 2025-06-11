package com.example.corpCartServer.models.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Admin extends User {

    @Column(nullable = false)
    private Integer securityKey;

}