package com.cursojava.curso.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User validatedUser = userDao.validateUser(user);
        if (validatedUser != null) {
            // Si el usuario es válido, creamos el token JWT
            String tokenJwt = jwtUtil.create(validatedUser.getId().toString(), validatedUser.getEmail());
            return tokenJwt;
        }
        else {
            return "Error";
        
        }
    }
}
