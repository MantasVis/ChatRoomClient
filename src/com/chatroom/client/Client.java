package com.chatroom.client;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private TextArea inputTextArea;
    private TextFlow chatTextArea, onlineUserArea;
    private String message = "";
    private String serverIP, username;
    private Socket connection;
    private boolean disconnected = false;

    public Client(String host, TextFlow chatTextArea, TextArea inputTextArea, TextFlow onlineUserArea, String username)
    {
        this.chatTextArea = chatTextArea;
        this.inputTextArea = inputTextArea;
        this.onlineUserArea = onlineUserArea;
        this.serverIP = host;
        this.username = username;
    }

    public void start()
    {
        try
        {
            connectToServer();
            setUpStreams();
            whileChatting();
        }
        catch (EOFException e)
        {
            showMessage("\nClient terminated the connection.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeClient();
        }
    }

    /**
     * Connect to server
     */
    private void connectToServer() throws IOException
    {
        try
        {
            showMessage("Attempting connection to server... \n");
            connection = new Socket(InetAddress.getByName(serverIP), 6789);
            showMessage("Connected to: " + connection.getInetAddress().getHostName());
        }
        catch (ConnectException e)
        {
            showMessage("Failed to connect to server!\n");
        }
        catch (UnknownHostException e)
        {
            showMessage("Incorrect server IP address. Please try again...");
        }
    }

    /**
     * Set up stream to send and retrieve data
     */
    private void setUpStreams() throws IOException
    {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        sendCommand("START_CONNECTION;" + username);
    }

    /**
     * Receives messages
     */
    private void whileChatting() throws IOException
    {
        ableToType(true);

        do
        {
            try
            {
                message = (String) input.readObject();

                if (message.startsWith("Œ"))
                {
                    processCommand(message);
                }
                else
                {
                    showMessage("\n" + message);
                }
            }
            catch (ClassNotFoundException e)
            {
                showMessage("\nUser didn't send a string");
            }
        }
        while (!disconnected);
    }

    private void processCommand(String command)
    {
        if (command.contains("USER_LIST"))
        {
            Text userList = new Text(command.split(":")[1]);
            Platform.runLater(() -> onlineUserArea.getChildren().add(userList));
        }
    }

    /**
     * Close streams and sockets before shutting down the client
     */
    private void closeClient()
    {
        ableToType(false);

        try
        {
            output.close();
            input.close();
            connection.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sends messages
     */
    public void sendMessage(String message)
    {
        try
        {
            output.writeObject(username + ": " + message);
            output.flush();
            inputTextArea.clear();
        }
        catch (IOException e)
        {
            Text t1 = new Text("\nError sending message");
            chatTextArea.getChildren().add(t1);
        }
    }

    /**
     * Sends commands
     */
    public void sendCommand(String command)
    {
        try
        {
            output.writeObject("Œ" + command);
            output.flush();
            inputTextArea.clear();
        }
        catch (IOException e)
        {
            Text t1 = new Text("\nError sending command");
            chatTextArea.getChildren().add(t1);
        }
    }

    public void setDisconnected()
    {
        disconnected = true;
    }

    /**
     * Updates the chat window
     */
    private void showMessage(final String text)
    {
        Text t1 = new Text(text);
        Platform.runLater(() -> chatTextArea.getChildren().add(t1));
    }

    /**
     * Allows and disallows typing
     */
    public void ableToType(final boolean allowed)
    {
        Platform.runLater(() -> inputTextArea.setEditable(allowed));
    }
}
