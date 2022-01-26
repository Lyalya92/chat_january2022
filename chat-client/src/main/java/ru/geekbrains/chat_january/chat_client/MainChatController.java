package ru.geekbrains.chat_january.chat_client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainChatController implements Initializable {
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


    public void click(ActionEvent actionEvent) {
        if (btnConnect.getText().equals("Connect")) {
            btnConnect.setText("Disconnect");
        } else {
            btnConnect.setText("Connect");
        }
    }

    public void mockAction(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void sendMessage(ActionEvent actionEvent) {
        var message = inputField.getText();
        if (message.isBlank()) {
            return;
        }
        if (contactList.getSelectionModel().getSelectedItem()!=null) {
            message = contactList.getSelectionModel().getSelectedItem().toString() + ": " + message;
        } else {
            message = "ALL: " + message;
        }
        mainChatArea.appendText(message + System.lineSeparator());
        inputField.clear();
    }

    public void showHelp(ActionEvent actionEvent) {
    }

    public void showAbout(ActionEvent actionEvent) {
    }

    public void settings(ActionEvent actionEvent) {
    }

    public void login(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var contacts = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            contacts.add("Contact#" + (i + 1));
        }
        contactList.setItems(FXCollections.observableList(contacts));
    }
}
