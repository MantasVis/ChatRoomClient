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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatroom.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Instant messenger client");
            Scene scene = new Scene(root, 740, 520);
            scene.getStylesheets().add("/redLounge.css");
            primaryStage.setScene(scene);
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
