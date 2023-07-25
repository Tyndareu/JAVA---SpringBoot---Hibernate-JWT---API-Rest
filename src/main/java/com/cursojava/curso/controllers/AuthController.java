package com.cursojava.curso.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User validatedUser = userDao.validateUser(user);
        if (validatedUser != null) {
            // Si el usuario es válido, creamos el token JWT
            String tokenJwt = jwtUtil.create(String.valueOf(validatedUser.getId()), validatedUser.getEmail());
            return ResponseEntity.ok(tokenJwt);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}

