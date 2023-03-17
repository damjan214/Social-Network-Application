package com.example.reteadesocializaregui;

import com.example.reteadesocializaregui.controllers.HelloController;
import com.example.reteadesocializaregui.database_repository.FriendshipDatabaseRepository;
import com.example.reteadesocializaregui.database_repository.MessagesDatabaseRepository;
import com.example.reteadesocializaregui.database_repository.UserDatabaseRepository;
import com.example.reteadesocializaregui.file_repository.FriendshipFileRepository;
import com.example.reteadesocializaregui.file_repository.UserFileRepository;
import com.example.reteadesocializaregui.service.Service;
import com.example.reteadesocializaregui.validators.FriendshipValidator;
import com.example.reteadesocializaregui.validators.MessageValidator;
import com.example.reteadesocializaregui.validators.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        UserValidator userValidator = new UserValidator();
        UserFileRepository userRepository = new UserFileRepository("D:\\Facultate\\Metode avansate de programare\\ReteaDeSocializareGUI\\src\\main\\java\\com\\example\\reteadesocializaregui\\files\\users.cvs", userValidator);
        FriendshipValidator friendshipValidator = new FriendshipValidator(userRepository);
        FriendshipFileRepository friendshipRepository = new FriendshipFileRepository("D:\\Facultate\\Metode avansate de programare\\ReteaDeSocializareGUI\\src\\main\\java\\com\\example\\reteadesocializaregui\\files\\friendships.cvs", friendshipValidator);
        MessageValidator messageValidator = new MessageValidator();
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345678", userValidator);
        FriendshipDatabaseRepository friendshipDatabaseRepository = new FriendshipDatabaseRepository("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345678", friendshipValidator);
        MessagesDatabaseRepository messagesDatabaseRepository = new MessagesDatabaseRepository("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345678", messageValidator);
        Service serviceUser = new Service(userDatabaseRepository, friendshipDatabaseRepository, messagesDatabaseRepository);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        HelloController helloController = fxmlLoader.getController();
        helloController.setService(serviceUser);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}