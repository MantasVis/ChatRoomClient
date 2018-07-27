package com.chatroom.client;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable
{
    Client client;

    //FXML Objects
    @FXML
    private JFXTextField usernameField;
    @FXML
    private TextArea onlineUserArea, chatTextArea, inputTextArea;

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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        client = new Client(null, chatTextArea, inputTextArea);
        client.ableToType(false);
    }
}
