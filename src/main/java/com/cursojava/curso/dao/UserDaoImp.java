package com.cursojava.curso.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
    public User validateUser(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> users = entityManager.createQuery(query, User.class)
                .setParameter("email", user.getEmail())
                .getResultList();
    
        if (users.isEmpty()) {
            return null; // El usuario no fue encontrado en la base de datos
        }
    
        String storedUser = users.get(0).getPassword(); // Obtener el primer usuario de la lista (si hay más de uno, se ignora el resto)
    
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(storedUser, user.getPassword())) {
            return users.get(0);
        } else {
            return null;
        }
    }
}