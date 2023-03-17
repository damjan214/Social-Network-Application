package com.example.reteadesocializaregui.file_repository;

import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.validators.Validator;

import java.util.List;

/**
 * Clasa de repository file pentru user
 */
public class UserFileRepository extends AbstractFileRepository<Long, User> {
    /**
     * Constructor pentru repository
     *
     * @param fileName  - numele fisierului in care salvam userii
     * @param validator - validatorul pentru useri
     */
    public UserFileRepository(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    /**
     * Functie ce extrage userul din fisier
     *
     * @param attributes - atributele userului pe care dorim sa il extragem
     * @return - entitatea extrasa
     */
    public User extractEntity(List<String> attributes) {
        User user = new User(attributes.get(1), attributes.get(2), attributes.get(3), attributes.get(4));
        user.setID(Long.parseLong(attributes.get(0)));
        return user;
    }

    /**
     * Functie ce transforma un user intr-o lista de string-uri
     *
     * @param user - userul pe care il transformam intr-o lista de string-uri
     * @return - Lista de string-uri
     */
    public String createEntityAsString(User user) {
        return user.getID() + ";" + user.getFirstName() + ";" + user.getLastName() + ";" + user.getPassword() + ";" + user.getEmail();
    }
}
