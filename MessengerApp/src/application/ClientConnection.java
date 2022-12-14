package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
    String username = "unknown";
    boolean usernameReceived = false;
    

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
            if (!usernameReceived) {
            	username = userDetails;
            	usernameReceived = true;
            	for (int i = 0; i < server.connectionList.size(); i++) {
            		output.writeUTF(server.connectionList.get(i).toString());
            	}
            	
            }

            
            //This is for sending messages portion
            while (true) {
                //Receive and send client messages
                String message = input.readUTF();
                
          //Decipher the message here
                
                server.broadcast(message);
                
                System.out.println("ClientConnections: " + username);
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {                    
                    server.messageList.add(message);
                });
                
            }
            
       
        } catch (IOException ex) {
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
