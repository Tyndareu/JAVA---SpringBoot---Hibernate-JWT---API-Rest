package com.cursojava.curso.models;

import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@RestController
public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
}
