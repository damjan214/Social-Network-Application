package com.example.reteadesocializaregui.database_repository;

import com.example.reteadesocializaregui.domain.Friendship;
import com.example.reteadesocializaregui.domain.FriendshipStatus;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;
import com.example.reteadesocializaregui.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDatabaseRepository extends MemoryRepository<Long, Friendship> {
    private String url;
    private String username;
    private String password;
    private Validator<Friendship> validator;

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Friendship> validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Friendship findOne(Long aLong) {
        String idS = aLong.toString();
        String sql = "SELECT * from friendships where id ='" + idS + "'" ;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("id");
            Long idUser1 = resultSet.getLong("idUser1");
            Long idUser2 = resultSet.getLong("idUser2");
            String friendsFrom = resultSet.getString("friendsFrom");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(friendsFrom, formatter);
            Friendship friendship = new Friendship(idUser1, idUser2);
            friendship.setID(id);
            friendship.setFriendsFrom(dateTime);
            return friendship;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idUser1 = resultSet.getLong("idUser1");
                Long idUser2 = resultSet.getLong("idUser2");
                String friendsFrom = resultSet.getString("friendsFrom");
                String stringStatus = resultSet.getString("status");
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime dateTime = LocalDateTime.parse(friendsFrom, formatter);
                Friendship friendship = new Friendship(idUser1, idUser2);
                friendship.setID(id);
                friendship.setFriendsFrom(dateTime);
                FriendshipStatus status = FriendshipStatus.valueOf(stringStatus);
                friendship.setStatus(status);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship entity) {

        validator.validate(entity);

        String sql = "insert into friendships (id, iduser1, iduser2, friendsfrom, status ) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,entity.getID());
            ps.setLong(2, entity.getIdUser1());
            ps.setLong(3, entity.getIdUser2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity.getFriendsFrom().format(formatter);
            ps.setString(4,dateTime);
            ps.setString(5, entity.getStatus().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship delete(Long aLong) {
        Friendship friendship = findOne(aLong);
        if(friendship == null){
            throw new LackException("Aceasta prietenie nu exista!\n");
        }
        String idS = aLong.toString();
        String sql = "delete from friendships where id ='" + idS + "'" ;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship update(Friendship entity1, Friendship entity2) {
        validator.validate(entity1);
        validator.validate(entity2);
        String id1 = entity1.getID().toString();
        String id2 = entity2.getID().toString();
        String sql = "update friendships set id = ?, iduser1 = ?, iduser2 = ?, friendsfrom = ?, status = ? where id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity2.getID());
            ps.setLong(2, entity2.getIdUser1());
            ps.setLong(3, entity2.getIdUser2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity2.getFriendsFrom().format(formatter);
            ps.setString(4, dateTime);
            ps.setString(5,entity2.getStatus().toString());
            ps.setLong(6, entity1.getID());
            ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int capacity(){
        Iterable<Friendship> friendships = findAll();
        int capacity = 0;
        for(Friendship friendship : friendships){
            capacity++;
        }
        return capacity;
    }

    @Override
    public boolean vid(){
        if (capacity() == 0) {
            return true;
        }
        return false;
    }
}




