package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.stage.Stage;
import javafxsocketprogramming.ConnectionUtil;
import javafxsocketprogramming.TaskClientConnection;
//import threading.ServerThread;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * This is the GUI for the Messenger Client area  
 * Have removed password capabilities for now
 * 
 * @author BaraMeyMey
 *
 */
public class MainUser extends Application {
	
	//Area to add the messages along with viewing them 
	ObservableList<String> messageList = FXCollections.observableArrayList();
	ListView<String> messageListView = new ListView<String>();
	ServerSocket serverSocket;
	
	//To provide a list of available users to be connected 
	ListView<String> userListView = new ListView<String>();
	ObservableList<String> userList = FXCollections.observableArrayList();
	String username;
	
	DataOutputStream output = null;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			messageList.add("Hi there");
			userList.add("Group chat");
			
		//Left side text and buttons
			BorderPane root = new BorderPane();
			//NOTE: Possibly change this to current active IP Address
			Text ipText = new Text("IP Address: enter to connect");
			Text portNumberText = new Text("Enter Port to connect \n");
			
			Text ipAddressText = new Text("IP Address:");
			TextField ipAddressInput = new TextField ();
			Text portText = new Text("Port Number:");
			TextField portInput = new TextField ();
			Text usernameText = new Text("Username: ");
			TextField usernameInput = new TextField ();
//			Text passwordText = new Text("Password: ");
//			PasswordField passwordInput = new PasswordField ();
			
			Button connectButton = new Button ("Connect to Server");
			
			Text userText = new Text("\n Users:");
			userListView.setItems(userList);
			
			
		//Organizing all the left side buttons:
			VBox leftPane = new VBox (ipText, portNumberText, ipAddressText, 
					ipAddressInput, portText, portInput, usernameText, 
					usernameInput, 
//					passwordText, passwordInput, 
					connectButton,
					userText, userListView);
			
			leftPane.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
			leftPane.setAlignment(Pos.TOP_LEFT);
			leftPane.setPadding(new Insets(10,10,10,10));
			root.setLeft(leftPane);
			
		//Middle area: Display text messages 
			
			messageListView.setItems(messageList);
			
			TextField inputMessage = new TextField();
			inputMessage.setPromptText("Enter message: ");
			Button sendButton = new Button("Send");
			HBox messageHBox = new HBox(inputMessage, sendButton);
			HBox.setHgrow(inputMessage, Priority.ALWAYS);
			
			VBox messageArea = new VBox (messageListView, messageHBox);
			root.setCenter(messageArea);
			
		//Setup of buttons: 
			//Start button: General code from class example
			connectButton.setOnAction(value -> {
				String ipAddress = ipAddressInput.getText();
				int port = Integer.valueOf(portInput.getText());
				messageList.clear();
				username = usernameInput.getText();
				
				try {
					//Connection to server
					Socket clientSocket = new Socket (ipAddress, port);
					messageList.add("Connected to server");
					System.out.println("Connected to server");
					
					//Output Stream to server
					output = new DataOutputStream (clientSocket.getOutputStream());
					
							
			//		messageList.add(username + " has entered the chat");
					
					System.out.println (username + " has entered the chat");
					
					//Thread to read from server 
					ServerConnection connection = new ServerConnection(clientSocket, this, username);
					Thread thread = new Thread (connection);
					thread.start();
								
				//	clientSocket.close();  //Just added this now 

				} catch (IOException e) {
					messageList.add("Unable to connect with server");
					e.printStackTrace();
				}
				
			});
			
			//Send message on click of send button
			sendButton.setOnAction(value -> {
				
				try {
					//Attach Username to beginning of message and receive from textInput
					String message = username + ": " + inputMessage.getText().trim();
					
					
					//If message is empty, don't send
					if (message.length() == 0) {
						return;
					}
					
			//Encrypt here
					
					//Send message to server
					output.writeUTF(message);
					output.flush();
					
					//Clear input for next message
					inputMessage.clear();
					
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e);
				}
				
			});
			

			
			
			//Setting up scene of Javafx
			primaryStage.setTitle("Client Messenger App");
			Scene scene = new Scene(root,700,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
