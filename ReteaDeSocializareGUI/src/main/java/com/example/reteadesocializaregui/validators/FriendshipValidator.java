package com.example.reteadesocializaregui.validators;

import com.example.reteadesocializaregui.domain.Friendship;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.ValidationException;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;

/**
 * Clasa ce are rol pentru a valida o prietenie
 */
public class FriendshipValidator implements Validator<Friendship> {
    private MemoryRepository<Long, User> repositoryUser;

    public FriendshipValidator(MemoryRepository<Long, User> repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    /**
     * Functia ce verifica prietenia daca este valida.
     *
     * @param entity - entitatea ce urmeaza a fii validata.
     * @throws ValidationException - in cazul in care entitatea este invalida.
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        String error = "";
        if (entity.getIdUser1() == entity.getIdUser2()) {
            error = error + "Cei doi useri introdusi sunt identici!\n";
        }
        if (error.length() > 0) {
            throw new ValidationException(error);
        }
    }
}
