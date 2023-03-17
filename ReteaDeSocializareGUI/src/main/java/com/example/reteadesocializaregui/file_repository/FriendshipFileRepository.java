package com.example.reteadesocializaregui.file_repository;

import com.example.reteadesocializaregui.domain.Friendship;
import com.example.reteadesocializaregui.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clasa de repository file pentru prietenie
 */
public class FriendshipFileRepository extends AbstractFileRepository<Long, Friendship> {
    /**
     * Constructor pentru repository
     *
     * @param fileName  - numele fisierului in care salvam prieteniile
     * @param validator - validatorul pentru prietenii
     */
    public FriendshipFileRepository(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    /**
     * Functie ce extrage prietenia din fisier
     *
     * @param attributes - atributele prieteniei pe care dorim sa il extragem
     * @return - prietenia extrasa
     */
    public Friendship extractEntity(List<String> attributes) {
        Friendship friendship = new Friendship(Long.parseLong(attributes.get(1)), Long.parseLong(attributes.get(2)));
        friendship.setFriendsFrom(LocalDateTime.parse(attributes.get(3)));
        friendship.setID(Long.parseLong(attributes.get(0)));
        return friendship;
    }

    /**
     * Functie ce transforma o prietenie intr-o lista de string-uri
     *
     * @param friendship - prietenia pe care o transformam intr-o lista de string-uri
     * @return - Lista de string-uri
     */
    public String createEntityAsString(Friendship friendship) {
        return friendship.getID() + ";" + friendship.getIdUser1() + ";" + friendship.getIdUser2() + ";" + friendship.getFriendsFrom();
    }
}
