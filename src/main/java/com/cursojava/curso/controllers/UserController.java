package com.cursojava.curso.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.curso.models.User;

@RestController
public class UserController {
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

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Juan");
        user1.setSurname("Rodriguez");
        user1.setEmail("juan@gmail.com");
        user1.setPhone("31245698");
        userList.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Juan-2");
        user2.setSurname("Rodriguez-2");
        user2.setEmail("juan@gmail.com-2");
        user2.setPhone("31245698-2");
        userList.add(user2);

        // Agregar más usuarios si es necesario

        return userList;
    }

}