package com.example.reteadesocializaregui.controllers;

import com.example.reteadesocializaregui.HelloApplication;
import com.example.reteadesocializaregui.domain.Message;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class ChatController extends Observer {
    private Service service;

    private User user1;

    private User user2;

    @FXML
    private Button buttonBackToUser;

    @FXML
    private Label labelChatWith;

    @FXML
    private Button buttonSendMessage;

    @FXML
    private TextField textMessage;

    @FXML
    private ScrollPane scrollPaneConversation;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void initialise() {
        labelChatWith.setText("You are chatting with " + user2.getFirstName() + " " + user2.getLastName());
        reload_conversation();
    }

    public void onBackToUserButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) buttonBackToUser.getScene().getWindow();
        UserController userController = fxmlLoader.getController();
        userController.setService(this.service);
        userController.setUser(user1);
        userController.initialise();
        stage.setResizable(false);
        stage.setTitle("User");
        stage.setScene(scene);
    }

    public void onSendMessageButtonClick() {
        try {
            String content = textMessage.getText();
            Collection<Message> messages = (Collection<Message>) service.findAllMessages();
            Long id = (long) messages.size();
            service.save_message(id, user1.getID(), user2.getID(), content);
        } catch (ArrayIndexOutOfBoundsException exception) {

        }
        textMessage.setText("");
    }

    public void reload_conversation() {
        Iterable<Message> conversation = service.conversation_of_2_users(user1.getID(), user2.getID());
        VBox vBox = new VBox();
        vBox.setPrefWidth(775);
        vBox.setPrefHeight(320);
        for (Message msg : conversation) {
            if (msg.getIdUser1().equals(user1.getID())) {
                Label labelMessage = new Label();
                String cssMessage = this.getClass().getResource("/com/example/reteadesocializaregui/css/messagestyle.css").toExternalForm();
                labelMessage.getStylesheets().add(cssMessage);
                Label labelLastName = new Label();
                String cssLastName = this.getClass().getResource("/com/example/reteadesocializaregui/css/messagenamestyle.css").toExternalForm();
                labelLastName.getStylesheets().add(cssLastName);
                Label labelSpace = new Label();
                labelSpace.setText("");
                labelMessage.setText(msg.getContent());
                labelLastName.setText(user1.getLastName() + ":");
                VBox vBoxMessage = new VBox();
                vBoxMessage.getChildren().add(labelLastName);
                vBoxMessage.getChildren().add(labelMessage);
                vBoxMessage.getChildren().add(labelSpace);
                vBoxMessage.setAlignment(Pos.BASELINE_RIGHT);
                vBox.getChildren().add(vBoxMessage);
            } else {
                Label labelMessage = new Label();
                String cssMessage = this.getClass().getResource("/com/example/reteadesocializaregui/css/messagestyle.css").toExternalForm();
                labelMessage.getStylesheets().add(cssMessage);
                Label labelLastName = new Label();
                String cssLastName = this.getClass().getResource("/com/example/reteadesocializaregui/css/messagenamestyle.css").toExternalForm();
                labelLastName.getStylesheets().add(cssLastName);
                Label labelSpace = new Label();
                labelSpace.setText("");
                labelMessage.setText(msg.getContent());
                labelLastName.setText(user2.getLastName() + ":");
                VBox vBoxMessage = new VBox();
                vBoxMessage.getChildren().add(labelLastName);
                vBoxMessage.getChildren().add(labelMessage);
                vBoxMessage.getChildren().add(labelSpace);
                vBox.getChildren().add(vBoxMessage);
            }
            scrollPaneConversation.setContent(vBox);
            scrollPaneConversation.setVvalue(1D);
        }
    }

    public void update() {
        reload_conversation();
    }

}
