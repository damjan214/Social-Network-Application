package com.example.reteadesocializaregui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clasa ce reprezinta prietenia dintre 2 utilizatori
 */
public class Friendship extends Entity<Long> {
    /**
     * ID-ul primului utilizator
     */
    private Long idUser1;
    /**
     * ID-ul celui de-al doilea utilizator
     */
    private Long idUser2;
    /**
     * Momentul in care a fost realizata prietenia
     */
    private LocalDateTime friendsFrom;

    /**
     * Statusul prieteniei
     */
    private FriendshipStatus status;

    /**
     * Constructor pentru prietenie
     *
     * @param idUser1 - ID-ul primului utilizator
     * @param idUser2 - ID-ul celui de-al doilea utilizator
     */
    public Friendship(Long idUser1, Long idUser2) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendsFrom = LocalDateTime.now();
        this.status = FriendshipStatus.PENDING;
    }

    /**
     * Returneaza ID-ul primului utilizator
     *
     * @return - ID-ul primului utilizator
     */
    public Long getIdUser1() {
        return idUser1;
    }

    /**
     * Seteaza ID-ul primului utilizator cu idUser1
     *
     * @param idUser1 - ID-ul setat
     */

    public void setIdUser1(Long idUser1) {
        this.idUser1 = idUser1;
    }

    /**
     * Returneaza ID-ul celui de-al doilea utilizator
     *
     * @return - ID-ul celui de-al doilea utiilizator
     */

    public Long getIdUser2() {
        return idUser2;
    }

    /**
     * Seteaza ID-ul celui de-al doilea utilizator cu idUser2
     *
     * @param idUser2 - ID-ul setat
     */

    public void setIdUser2(Long idUser2) {
        this.idUser2 = idUser2;
    }

    /**
     * Returneaza data in care cei doi useri au devenit prieteni
     *
     * @return - data in care cei doi useri au devenit prieteni
     */
    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    /**
     * @param friendsFrom -
     */
    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    /**
     * Metoda cu ajutorul careia afisam prietenia.
     *
     * @return - se returneaza prietenia afisata
     */
    @Override
    public String toString() {
        return "Friendship{" +
                "idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                ", friendsFrom=" + friendsFrom +
                '}';
    }

    /**
     * Metoda ce verifica daca 2 prietenii sunt identice.
     *
     * @param o -  prietenia cu care este comparat prietenia this
     * @return - adevarat in cazul in care obiectele sunt identice, altfel fals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship that)) return false;
        return Objects.equals(idUser1, that.idUser1) && Objects.equals(idUser2, that.idUser2) && Objects.equals(friendsFrom, that.friendsFrom);
    }

    /**
     * Returneaza hashCode-ul prieteniei.
     *
     * @return - returneaza hashCode-ul prieteniei.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idUser1, idUser2, friendsFrom);
    }
}
