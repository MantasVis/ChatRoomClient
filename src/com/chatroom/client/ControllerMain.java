package com.chatroom.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerMain
{
    Client client;

    //FXML Objects
    @FXML
    private TextArea onlineUserArea, chatTextArea, inputTextArea;

    private boolean connected = false;

    //FXML Actions
    @FXML
    void sendButton(ActionEvent event)
    {
        String message = inputTextArea.getText();
        sendMessage(message);
    }

    @FXML
    void enterKey(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            String message = inputTextArea.getText();
            sendMessage(message);
            inputTextArea.clear();
        }
    }

    @FXML
    void connect(ActionEvent event) throws IOException
    {
        if (connected)
        {
            Alert alert = new Alert(Alert.AlertType.NONE, "You are already connected", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/connectMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Connect to server");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ControllerConnect controllerConnect = loader.getController();
            ControllerMain controllerMain = this;
            controllerConnect.setControllerMain(controllerMain);
            stage.show();
        }
    }

    @FXML
    void disconnect(ActionEvent event)
    {
        if (connected)
        {
            client.sendCommand("END_CONNECTION");
            client.setDisconnected();
            onlineUserArea.clear();
            connected = false;
        }
    }

    public void sendMessage(String message)
    {
        if (!message.equals(""))
        {
            client.sendMessage(message);
        }
        inputTextArea.clear();
    }

    public void start(String ip, String username)
    {
        client = new Client(ip, chatTextArea, inputTextArea, onlineUserArea, username);
        client.ableToType(false);
        connected = true;

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> client.start());
    }
}
