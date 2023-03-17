package com.example.reteadesocializaregui.user_interface_console;

import com.example.reteadesocializaregui.domain.Entity;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.exceptions.ValidationException;
import com.example.reteadesocializaregui.service.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI<ID, E extends Entity<ID>> {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    public int ui_add_user() {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduceti ID-ul utilizatorului: ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        System.out.println("Introduceti numele utilizatorului: ");
        String firstName = input.nextLine();
        System.out.println("Introduceti prenumele utilizatorului: ");
        String lastName = input.nextLine();
        System.out.println("Introduceti parola utilizatorului: ");
        String password = input.nextLine();
        System.out.println("Introduceti e-mailul utilizatorului: ");
        String email = input.nextLine();
        try {
            service.save_user(id, firstName, lastName, password, email);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (DuplicateException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        System.out.println("Adaugarea a fost realizata cu succes!\n");
        return 0;
    }

    public int ui_delete_user() {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduceti ID-ul utilizatorului: ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        try {
            service.delete_user(id);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (LackException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        System.out.println("Stergerea a fost realizata cu succes!\n");
        return 0;
    }

    public int ui_user_list() {
        try {
            Iterable<User> users = service.findAll();
            System.out.println("Lista de utilizatori: ");
            for (User user : users) {
                System.out.println(user);
            }
        } catch (LackException e) {
            System.out.print(e.getMessage());
        }
        System.out.println();
        return 0;
    }

    public int ui_add_friend() {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduceti ID-ul prieteniei: ");
        Long idFriendship;
        try {
            idFriendship = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        System.out.println("Introduceti ID-ul utilizatorului 1: ");
        Long idFriend1;
        try {
            idFriend1 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        System.out.println("Introduceti ID-ul utilizatorului 2: ");
        Long idFriend2;
        try {
            idFriend2 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        try {
            service.save_friend(idFriendship,idFriend1, idFriend2);
        } catch (LackException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (DuplicateException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        System.out.println("Cei doi useri au devenit prieteni!\n");
        return 0;
    }

    public int ui_delete_friend() {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduceti ID-ul utilizatorului 1: ");
        Long id1;
        try {
            id1 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        System.out.println("Introduceti ID-ul utilizatorului 2: ");
        Long id2;
        try {
            id2 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        try {
            service.delete_friend(id1, id2);
        } catch (LackException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (DuplicateException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        System.out.println("Cei doi useri nu mai sunt prieteni!\n");
        return 0;
    }

    public int ui_friend_list() {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduceti ID-ul utilizatorului : ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID-ul contine doar cifre!\n");
            return 0;
        }
        input.nextLine();
        try {
            List<User> friends = new ArrayList<>();
            friends = service.friend_list(id);
            if(friends.size() == 0){
                System.out.println("Acest user nu are niciun prieten!\n");
            }
            else{
                System.out.println("Lista de prieteni: ");
                for (User user : friends) {
                    System.out.println(user);
                }
                System.out.println();
            }
        } catch (LackException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void ui_number_of_communities(){
        try{
            int number_of_communities = service.number_of_communities();
            System.out.print("Numarul de comunitati al retelei este: ");
            System.out.println(number_of_communities);
            System.out.println();
        }
        catch (LackException e){
            System.out.println("Nu exista nicio comunitate!\n");
        }
    }


    public void menu() {
        System.out.println("Retea de socializare");
        System.out.println("1.Adauga un utilizator.");
        System.out.println("2.Sterge un utilizator.");
        System.out.println("3.Afiseaza lista de utilizatori.");
        System.out.println("4.Adauga un prieten pentru un utilizator.");
        System.out.println("5.Sterge un prieten pentru un utilizator.");
        System.out.println("6.Afiseaza lista de prieteni a unui utilizator.");
        System.out.println("7.Afiseaza numarul de comunitati.");
        System.out.println("Pentru a inchide aplicatia, scrieti 'exit'.\n");
    }

    public int run() {
        Scanner input = new Scanner(System.in);
        while (true) {
            menu();
            System.out.println("Introduceti: ");
            String command = input.nextLine();
            if (command.equals("1")) {
                ui_add_user();
            }
            if (command.equals("2")) {
                ui_delete_user();
            }
            if (command.equals("3")) {
                ui_user_list();
            }
            if (command.equals("4")) {
                ui_add_friend();
            }
            if (command.equals("5")) {
                ui_delete_friend();
            }
            if (command.equals("6")) {
                ui_friend_list();
            }
            if (command.equals("7")) {
                ui_number_of_communities();
            }
            if (command.equals("exit")) {
                return 0;
            }
        }
    }
}
