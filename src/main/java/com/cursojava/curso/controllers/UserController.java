package com.cursojava.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

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

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
    
        // Generar el hash de la contraseña utilizando Argon2
        String hashedPassword = Argon2Factory.create().hash(10, 1024, 1, user.getPassword());
    
        user.setPassword(hashedPassword);
        userDao.save(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = userDao.getUsers();
        return userList;
    }

}
