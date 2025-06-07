package com.example.corpCartServer.models.user;

import jakarta.persistence.Entity;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Admin extends User {

    private Integer securityKey;

}
