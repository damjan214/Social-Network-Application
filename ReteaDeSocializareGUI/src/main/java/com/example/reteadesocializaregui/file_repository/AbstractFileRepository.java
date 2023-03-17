package com.example.reteadesocializaregui.file_repository;

import com.example.reteadesocializaregui.memory_repository.MemoryRepository;
import com.example.reteadesocializaregui.domain.Entity;
import com.example.reteadesocializaregui.validators.Validator;

import java.io.*;

import java.util.Arrays;
import java.util.List;

/**
 * Clasa ce are rol de a construi un repository ce contine entitati salvate in fisier.
 *
 * @param <ID> - ID-ul entitatii
 * @param <E>  - tipul entitatii
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends MemoryRepository<ID, E> {
    /**
     * numele fisierului unde salvam entitatile
     */
    String fileName;

    /**
     * Constructor pt repository file
     *
     * @param fileName  - numele fisierului unde salvam entitatile
     * @param validator - validator pentru entitatea E
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();

    }

    /**
     * Functie ce trimite datele salvate (in fisier) in memoria aplicatiei
     */
    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                E e = extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Functie ce extrage entitatea din fisier
     *
     * @param attributes - atributele entitatii pe care dorim sa o extragem
     * @return - entitatea extrasa
     */
    public abstract E extractEntity(List<String> attributes);

    /**
     * Functie ce transforma o entitate intr-o lista de string-uri
     *
     * @param entity - entitatea pe care o transformam intr-o lista de string-uri
     * @return - Lista de string-uri
     */
    protected abstract String createEntityAsString(E entity);

    /**
     * Functie ce adauga o entitate in container.
     *
     * @param entity - entitatea ce urmeaza sa fie adaugata in container
     * @return - null in cazul in care se realizeaza adaugarea, altfel se arunca exceptie
     */
    @Override
    public E save(E entity) {
        E e = super.save(entity);
        if (e == null) {
            writeToFile(entity);
        }
        return e;

    }

    /**
     * Functie ce sterge o entitate din container.
     *
     * @param id - id-ul entitatii
     * @return - null in cazul in care se realizeaza stergerea, altfel se arunca exceptie
     */
    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        if (e == null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(fileName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            writer.print("");
            writer.close();
            for (E entity : findAll()) {
                writeToFile(entity);
            }
        }
        return e;
    }

    /**
     * Functie ce inlocuieste entity1 cu entity2.
     *
     * @param entity1 - entitatea ce urmeaza sa fie modificata
     * @param entity2 - entitatea ce inlocuieste entity1
     * @return - null in cazul in care se realizeaza modificarea, altfel se arunca exceptie
     */
    @Override
    public E update(E entity1, E entity2) {
        E e = super.update(entity1, entity2);
        if (e == null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(fileName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            writer.print("");
            writer.close();
            for (E entity : findAll()) {
                writeToFile(entity);
            }
        }
        return e;
    }

    /**
     * Functie ce transcrie entitatea in fisier
     *
     * @param entity - entitatea transcrisa in fisier
     */
    protected void writeToFile(E entity) {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


