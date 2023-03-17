package com.example.reteadesocializaregui.service;

import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.exceptions.ValidationException;
import com.example.reteadesocializaregui.validators.MessageValidator;
import org.testng.annotations.Test;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;
import com.example.reteadesocializaregui.validators.FriendshipValidator;
import com.example.reteadesocializaregui.validators.UserValidator;

import java.util.List;

class ServiceTest {

    @Test
    void findOne() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        User user = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        user.setID(Long.valueOf(1));
        serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        assert (serviceUser.findOne(Long.valueOf(1)).getID() == user.getID());
        try {
            serviceUser.findOne(Long.valueOf(2));
        } catch (LackException e) {
            assert (true);
        }
    }

    @Test
    void findAll() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        User user1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        try {
            Iterable<User> empty = serviceUser.findAll();
        } catch (LackException e) {
            assert (true);
        }
        userRepository.save(user1);
        Iterable<User> users = serviceUser.findAll();
        assert (users.iterator().hasNext() == true);
    }

    @Test
    void save_user() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        Iterable<User> users = serviceUser.findAll();
        assert (users.iterator().hasNext() == true);
        try {
            serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        } catch (DuplicateException e) {
            assert (true);
        }
    }

    @Test
    void delete_user() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        User u1 = new User("Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        User u2 = new User("Pop", "Alin", "papp0ptotaia1", "popalin@gmail.com");
        u1.setID(Long.valueOf(1));
        u2.setID(Long.valueOf(2));
        userRepository.save(u1);
        userRepository.save(u2);
        serviceUser.save_friend(Long.valueOf(0), Long.valueOf(1), Long.valueOf(2));
        User user1 = serviceUser.findOne(Long.valueOf(1));
        User user2 = serviceUser.findOne(Long.valueOf(2));
        List<User> friends1 = serviceUser.friend_list(user1.getID());
        List<User> friends2 = serviceUser.friend_list(user2.getID());
        assert (friends1.get(0) == user2);
        assert (friends2.get(0) == user1);
        serviceUser.delete_user(Long.valueOf(2));
        user1 = serviceUser.findOne(Long.valueOf(1));
        try {
            friends1 = serviceUser.friend_list(user1.getID());
        } catch (LackException e) {
            assert (true);
        }
        Iterable<User> users = userRepository.findAll();
        assert (users.iterator().hasNext() == true);
        serviceUser.delete_user(Long.valueOf(1));
        try {
            users = serviceUser.findAll();
        } catch (LackException e) {
            assert (users.iterator().hasNext() == false);
        }
    }

    @Test
    void save_friend() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        serviceUser.save_user(Long.valueOf(2), "Pop", "Alin", "papp0ptotaia1", "popalin@gmail.com");
        serviceUser.save_friend(Long.valueOf(0), Long.valueOf(1), Long.valueOf(2));
        User user1 = serviceUser.findOne(Long.valueOf(1));
        User user2 = serviceUser.findOne(Long.valueOf(2));
        List<User> friends1 = serviceUser.friend_list(user1.getID());
        List<User> friends2 = serviceUser.friend_list(user2.getID());
        assert (friends1.get(0) == user2);
        assert (friends2.get(0) == user1);
        try {
            serviceUser.save_friend(Long.valueOf(0), Long.valueOf(1), Long.valueOf(1));
        } catch (ValidationException e) {
            assert (true);
        }
    }

    @Test
    void delete_friend() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        serviceUser.save_user(Long.valueOf(2), "Pop", "Alin", "papp0ptotaia1", "popalin@gmail.com");
        serviceUser.save_friend(Long.valueOf(0), Long.valueOf(1), Long.valueOf(2));
        User user1 = serviceUser.findOne(Long.valueOf(1));
        User user2 = serviceUser.findOne(Long.valueOf(2));
        List<User> friends1 = serviceUser.friend_list(user1.getID());
        List<User> friends2 = serviceUser.friend_list(user2.getID());
        assert (friends1.get(0) == user2);
        assert (friends2.get(0) == user1);
        try {
            serviceUser.delete_friend(Long.valueOf(1), Long.valueOf(1));
        } catch (LackException e) {
            assert (true);
        }
        serviceUser.delete_friend(Long.valueOf(1), Long.valueOf(2));
        user1 = serviceUser.findOne(Long.valueOf(1));
        user2 = serviceUser.findOne(Long.valueOf(2));
        try {
            friends1 = serviceUser.friend_list(user1.getID());
        } catch (LackException e) {
            assert (true);
        }
        try {
            friends2 = serviceUser.friend_list(user2.getID());
        } catch (LackException e) {
            assert (true);
        }
    }

    @Test
    void friend_list() {
        UserValidator userValidator = new UserValidator();
        MemoryRepository userRepository = new MemoryRepository(userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        MemoryRepository friendshipRepository = new MemoryRepository(friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        MemoryRepository messageRepository = new MemoryRepository(messageValidator);
        Service serviceUser = new Service(userRepository, friendshipRepository, messageRepository);
        serviceUser.save_user(Long.valueOf(1), "Cotoara", "Damian", "12345678A", "damian_cotoara@yahoo.ro");
        serviceUser.save_user(Long.valueOf(2), "Pop", "Alin", "papp0ptotaia1", "popalin@gmail.com");
        try {
            serviceUser.friend_list(Long.valueOf(1));
        } catch (LackException e) {
            assert (true);
        }
        serviceUser.save_friend(Long.valueOf(0), Long.valueOf(1), Long.valueOf(2));
        User user1 = serviceUser.findOne(Long.valueOf(1));
        User user2 = serviceUser.findOne(Long.valueOf(2));
        List<User> friends1 = serviceUser.friend_list(user1.getID());
        List<User> friends2 = serviceUser.friend_list(user2.getID());
        assert (friends1.get(0) == user2);
        assert (friends2.get(0) == user1);
        List<User> friend_list1 = serviceUser.friend_list(Long.valueOf(1));
        List<User> friend_list2 = serviceUser.friend_list(Long.valueOf(2));
        assert (friend_list1.get(0) == user2);
        assert (friend_list2.get(0) == user1);
    }
}