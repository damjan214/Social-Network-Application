package com.example.reteadesocializaregui.controllers;

import com.example.reteadesocializaregui.HelloApplication;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.ValidationException;
import com.example.reteadesocializaregui.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class RegisterController {

    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private Label labelRegister;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelConfirmPassword;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelSuccess;

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textLastName;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textConfirmPassword;

    @FXML
    private TextField textEmail;

    @FXML
    private Button buttonRegister;

    public int onRegisterButtonClick(){
        try{
            String error = "";
            labelSuccess.setText("");
            String firstName = textFirstName.getText();
            String lastName = textLastName.getText();
            String password = textPassword.getText();
            String confirmPassword = textConfirmPassword.getText();
            String email = textEmail.getText();
            if(!password.equals(confirmPassword)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Parolele nu sunt identice!\n");
                alert.show();
                return 0;
            }
            Collection<User> users = (Collection<User>) service.findAll();
            Long id = (long) users.size();
            try {
                service.save_user(id, firstName, lastName, password, email);
            } catch (ValidationException e) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setContentText(e.getMessage());
                alertError.show();
                return 0;

            } catch (DuplicateException e) {
                Alert alertDuplication = new Alert(Alert.AlertType.ERROR);
                alertDuplication.setContentText(e.getMessage());
                alertDuplication.show();
                return 0;
            }
            labelSuccess.setText("Inregistrarea a fost realizata cu succes!\n");
        }
        catch (StringIndexOutOfBoundsException exception){
            Alert stringIndexAlert = new Alert(Alert.AlertType.ERROR);
            stringIndexAlert.setContentText("Campurile sunt goale!\n");
            stringIndexAlert.show();
            return 0;
        }
        return 0;
    }

    public void onLoginButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
        HelloController helloController = fxmlLoader.getController();
        helloController.setService(this.service);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
    }
}
