package com.example.reteadesocializaregui.database_repository;

import com.example.reteadesocializaregui.domain.Message;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;
import com.example.reteadesocializaregui.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class MessagesDatabaseRepository extends MemoryRepository<Long, Message> {

    private String url;
    private String username;
    private String password;
    private Validator<Message> validator;

    public MessagesDatabaseRepository(String url, String username, String password, Validator<Message> validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Message findOne(Long aLong) {
        String idS = aLong.toString();
        String sql = "SELECT * from messages where id ='" + idS + "'";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("id");
            Long idUser1 = resultSet.getLong("iduser1");
            Long idUser2 = resultSet.getLong("iduser2");
            String messageFrom = resultSet.getString("messagefrom");
            String content = resultSet.getString("content");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(messageFrom, formatter);
            Message message = new Message(idUser1, idUser2, content);
            message.setID(id);
            message.setMessageFrom(dateTime);
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idUser1 = resultSet.getLong("iduser1");
                Long idUser2 = resultSet.getLong("iduser2");
                String messageFrom = resultSet.getString("messagefrom");
                String content = resultSet.getString("content");
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime dateTime = LocalDateTime.parse(messageFrom, formatter);
                Message message = new Message(idUser1, idUser2, content);
                message.setID(id);
                message.setMessageFrom(dateTime);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message save(Message entity) {

        String sql = "insert into messages (id, iduser1, iduser2, messagefrom, content ) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getID());
            ps.setLong(2, entity.getIdUser1());
            ps.setLong(3, entity.getIdUser2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity.getMessageFrom().format(formatter);
            ps.setString(4, dateTime);
            ps.setString(5, entity.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long aLong) {
        Message msg = findOne(aLong);
        if (msg == null) {
            throw new LackException("Acest mesaj nu exista!\n");
        }
        String idS = aLong.toString();
        String sql = "delete from messages where id ='" + idS + "'";
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
    public Message update(Message entity1, Message entity2) {
        String id1 = entity1.getID().toString();
        String id2 = entity2.getID().toString();
        String sql = "update messages set id = ?, iduser1 = ?, iduser2 = ?, messagefrom = ?, content = ? where id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity2.getID());
            ps.setLong(2, entity2.getIdUser1());
            ps.setLong(3, entity2.getIdUser2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity2.getMessageFrom().format(formatter);
            ps.setString(4, dateTime);
            ps.setString(5, entity2.getContent());
            ps.setLong(6, entity1.getID());
            ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int capacity() {
        Iterable<Message> messages = findAll();
        int capacity = 0;
        for (Message message : messages) {
            capacity++;
        }
        return capacity;
    }

    @Override
    public boolean vid() {
        if (capacity() == 0) {
            return true;
        }
        return false;
    }
}
