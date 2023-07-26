package com.cursojava.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setSurname("Rodriguez");
        user.setEmail("juan@gmail.com");
        user.setPhone("31245698");
        return user;
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDao.delete(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestHeader(name = "Authorization") String token) {
        
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userID = jwtUtil.getKey(token);
        if (userID == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        List<User> userList = userDao.getUsers();
        return ResponseEntity.ok(userList);
    }
}
