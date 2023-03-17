package com.example.reteadesocializaregui.domain;

import java.util.Objects;

/**
 * Clasa ce reprezinta un utilizator.
 */
public class User extends Entity<Long> {
    /**
     * numele unui utilizator.
     */
    private String firstName;
    /**
     * prenumele unui utilizator.
     */
    private String lastName;

    /**
     * parola unui utilizator
     */
    private String password;

    /**
     * e-mail-ul unui utilizator
     */
    private String email;

    /**
     * Constructor pentru utilitzator.
     *
     * @param firstName - numele utilizatorului
     * @param lastName  - prenumele utilizatorului
     * @param password  - parola utilizatorului
     * @param email     - e-mailul utilizatorului
     */

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    /**
     * Returneaza numele utilizatorului.
     *
     * @return - numele utilizatorului
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Seteaza numele firstName la utilizator.
     *
     * @param firstName - numele ce urmeaza sa fie setat
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returneaza prenumele utilizatorului.
     *
     * @return - prenumele utilizatorului
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Seteaza prenumele lastName la utilizator.
     *
     * @param lastName - prenumele ce urmeaza sa fie setat
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returneaza parola utilizatorului.
     * @return - parola utilizatorului
     */

    public String getPassword() {
        return password;
    }

    /**
     * Seteaza parola password la utilizator.
     * @param password - parola ce urmeaza sa fie setata
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returneaza e-mailul utilizatorului.
     * @return
     */

    public String getEmail() {
        return email;
    }

    /**
     * Seteaza e-mailul email la utilizator.
     * @param email - e-mailul ce urmeaza sa fie setat
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metoda cu ajutorul careia afisam utilizatorul.
     *
     * @return - se returneaza utilizatorul afisat
     */

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Metoda ce verifica daca 2 utilizatori sunt identici.
     *
     * @param o -  user-ul cu care este comparat user-ul this
     * @return - adevarat in cazul in care obiectele sunt identice, altfel fals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }


    /**
     * Returneaza hashCode-ul utilizatorului.
     *
     * @return - returneaza hashCode-ul utilizatorului
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, password, email);
    }
}
