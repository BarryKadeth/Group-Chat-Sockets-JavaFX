package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.application.Platform;

/**
 *
 * This is a thread for every client / main connection
 */
public  class ClientConnection implements Runnable {

    Socket socket;
    Main server;
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;
    String username = "";
    boolean usernameReceived = false;
    
	//Key for encryption and decryption
	private String password = "Bar12345Bar12345";
    private SecretKey key = new SecretKeySpec(password.getBytes(), "AES");
    

    public ClientConnection(Socket socket, Main server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try { 
            // Create input and output streams
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
            //The very first input from the client is the username
            //To add the password later
            String userDetails = input.readUTF();
  
            //Need to decipher here 
            Encryption encrypter = new Encryption(key);
            String decryptedMessage = encrypter.decrypt(userDetails);
            
            
            if (!usernameReceived) {
                Platform.runLater(() -> {                  	
                	username = decryptedMessage;
                });
            	usernameReceived = true;
            	//Send message to everyone already connected that this user has connected
            for (int i = 0; i < server.connectionList.size(); i++) {
            	String messageConnections = server.connectionList.get(i).toString();
            	//Encrypt the message
            	Encryption connectionsEncrypter = new Encryption(key);
                String encryptedMessageConnections = connectionsEncrypter.encrypt(messageConnections);
            	output.writeUTF(encryptedMessageConnections);
            	}           	
            }

            
            //This is for sending messages portion
            while (true) {
                //Receive and send client messages
                String message = input.readUTF();
                
          //Decipher the message here
                Encryption messageDecrypter = new Encryption(key);
                String decryptedMessage1 = messageDecrypter.decrypt(message);
                System.out.println(decryptedMessage1);
                
                //save to database
                DatabaseManager.addMessage(new Date().toString(), 
                		username, "everyone", decryptedMessage1);

              //Send the message back to everyone: the encrypted form 
                server.broadcast(message);
                System.out.println("ClientConnections: " + username);
                
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {                    
                    server.messageList.add(decryptedMessage1);
                });
                
            }
            
       
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            try {
//                socket.close();
//            } 
//            catch (IOException ex) {
//                ex.printStackTrace();
//            }

        }

    }

    //send message back to client
    public void sendMessage(String message) {
          try {
        	  
    //This portion will need to be encrypted when sent back 
        	  
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
       
    }
    
    @Override
    public String toString () {
    	return (username);
    }
    
    public void setUsername (String username) {
    	this.username = username;
    }

}
