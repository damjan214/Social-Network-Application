package com.example.reteadesocializaregui.service;

import com.example.reteadesocializaregui.controllers.Observable;
import com.example.reteadesocializaregui.domain.*;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;

import java.util.*;

/**
 * Clasa ce are rol de a construi un service pentru utilizatori.
 */
public class Service extends Observable {
    /**
     * Repository-ul pentru utilizatori.
     */
    private MemoryRepository<Long, User> repositoryUser;

    /**
     * Repository-ul pentru prietenii.
     */
    private MemoryRepository<Long, Friendship> repositoryFriendship;

    private MemoryRepository<Long, Message> repositoryMessage;

    /**
     * Constructor pentru service.
     *
     * @param repositoryUser - repository pentru utilizatori
     */
    public Service(MemoryRepository<Long, User> repositoryUser, MemoryRepository<Long, Friendship> repositoryFriendship, MemoryRepository<Long, Message> repositoryMessage) {
        this.repositoryUser = repositoryUser;
        this.repositoryFriendship = repositoryFriendship;
        this.repositoryMessage = repositoryMessage;
    }

    /**
     * Functie ce returneaza utilizatorul ce are ID-ul id.
     *
     * @param id - id-ul utilizatorului
     * @return - utilizatorul in cazul in care aceasta exista in container, altfel se arunca exceptie
     */
    public User findOne(Long id) {
        return repositoryUser.findOne(id);
    }

    /**
     * Functie ce returneaza prietenia ce are ID-ul id.
     *
     * @param id - id-ul prieteniei
     * @return - prietenia in cazul in care aceasta exista in container, altfel se arunca exceptie
     */
    public Friendship findOneFriendship(Long id) {
        return repositoryFriendship.findOne(id);
    }

    public Message findOneMessage(Long id) {
        return repositoryMessage.findOne(id);
    }

    /**
     * Functie ce returneaza toti utilizatorii din container.
     *
     * @return - container-ul de entitati in cazul in care avem entitati in acesta, altfel se arunca exceptie
     */
    public Iterable<User> findAll() {
        return repositoryUser.findAll();
    }

    /**
     * Functie ce returneaza toate prieteniile din container.
     *
     * @return - container-ul de entitati in cazul in care avem entitati in acesta, altfel se arunca exceptie
     */
    public Iterable<Friendship> findAllFriendships() {
        return repositoryFriendship.findAll();
    }

    public Iterable<Message> findAllMessages() {
        return repositoryMessage.findAll();
    }

    /**
     * Functie ce adauga un utilizator in container
     *
     * @param id        - id-ul utilizatorului
     * @param firstName - numele utilizatorului
     * @param lastName  - prenumele utilizatorului
     * @return - null in cazul in care se realizeaza adaugarea, altfel se arunca exceptie
     */
    public User save_user(Long id, String firstName, String lastName, String password, String email) {
        User user = new User(firstName, lastName, password, email);
        user.setID(id);
        repositoryUser.save(user);
        return null;
    }

    public Message save_message(Long id, Long idUser1, Long idUser2, String content) {
        Message message = new Message(idUser1, idUser2, content);
        message.setID(id);
        repositoryMessage.save(message);
        notification();
        return null;
    }

    /**
     * Functie ce sterge un utilizator din container.
     *
     * @param id - id-ul utilizatorului
     * @return - null in cazul in care se realizeaza stergerea, altfel se arunca exceptie
     */
    public User delete_user(Long id) {
        Iterable<User> users = repositoryUser.findAll();
        for (User usr : users) {
            if (usr != repositoryUser.findOne(id)) {
                delete_friend(id, usr.getID());
            }
        }
        repositoryUser.delete(id);
        return null;
    }

    /**
     * Functie ce adauga prietenia dintre doi utilizatori.
     *
     * @param idFriend1 - id-ul primului utilizator
     * @param idFriend2 - id-ul celui de-al doilea utilizator
     * @return - null in cazul in care se realizeaza adaugarea, altfel se arunca exceptie
     */
    public Friendship save_friend(Long idFriendship, Long idFriend1, Long idFriend2) {
        Friendship friendship = new Friendship(idFriend1, idFriend2);
        Iterable<Friendship> friendships;
        if (repositoryFriendship.vid() == true) {
            friendship.setID(idFriendship);
            repositoryFriendship.save(friendship);
            return null;
        }
        friendships = repositoryFriendship.findAll();
        for (Friendship fr : friendships) {
            if (fr.getIdUser1() == idFriend1 && fr.getIdUser2() == idFriend2) {
                throw new DuplicateException();
            } else if (fr.getIdUser1() == idFriend2 && fr.getIdUser2() == idFriend1) {
                throw new DuplicateException();
            }
        }
        friendship.setID(idFriendship);
        repositoryFriendship.save(friendship);
        notification();
        return null;
    }

    /**
     * Functie ce sterge prietenia dintre doi utilizatori.
     *
     * @param idFriend1 - id-ul primului utilizator
     * @param idFriend2 - id-ul celui de-al doilea utilizator
     * @return - null in cazul in care se realizeaza stergerea, altfel se arunca exceptie
     */
    public User delete_friend(Long idFriend1, Long idFriend2) {
        User user1 = repositoryUser.findOne(idFriend1);
        User user2 = repositoryUser.findOne(idFriend2);
        Friendship friendship = new Friendship(idFriend1, idFriend2);
        if (repositoryFriendship.vid() == true) return null;
        Iterable<Friendship> friendships = repositoryFriendship.findAll();
        int exist = 0;
        for (Friendship fr : friendships) {
            if ((fr.getIdUser1() == idFriend1 && fr.getIdUser2() == idFriend2) || (fr.getIdUser1() == idFriend2 && fr.getIdUser2() == idFriend1)) {
                friendship.setID(fr.getID());
                exist = 1;
            }
        }
        if (exist == 0) return null;
        repositoryFriendship.delete(friendship.getID());
        notification();
        return null;
    }

    public void update_friendship_status(Friendship friendship) {
        Friendship friendship_accepted = new Friendship(friendship.getIdUser1(), friendship.getIdUser2());
        friendship_accepted.setID(friendship.getID());
        friendship_accepted.setFriendsFrom(friendship.getFriendsFrom());
        friendship_accepted.setStatus(FriendshipStatus.ACCEPTED);
        repositoryFriendship.update(friendship, friendship_accepted);
        notification();
    }

    /**
     * Functie ce returneaza lista de prieteni a unui utilizator
     *
     * @param id - id-ul utilizatorului
     * @return - lista de prieteni a utilizatorului in cazul in care acesta are prieteni, altfel se arunca exceptie
     */
    public List<User> friend_list(Long id) {
        User user1 = repositoryUser.findOne(id);
        List<User> friends = new ArrayList<>();
        Iterable<Friendship> friendships = repositoryFriendship.findAll();
        for (Friendship friendship : friendships) {
            if (friendship.getIdUser1() == id) {
                friends.add(repositoryUser.findOne(friendship.getIdUser2()));
            } else if (friendship.getIdUser2() == id) {
                friends.add(repositoryUser.findOne(friendship.getIdUser1()));
            }
        }
        return friends;
    }


    /**
     * Functie ce determina numarul de comunitati din retea.
     *
     * @return - numarul de comunitati din retea
     */
    public int number_of_communities() {
        Iterable<User> users = repositoryUser.findAll();
        int nr_of_users = repositoryUser.capacity();
        int nr_of_communities;
        int matrix[][] = new int[2 * nr_of_users][2 * nr_of_users];
        int visited[] = new int[nr_of_users];
        for (int i = 0; i < nr_of_users; i++) {
            visited[i] = 0;
        }
        for (int i = 0; i < nr_of_users; i++) {
            for (int j = 0; j < nr_of_users; j++) {
                matrix[i][j] = 0;
            }
        }
        if (repositoryFriendship.vid() == true) {
            nr_of_communities = nr_of_users;
            return nr_of_communities;
        }
        Iterable<Friendship> friendships = repositoryFriendship.findAll();
        for (User user : users) {
            for (User usr : friend_list(user.getID())) {
                matrix[user.getID().intValue()][usr.getID().intValue()] = 1;
            }
        }
        Graph graph = new Graph(matrix, visited, nr_of_users);
        nr_of_communities = graph.number_of_communities();
        return nr_of_communities;
    }

    public int generate_id() {
        Iterable<User> users = findAll();
        Collection<Friendship> friendships = (Collection<Friendship>) findAllFriendships();
        int id = 0;
        for (Friendship friendship : friendships) {
            if (friendship.getID() > id) id = friendship.getID().intValue();
        }
        id = id + 1;
        return id;
    }

    public Iterable<Message> conversation_of_2_users(Long idUser1, Long idUser2) {
        List<Message> conversation = new ArrayList<>();
        Iterable<User> users = repositoryUser.findAll();
        Iterable<Friendship> friendships = repositoryFriendship.findAll();
        Iterable<Message> messages = repositoryMessage.findAll();
        for (Message msg : messages) {
            if ((msg.getIdUser1().equals(idUser1) && msg.getIdUser2().equals(idUser2)) || (msg.getIdUser1().equals(idUser2) && msg.getIdUser2().equals(idUser1))) {
                conversation.add(msg);
            }
        }
        Collections.sort(conversation, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getID().compareTo(o2.getID());
            }
        });
        return conversation;
    }

}
