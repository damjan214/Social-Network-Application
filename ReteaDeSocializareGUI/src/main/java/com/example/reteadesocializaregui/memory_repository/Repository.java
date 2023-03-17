package com.example.reteadesocializaregui.memory_repository;

import com.example.reteadesocializaregui.domain.Entity;

/**
 * Interfata pentru repository
 *
 * @param <ID> - ID-ul entitatii
 * @param <E>  - tipul entitatii
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Functie ce returneaza entitatea ce are ID-ul id.
     *
     * @param id - id-ul entitatii
     * @return - entitatea in cazul in care aceasta exista in container, altfel se arunca exceptie
     */
    E findOne(ID id);

    /**
     * Functie ce returneaza toate entitatile din container.
     *
     * @return - container-ul de entitati in cazul in care avem entitati in acesta, altfel se arunca exceptie
     */
    Iterable<E> findAll();

    /**
     * Functie ce adauga o entitate in container.
     *
     * @param entity - entitatea ce urmeaza sa fie adaugata in container
     * @return - null in cazul in care se realizeaza adaugarea, altfel se arunca exceptie
     */
    E save(E entity);

    /**
     * Functie ce sterge o entitate din container.
     *
     * @param id - id-ul entitatii
     * @return - null in cazul in care se realizeaza stergerea, altfel se arunca exceptie
     */
    E delete(ID id);

    /**
     * Functie ce inlocuieste entity1 cu entity2.
     *
     * @param entity1 - entitatea ce urmeaza sa fie modificata
     * @param entity2 - entitatea ce inlocuieste entity1
     * @return - null in cazul in care se realizeaza modificarea, altfel se arunca exceptie
     */
    E update(E entity1, E entity2);

    /**
     * Functie care verifica daca repository-ul e vid sau nu
     * @return true daca e vid, false daca nu e vid
     */
    boolean vid();

    /**
     * Functie ce returneaza numarul de entitati din repository
     * @return numarul de entitati din repository
     */
    int capacity();

}
