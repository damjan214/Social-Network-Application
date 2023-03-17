package com.example.reteadesocializaregui.domain;

import org.testng.annotations.Test;

class UserTest {

    @Test
    void getFirstName() {
        User user = new User("Cotoara", "Damian", "12345678", "damian_cotoara@yahoo.ro");
        assert (user.getFirstName().equals("Cotoara"));
    }

    @Test
    void setFirstName() {
        User user = new User("Cotoara", "Damian", "12345678", "damian_cotoara@yahoo.ro");
        user.setFirstName("Coroata");
        assert (user.getFirstName().equals("Coroata"));
    }

    @Test
    void getLastName() {
        User user = new User("Cotoara", "Damian", "12345678", "damian_cotoara@yahoo.ro");
        assert (user.getLastName().equals("Damian"));
    }

    @Test
    void setLastName() {
        User user = new User("Cotoara", "Damian", "12345678", "damian_cotoara@yahoo.ro");
        user.setLastName("Damjan");
        assert (user.getLastName().equals("Damjan"));
    }

}