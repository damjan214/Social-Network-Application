package com.example.reteadesocializaregui.database_repository;

import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.memory_repository.MemoryRepository;
import com.example.reteadesocializaregui.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDatabaseRepository extends MemoryRepository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;

    public UserDatabaseRepository(String url, String username, String password, Validator<User> validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public User findOne(Long aLong) {
        String idS = aLong.toString();
        String sql = "SELECT * from users where id ='" + idS + "'" ;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            User user = new User(firstName, lastName, password, email);
            user.setID(id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(firstName, lastName, password, email);
                user.setID(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {
        for(User user: findAll()){
            if(user.getEmail().equals(entity.getEmail())){
                throw new DuplicateException("Exista deja un cont cu acest e-mail!\n");
            }
        }
        validator.validate(entity);

        String sql = "insert into users (id, firstname, lastname, password, email ) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getID());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long aLong) {
        User user = findOne(aLong);
        if(user == null){
            throw new LackException("Acest user nu exista!\n");
        }
        String idS = aLong.toString();
        String sql = "delete from users where id ='" + idS + "'" ;
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
    public User update(User entity1, User entity2) {
        //validator.validate(entity1);
        //validator.validate(entity2);
        String id1 = entity1.getID().toString();
        String id2 = entity2.getID().toString();
        String sql = "update users set id = ?, firstname = ?, lastname = ?, password = ?, email = ? where id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity2.getID());
            ps.setString(2, entity2.getFirstName());
            ps.setString(3, entity2.getLastName());
            ps.setString(4, entity2.getPassword());
            ps.setString(5, entity2.getEmail());
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
        Iterable<User> users = findAll();
        int capacity = 0;
        for(User user : users){
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



