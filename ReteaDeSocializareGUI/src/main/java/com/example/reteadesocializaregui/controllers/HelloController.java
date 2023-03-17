package com.example.reteadesocializaregui.controllers;

import com.example.reteadesocializaregui.HelloApplication;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private Service service;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelRegister;

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonNewTab;

    @FXML
    private ImageView imgView;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    public void initialize() throws IOException{
/*        try {
            Image image = new Image(new FileInputStream("social_media_logo.png"));
            imgView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void onLoginButtonClick() throws IOException {
        String email = textEmail.getText();
        String password = textPassword.getText();
        Iterable<User> users = service.findAll();
        String error = "";
        int exist = 0;
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) buttonRegister.getScene().getWindow();
                UserController userController = fxmlLoader.getController();
                userController.setService(this.service);
                userController.setUser(user);
                userController.initialise();
                stage.setResizable(false);
                stage.setTitle("User");
                stage.setScene(scene);
                exist = 1;
            }
        }
        if(exist == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("E-mail-ul sau parola introdusa nu este corecta!\n");
            alert.show();
        }
    }

    public void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
        RegisterController registerController = fxmlLoader.getController();
        registerController.setService(this.service);
        stage.setResizable(false);
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    public void onNewTabButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        HelloController helloController = fxmlLoader.getController();
        helloController.setService(this.service);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}