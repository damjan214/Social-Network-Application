package com.example.reteadesocializaregui.memory_repository;

import com.example.reteadesocializaregui.domain.Entity;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.validators.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa ce are rol de a construi un repository ce contine entitati salvate in memorie.
 *
 * @param <ID> - ID-ul entitatii
 * @param <E>  - tipul entitatii
 */
public class MemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    /**
     * Validatorul pentru entitati.
     */
    private Validator<E> validator;
    /**
     * Container-ul de date.
     */
    private Map<ID, E> entities;

    /**
     * Constructor pentru repository.
     *
     * @param validator - validatorul pentru entitatea E
     */
    public MemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap<ID, E>();
    }

    /**
     * Functie ce returneaza entitatea ce are ID-ul id.
     *
     * @param id - id-ul entitatii
     * @return - entitatea in cazul in care aceasta exista in container, altfel se arunca exceptie
     */
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fii nul!\n");
        } else if (entities.get(id) == null) {
            throw new LackException("Entitatea cu acest ID nu exista!\n");
        }
        return entities.get(id);
    }

    /**
     * Functie ce returneaza toate entitatile din container.
     *
     * @return - container-ul de entitati in cazul in care avem entitati in acesta, altfel se arunca exceptie
     */
    public Iterable<E> findAll() {
        if (entities.values().size() == 0) {
            throw new LackException("Nu avem nicio entitate!\n");
        }
        return entities.values();
    }

    /**
     * Functie ce adauga o entitate in container.
     *
     * @param entity - entitatea ce urmeaza sa fie adaugata in container
     * @return - null in cazul in care se realizeaza adaugarea, altfel se arunca exceptie
     */
    public E save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ID-ul nu poate fii nul!\n");
        }
        validator.validate(entity);
        for (E ent : entities.values()) {
            if (ent.getID() == entity.getID()) {
                throw new DuplicateException("ID-ul exista deja!\n");
            }
        }
        entities.put(entity.getID(), entity);
        return null;
    }

    /**
     * Functie ce sterge o entitate din container.
     *
     * @param id - id-ul entitatii
     * @return - null in cazul in care se realizeaza stergerea, altfel se arunca exceptie
     */
    public E delete(ID id) {
        if (entities.get(id) == null) {
            throw new LackException("Entitatea cu acest ID nu exista!\n");
        } else entities.remove(id);
        return null;
    }

    /**
     * Functie ce inlocuieste entity1 cu entity2.
     *
     * @param entity1 - entitatea ce urmeaza sa fie modificata
     * @param entity2 - entitatea ce inlocuieste entity1
     * @return - null in cazul in care se realizeaza modificarea, altfel se arunca exceptie
     */
    public E update(E entity1, E entity2) {
        entities.put(entity1.getID(), entity2);
        return null;
    }

    /**
     * Functie ce returneaza numarul de entitati din repository
     * @return - numarul de entitati din repository
     */
    public int capacity() {
        int capacity = 0;
        for (Entity e : entities.values()) {
            capacity++;
        }
        return capacity;
    }

    /**
     * Functie ce returneaza true daca repository-ul e vid sau false daca nu e vid
     * @return - true daca e vid, false daca nu e vid
     */
    public boolean vid() {
        if (capacity() == 0) {
            return true;
        }
        return false;
    }
}
