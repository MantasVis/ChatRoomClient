package com.chatroom.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ControllerConnect
{
    @FXML
    private TextField usernameField, ipField;
    @FXML
    private AnchorPane ap;

    private ControllerMain controllerMain;

    @FXML
    void cancelConnect(ActionEvent event)
    {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmConnect(ActionEvent event)
    {
        String username = usernameField.getText();
        String ip = ipField.getText();

        controllerMain.start(ip, username);
    }

   public void setControllerMain(ControllerMain controllerMain)
   {
       this.controllerMain = controllerMain;
   }

}
