package application;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
    boolean firstLogin = true;
    
	//Key for encryption and decryption
	private String password = "Bar12345Bar12345";
    private SecretKey key = new SecretKeySpec(password.getBytes(), "AES");

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
            		String message = "     " + username;
            		
            	//Send an encrypted username
            		Encryption encrypter = new Encryption(key);
            		String encryptedMessage = encrypter.encrypt(message);
            		client.output.writeUTF(encryptedMessage);
            		client.output.writeUTF(encryptedMessage); // sent twice
            		usernameSent = true;
            	}
            	
            	
                //Create data input stream
                input = new DataInputStream(socket.getInputStream());


                //get input from the server
                String message = input.readUTF();
                
          //Decipher here
                
                Encryption encrypter = new Encryption(key);
                String decryptedMessage = encrypter.decrypt(message);
                
                
                
                
                //5 spaces is a key that the message is a user name
                if (decryptedMessage.contains("     ") && decryptedMessage.contains(username) &&
                		firstLogin) {
                	//Do nothing
                	firstLogin = false;
                } else if (decryptedMessage.contains("     ")) {
                	
                    Platform.runLater(() -> {                    
                    	client.userList.add(decryptedMessage);
                    	client.messageList.add(decryptedMessage + " is here");
                    });

                	
                	
                } else {

                    //append message of the Text Area of UI (GUI Thread)
                    Platform.runLater(() -> {
                        //display the message in the text area
                        client.messageList.add(decryptedMessage + "\n");
                    }); 
                }
             
            } catch (Exception ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
