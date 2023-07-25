package com.cursojava.curso.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursojava.curso.models.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
    
        User storedUser = users.get(0); // Obtener el primer usuario de la lista (si hay más de uno, se ignora el resto)
    
        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
        if (user.getPassword().equals(storedUser.getPassword())) {
            return storedUser; // La contraseña es correcta, devolver el usuario encontrado
        } else {
            return null; // La contraseña es incorrecta, no se encuentra el usuario
        }
    }
}