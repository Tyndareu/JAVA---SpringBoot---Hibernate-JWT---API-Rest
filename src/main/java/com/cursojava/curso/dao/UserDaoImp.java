package com.cursojava.curso.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursojava.curso.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query, User.class).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void save(User user) {
        entityManager.merge(user);
    }
    @Override
    public boolean validateUser(User user) {
        String query = "FROM User WHERE email = :email AND password = :password";
        List<User> users = entityManager.createQuery(query, User.class)
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword())
                .getResultList();
        return users.size() > 0;
    }
    
}
