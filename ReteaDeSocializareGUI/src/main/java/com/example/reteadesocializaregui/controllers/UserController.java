package com.example.reteadesocializaregui.controllers;

import com.example.reteadesocializaregui.HelloApplication;
import com.example.reteadesocializaregui.domain.Friendship;
import com.example.reteadesocializaregui.domain.User;
import com.example.reteadesocializaregui.domain.UserDTO;
import com.example.reteadesocializaregui.exceptions.DuplicateException;
import com.example.reteadesocializaregui.exceptions.LackException;
import com.example.reteadesocializaregui.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UserController extends Observer{
    private Service service;

    private User user;

    private final ObservableList<UserDTO> friends = FXCollections.observableArrayList();
    private final ObservableList<UserDTO> requests = FXCollections.observableArrayList();

    @FXML
    private Label labelUser;

    @FXML
    private Label labelFriendAdded;

    @FXML
    private Label labelFriendDeleted;

    @FXML
    private Label labelAcceptFriendRequest;

    @FXML
    private Label labelFriendNotSelected;

    @FXML
    private TableView<UserDTO> tableFriendList;

    @FXML
    private TableView<UserDTO> tablePendingList;

    @FXML
    private TableColumn<UserDTO, String> columnFirstNameL;

    @FXML
    private TableColumn<UserDTO, String> columnLastNameL;

    @FXML
    private TableColumn<UserDTO, String> columnDateL;


    @FXML
    private TableColumn<UserDTO, String> columnFirstNameP;

    @FXML
    private TableColumn<UserDTO, String> columnLastNameP;

    @FXML
    private TableColumn<UserDTO, String> columnDateP;

    @FXML
    private Button buttonLogout;

    @FXML
    private TextField textNameFriend;

    @FXML
    private Button buttonAddFriend;

    @FXML
    private Button buttonDeleteFriend;

    @FXML
    private Button buttonAcceptRequest;

    @FXML
    private Button buttonWithdrawFriendRequest;

    @FXML
    private Button buttonChat;

    @FXML
    private ImageView imageView;

    public void initialise(){
        labelUser.setText("Welcome back, " + user.getFirstName() + " " + user.getLastName() + "!");
        try {
            Image image = new Image(new FileInputStream("D:\\Facultate\\Metode avansate de programare\\ReteaDeSocializareGUI\\src\\main\\resources\\com\\example\\reteadesocializaregui\\images\\profile_picture.png"));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        columnFirstNameL.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastNameL.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnDateL.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableFriendList.setItems(friends);
        columnFirstNameP.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastNameP.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnDateP.setCellValueFactory(new PropertyValueFactory<>("date"));
        tablePendingList.setItems(requests);
        friend_list();
    }

    public void friend_list(){
        Iterable<Friendship> friendships = service.findAllFriendships();
        for (Friendship friendship : friendships) {
            if (friendship.getIdUser1() == user.getID()) {
                String[] stringArr = friendship.getFriendsFrom().toString().split(".", 2);
                String date = stringArr[0];
                switch (friendship.getStatus()) {
                    case ACCEPTED ->
                            friends.add(new UserDTO(friendship.getID(), service.findOne(friendship.getIdUser2()).getFirstName(), service.findOne(friendship.getIdUser2()).getLastName(), friendship.getFriendsFrom().toString()));
                }
            } else if (friendship.getIdUser2() == user.getID()) {
                switch (friendship.getStatus()){
                    case ACCEPTED ->
                            friends.add(new UserDTO(friendship.getID(), service.findOne(friendship.getIdUser1()).getFirstName(), service.findOne(friendship.getIdUser1()).getLastName(), friendship.getFriendsFrom().toString()));
                    case PENDING ->
                            requests.add(new UserDTO(friendship.getID(), service.findOne(friendship.getIdUser1()).getFirstName(), service.findOne(friendship.getIdUser1()).getLastName(), friendship.getFriendsFrom().toString()));
                }
            }
        }
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onLogoutButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) buttonLogout.getScene().getWindow();
        HelloController helloController = fxmlLoader.getController();
        helloController.setService(this.service);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public int onAddFriendButtonClick() {
        try{
            labelAcceptFriendRequest.setText("");
            labelFriendAdded.setText("");
            labelFriendDeleted.setText("");
            labelFriendNotSelected.setText("");
            String[] stringArr = textNameFriend.getText().split(" ", 2);
            String firstName = stringArr[0];
            String lastName = stringArr[1];
            Iterable<User> users = service.findAll();
            int id = service.generate_id();
            int exist = 0;
            for(User u : users){
                if(u.getFirstName().equals(firstName) && u.getLastName().equals(lastName)){
                    try{
                        service.save_friend((long) id, user.getID(), u.getID());
                        exist = 1;
                    }
                    catch (DuplicateException e){
                        labelFriendAdded.setText("Aceasta cerere de prietenie exista deja!\n");
                        return 0;
                    }
                }
            }
            if(exist == 0){
                Alert alertDoesntExist = new Alert(Alert.AlertType.ERROR);
                alertDoesntExist.setContentText("Utilizatorul nu a fost gasit!\n");
                alertDoesntExist.show();
            }
            else labelFriendAdded.setText("Prietenul a fost adaugat!\n");
        }
        catch (ArrayIndexOutOfBoundsException exception){
            Alert alertArrayIndex = new Alert(Alert.AlertType.ERROR);
            alertArrayIndex.setContentText("Utilizatorul nu a fost gasit!\n");
            alertArrayIndex.show();
        }
        return 0;
    }

    public void onDeleteFriendButtonClick(){
        try{
            labelAcceptFriendRequest.setText("");
            labelFriendAdded.setText("");
            labelFriendDeleted.setText("");
            labelFriendNotSelected.setText("");
            UserDTO userDTO = tableFriendList.getSelectionModel().getSelectedItem();
            Friendship friendship = service.findOneFriendship(userDTO.getId());
            service.delete_friend(friendship.getIdUser1(),friendship.getIdUser2());
            tableFriendList.getItems().removeAll(tableFriendList.getSelectionModel().getSelectedItem());
            labelFriendDeleted.setText("Prietenul a fost sters!\n");
        }
        catch (NullPointerException exception){
            Alert alertNullPointer = new Alert(Alert.AlertType.ERROR);
            alertNullPointer.setContentText("Nu ati selectat niciun utilizator din tabel!\n");
            alertNullPointer.show();
        }
    }

    public void onAcceptFriendRequestButtonClick(){
        try{
            labelAcceptFriendRequest.setText("");
            labelFriendAdded.setText("");
            labelFriendDeleted.setText("");
            labelFriendNotSelected.setText("");
            UserDTO userDTO = tablePendingList.getSelectionModel().getSelectedItem();
            Friendship friendship = service.findOneFriendship(userDTO.getId());
            service.update_friendship_status(friendship);
            labelAcceptFriendRequest.setText("Ai acceptat cererea de prietenie!\n");
        }
        catch (NullPointerException exception){
            Alert alertNullPointer = new Alert(Alert.AlertType.ERROR);
            alertNullPointer.setContentText("Nu ati selectat niciun utilizator din tabel!\n");
            alertNullPointer.show();
        }
    }

    public void onWithdrawFriendRequestClick(){
        try{
            labelAcceptFriendRequest.setText("");
            labelFriendAdded.setText("");
            labelFriendDeleted.setText("");
            labelFriendNotSelected.setText("");
            int deleted = 0;
            String[] stringArr = textNameFriend.getText().split(" ", 2);
            String firstName = stringArr[0];
            String lastName = stringArr[1];
            Iterable<User> users = service.findAll();
            for(User u : users){
                if(u.getFirstName().equals(firstName) && u.getLastName().equals(lastName)){
                    service.delete_friend(user.getID(), u.getID());
                    deleted = 1;
                }
            }
            labelFriendAdded.setText("Cererea de prietenie a fost retrasa cu succes!\n");
        }
        catch(ArrayIndexOutOfBoundsException exception){
            Alert alertArrayIndex = new Alert(Alert.AlertType.ERROR);
            alertArrayIndex.setContentText("Utilizatorul nu a fost gasit!\n");
            alertArrayIndex.show();
        }
        catch (LackException exception){
            Alert lackException = new Alert(Alert.AlertType.ERROR);
            lackException.setContentText(exception.getMessage());
            lackException.show();
        }
    }

    public int onChatButtonClick() throws IOException {
        labelAcceptFriendRequest.setText("");
        labelFriendAdded.setText("");
        labelFriendDeleted.setText("");
        labelFriendNotSelected.setText("");
        UserDTO userDTO = tableFriendList.getSelectionModel().getSelectedItem();
        if(userDTO == null){
            labelFriendNotSelected.setText("Selectati din tabel un prieten cu care doriti sa vorbiti!\n");
            return 0;
        }
        Friendship friendship = service.findOneFriendship(userDTO.getId());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) buttonLogout.getScene().getWindow();
        ChatController chatController = fxmlLoader.getController();
        chatController.setService(this.service);
        chatController.setUser1(user);
        if(user.equals(service.findOne(friendship.getIdUser1()))){
            chatController.setUser2(service.findOne(friendship.getIdUser2()));
        }
        else chatController.setUser2(service.findOne(friendship.getIdUser1()));
        chatController.initialise();
        stage.setResizable(false);
        stage.setTitle("Conversation");
        stage.setScene(scene);
        return 0;
    }

    @Override
    void update() {
        friends.clear();
        requests.clear();
        friend_list();
    }
}
