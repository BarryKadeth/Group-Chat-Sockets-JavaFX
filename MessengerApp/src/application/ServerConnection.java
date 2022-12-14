package application;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;

/**
 * It is used to get input from server simultaneously
 */
public class ServerConnection implements Runnable {
    //private variables
    Socket socket;
    MainUser client;
    DataInputStream input;
    String username;
    boolean usernameSent = false;

    //constructor
    public ServerConnection (Socket socket, MainUser client, String username) {
        this.socket = socket;
        this.client = client;
        this.username = username;
    }

    @Override 
    public void run() {    	
    	
        //continuously loop it
        while (true) {
            try {
            	
            	//This is to send the username to the server
            	//to add to ClientConnection class
            	if (!usernameSent) {
            		client.output.writeUTF(username);
            		usernameSent = true;
            	}
            	
            	
                //Create data input stream
                input = new DataInputStream(socket.getInputStream());

                //get input from the client
                String message = input.readUTF();

                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {
                    //display the message in the textarea
                    client.messageList.add(message + "\n");
                });
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
