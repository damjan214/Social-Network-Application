package com.example.reteadesocializaregui.memory_repository;

import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.validators.UserValidator;
import org.testng.annotations.Test;

class MemoryRepositoryTest {

    @Test
    void findOne() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        User user = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        user.setID(Long.valueOf(1));
        memoryRepository.save(user);
        assert (memoryRepository.findOne(Long.valueOf(1)) == user);
        try {
            memoryRepository.findOne(Long.valueOf(2));
        } catch (LackException e) {
            assert (true);
        }
    }

    @Test
    void findAll() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        try {
            Iterable<User> empty = memoryRepository.findAll();
        } catch (LackException e) {
            assert (true);
        }
        memoryRepository.save(user1);
        Iterable<User> users = memoryRepository.findAll();
        assert (users.iterator().hasNext() == true);
    }

    @Test
    void save() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        memoryRepository.save(user1);
        Iterable<User> users = memoryRepository.findAll();
        assert (users.iterator().hasNext() == true);
        try {
            memoryRepository.save(user1);
        } catch (DuplicateException e) {
            assert (true);
        }
    }

    @Test
    void delete() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        user1.setID(Long.valueOf(1));
        memoryRepository.save(user1);
        Iterable<User> users = memoryRepository.findAll();
        assert (users.iterator().hasNext() == true);
        memoryRepository.delete(Long.valueOf(1));
        try {
            users = memoryRepository.findAll();
        } catch (LackException e) {
            assert (users.iterator().hasNext() == false);
        }
    }

    @Test
    void update() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        user1.setID(Long.valueOf(1));
        User user2 = new User("Pop", "Alin", "papp0ptotaia1", "popalin@gmail.com");
        user2.setID(Long.valueOf(2));
        memoryRepository.save(user1);
        memoryRepository.update(user1, user2);
        assert (memoryRepository.findOne(Long.valueOf(1)) == user2);
    }

    @Test
    void capacity() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        assert (memoryRepository.capacity() == 0);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        memoryRepository.save(user1);
        assert (memoryRepository.capacity() == 1);
    }

    @Test
    void vid() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository memoryRepository = new MemoryRepository(userValidator);
        assert (memoryRepository.vid() == true);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        memoryRepository.save(user1);
        assert (memoryRepository.vid() == false);
    }
}