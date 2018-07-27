package com.chatroom.client;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private TextArea chatTextArea, inputTextArea;
    private String message = "";
    private String serverIP;
    private Socket connection;

    public Client(String host, TextArea chatTextArea, TextArea inputTextArea)
    {
        this.chatTextArea = chatTextArea;
        this.inputTextArea = inputTextArea;
        this.serverIP = host;
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
        showMessage("Attempting connection to server... \n");
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
        showMessage("Connected to: " + connection.getInetAddress().getHostName());
    }

    /**
     * Set up stream to send and retrieve data
     */
    private void setUpStreams() throws IOException
    {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        showMessage("\nStreams are now set up! \n");
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
                showMessage("\n" + message);
            }
            catch (ClassNotFoundException e)
            {
                showMessage("\nUser didn't send a string");
            }
        }
        while (!message.equals("SERVER: END"));
    }

    /**
     * Close streams and sockets before shutting down the client
     */
    private void closeClient()
    {
        showMessage("\nClosing connections... \n");
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
            output.writeObject("USER: " + message);
            output.flush();
            showMessage("\nUSER: " + message);
            inputTextArea.clear();
        }
        catch (IOException e)
        {
            chatTextArea.appendText("\nError sending message");
        }
    }

    /**
     * Updates the chat window
     */
    private void showMessage(final String text)
    {
        Platform.runLater(() -> chatTextArea.appendText(text));
    }

    /**
     * Allows and disallows typing
     */
    public void ableToType(final boolean allowed)
    {
        Platform.runLater(() -> inputTextArea.setEditable(allowed));
    }
}