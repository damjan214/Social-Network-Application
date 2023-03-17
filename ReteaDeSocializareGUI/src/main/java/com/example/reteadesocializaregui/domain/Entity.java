package com.example.reteadesocializaregui.domain;

import java.io.Serializable;

/**
 * Clasa ce reprezinta un obiect oarecare(un obiect abstract as putea spune).
 *
 * @param <ID>
 */
public class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    /**
     * ID-ul entitatii.
     */
    private ID ID;

    /**
     * Returneaza ID-ul entitatii.
     *
     * @return
     */
    public ID getID() {
        return ID;
    }

    /**
     * Seteaza ID-ul ID la entitate.
     *
     * @param ID - ID-ul ce urmeaza sa fie setat
     */
    public void setID(ID ID) {
        this.ID = ID;
    }
}