package com.cursojava.curso.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody User user) {
        if (userDao.validateUser(user)) {
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Login correcto\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"success\": false, \"message\": \"Credenciales inválidas\"}");
        }
    }
}
