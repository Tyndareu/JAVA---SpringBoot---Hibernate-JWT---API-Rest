package com.cursojava.curso.dao;

import java.util.List;

import com.cursojava.curso.models.User;

public interface UserDao {

    List<User> getUsers();

    void delete(Long id);

    void save(User user);

    boolean validateUser(User user);
    
}
