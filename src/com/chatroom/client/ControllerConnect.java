package com.chatroom.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

        if (username.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please choose a username", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
        else if (ip.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please enter a valid IP", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
        else if(username.contains("Œ"))
        {
            Alert alert = new Alert(Alert.AlertType.NONE, "Illegal character in the username", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
        else
        {
            controllerMain.start(ip, username);

            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        }
    }

   public void setControllerMain(ControllerMain controllerMain)
   {
       this.controllerMain = controllerMain;
   }

}
