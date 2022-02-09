package ru.geekbrains.chat_january.chat_client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.geekbrains.chat_january.chat_client.network.MessageProcessor;
import ru.geekbrains.chat_january.chat_client.network.NetworkService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainChatController implements Initializable, MessageProcessor {
    public static final String REGEX = "%!%";

    private  String nickname;
    private NetworkService networkService;

    @FXML
    public VBox loginPanel;

    @FXML
    public TextField loginField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextArea mainChatArea;

    @FXML
    public Button btnConnect;

    @FXML
    public ListView contactList;

    @FXML
    public TextField inputField;

    @FXML
    public Button btnSend;

    @FXML
    public VBox mainChatPanel;

    public void connectToServer (ActionEvent actionEvent) {

    }

    public void disconnectToServer (ActionEvent actionEvent) {

    }

    public void mockAction(ActionEvent actionEvent) {

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void showHelp(ActionEvent actionEvent) {

    }

    public void showAbout(ActionEvent actionEvent) {

    }

    public void click(ActionEvent actionEvent) {
        if (btnConnect.getText().equals("Connect")) {
            connectToServer(actionEvent);
            btnConnect.setText("Disconnect");

        } else {
            btnConnect.setText("Connect");
            disconnectToServer(actionEvent);
        }
    }


    public void sendMessage(ActionEvent actionEvent) {
        var message = inputField.getText();
        if (message.isBlank()) {
            return;
        }
        String recipient = (String) contactList.getSelectionModel().getSelectedItems().get(0);
        String messageType;
        String outMessage;
        if (recipient.equals("ALL")) {
            messageType = "/broadcast";
            outMessage = messageType + REGEX + nickname + REGEX + message;
        } else {
            messageType = "/private";
            outMessage = messageType + REGEX + recipient + REGEX + message;
        }
        networkService.sendMessage(outMessage);
        inputField.clear();
    }



    public void settings(ActionEvent actionEvent) {
    }

    public void login(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.networkService = new NetworkService(this);

    }

    @Override
    public void processMessage(String message) {
        Platform.runLater(()-> parseIncomingMessage(message));
    }

    private void parseIncomingMessage(String message) {
        var splitMessage = message.split(REGEX);
        switch (splitMessage[0]) {
            case "/auth_ok" :
                this.nickname = splitMessage[1];
                loginPanel.setVisible(false);
                mainChatPanel.setVisible(true);
                break;
            case "/error" :
                showError(splitMessage[1]);
                System.out.println("got error " + splitMessage);
                break;
            case "/list" :
                var contacts = new ArrayList<String>();
                contacts.add("ALL");
                for (int i = 1; i < splitMessage.length; i++) {
                    contacts.add(splitMessage[i]);
                }
                contactList.setItems(FXCollections.observableList(contacts));
                contactList.getSelectionModel().selectFirst();
                break;
            default:
                mainChatArea.appendText(splitMessage[0] + System.lineSeparator());
                break;
        }
    }

    private void showError (String message){
        var alert = new Alert(Alert.AlertType.ERROR,
                "An error occured: " + message,
                ButtonType.OK);
        alert.showAndWait();
    }

    public void sendAuth(ActionEvent actionEvent) {
        var login = loginField.getText();
        var password = passwordField.getText();
        if (login.isBlank() || password.isBlank()) {
            return;
        }

        var message = "/auth" + REGEX + login + REGEX + password;

        if (!networkService.isConnected()) {
            try {
                networkService.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        networkService.sendMessage(message);
    }
}
