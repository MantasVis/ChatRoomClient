package com.chatroom.client;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerMain
{
    Client client;

    //FXML Objects
    @FXML
    private TextArea onlineUserArea, chatTextArea, inputTextArea;
    @FXML
    private TextField usernameField;

    //FXML Actions
    @FXML
    void sendButton(ActionEvent event)
    {
        String message = inputTextArea.getText();
        if (!message.equals(""))
        {
            client.sendMessage(message);
        }
        inputTextArea.clear();
    }

    @FXML
    void connect(ActionEvent event)
    {
        String username = usernameField.getText();
        start(username);
    }

    @FXML
    void disconnect(ActionEvent event) {
        client.sendCommand("END_CONNECTION");
        client.setDisconnected();
        onlineUserArea.clear();
    }

    public void start(String username)
    {
        client = new Client("192.168.0.103", chatTextArea, inputTextArea, onlineUserArea, username);
        client.ableToType(false);

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> client.start());
    }
}
