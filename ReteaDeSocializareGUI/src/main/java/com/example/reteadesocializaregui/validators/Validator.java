package com.example.reteadesocializaregui.validators;

import com.example.reteadesocializaregui.exceptions.ValidationException;

/**
 * Interfata pentru validator.
 *
 * @param <T> - tipul entitatii
 */
public interface Validator<T> {
    /**
     * Functia ce verifica utilizatorul daca este valid.
     *
     * @param entity - entitatea ce urmeaza a fii validata.
     * @throws ValidationException - in cazul in care entitatea este invalida.
     */
    void validate(T entity) throws ValidationException;
}
