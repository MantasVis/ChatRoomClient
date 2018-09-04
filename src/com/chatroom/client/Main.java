package com.chatroom.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/chatroom.fxml"));
            primaryStage.setTitle("Instant messenger client");
            primaryStage.setScene(new Scene(root, 723, 520));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
