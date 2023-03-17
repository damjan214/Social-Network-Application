package com.example.reteadesocializaregui.validators;

import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.ValidationException;

/**
 * Clasa ce are rol de a valida utilizatorul.
 */
public class UserValidator implements Validator<User> {
    /**
     * Functia ce verifica utilizatorul daca este valid.
     *
     * @param entity - entitatea ce urmeaza a fii validata.
     * @throws ValidationException - in cazul in care entitatea este invalida.
     */
    @Override
    public void validate(User entity) throws ValidationException {
        char singleCharArray[] = new char[1];
        String error = "";
        entity.getFirstName().getChars(0, 1, singleCharArray, 0);
        char firstLetter = singleCharArray[0];
        if (firstLetter < 'A' || firstLetter > 'Z') {
            error = error + "Numele incepe cu majuscula!\n";
        }
        entity.getLastName().getChars(0, 1, singleCharArray, 0);
        firstLetter = singleCharArray[0];
        if (firstLetter < 'A' || firstLetter > 'Z') {
            error = error + "Prenumele incepe cu majuscula!\n";
        }

        if (entity.getFirstName().length() < 3) {
            error = error + "Numele trebuie sa aiba mai mult de 3 litere!\n";
        }
        if (entity.getLastName().length() < 3) {
            error = error + "Prenumele trebuie sa aiba mai mult de 3 litere!\n";
        }
        if (!entity.getFirstName().matches("^([^0-9]*)$")) {
            error = error + "Numele nu contine cifre sau spatii!\n";
        }
        if (!entity.getLastName().matches("^([^0-9]*)$")) {
            error = error + "Prenumele nu contine cifre sau spatii!\n";
        }
        if (!entity.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            error = error + "Parola contine cel putin 8 caractere si cel putin o litera si o cifra!\n";
        }

        if (!entity.getEmail().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            error = error + "E-mail-ul introdus nu este valid!\n";
        }

        if (error.length() > 0) {
            throw new ValidationException(error);
        }
    }
}