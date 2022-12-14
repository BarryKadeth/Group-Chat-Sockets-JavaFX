package application;
	
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * This is the GUI for the Messenger Server 
 * 
 * @author BaraMeyMey
 *
 */
public class Main extends Application {
	//Not used yet: 
	final ObservableList<Client> ipAddressList = FXCollections.observableArrayList();
	
	//List of connected clients: this is not used yet
	final ObservableList<Client> clientList = FXCollections.observableArrayList();
	ListView<Client> clientListView = new ListView<Client>();
	
	//List of connected clients: currently used
	final ObservableList<ClientConnection> connectionList = FXCollections.observableArrayList();
	
	
	//Message area on GUI
	ObservableList<String> messageList = FXCollections.observableArrayList();
	ListView<String> messageListView = new ListView<String>();
	ServerSocket serverSocket;
	
	//User list area on the GUI
	ListView<String> userListView = new ListView<String>();
	private ObservableList<String> userList = FXCollections.observableArrayList();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			messageList.add("Hi there");
			userList.add("Bazza");
			
		//Left side text and buttons
			BorderPane root = new BorderPane();
			//NOTE: Possibly change this to current active IP Address
			Text ipText = new Text("IP Address: localhost");
			Text portNumberText = new Text("Enter Port to connect \n");
			
			Text portText = new Text("Port Number:");
			TextField portInput = new TextField ();
			//Server start and pause buttons:
			Text serverControlsText = new Text("Server Controls:");
			Button startButton = new Button ("Start");
			Button stopButton = new Button ("Stop");
			stopButton.setVisible(false); //Hide stop button initially
			Text space = new Text (" ");
			HBox serverControls = new HBox (startButton, space, stopButton);
			Text userText = new Text("\n Users:");
			userListView.setItems(userList);
			
			
		//Organizing all the left side buttons:
			VBox leftPane = new VBox (ipText, portNumberText, portText, portInput, serverControlsText, 
					serverControls, userText, userListView);
			
			
			leftPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			leftPane.setAlignment(Pos.TOP_LEFT);
			leftPane.setPadding(new Insets(10,10,10,10));
			root.setLeft(leftPane);
			
		//Middle area: Display text messages 
			
			messageListView.setItems(messageList);
			root.setCenter(messageListView);
			
			
		//Setup of buttons: 
			//Start button: General code from class example
			startButton.setOnAction(value -> {
				
				int port = Integer.valueOf(portInput.getText());
				if(portInput.getText().equals("")) {
					messageList.clear();
					messageList.add("Invalid port number entered");
					portNumberText.setText("Invalid port entered \n");
					
				}
				else {
					startButton.setVisible(false);
					stopButton.setVisible(true);
					messageList.clear();
					
				//Create a thread that will be forever running until stopButton pressed
			        new Thread(() -> {
			    		try {
			    			serverSocket = new ServerSocket(port);
			    			portNumberText.setText("Port Number: " + port + "\n");
			    			System.out.println("Server started on port " + port);
			    			//runLater used as errors are thrown as the 
			    			//UI is being updated in other thread than JavaFX application
			    			Platform.runLater(()
			    					-> messageList.add("Server started on port " + port + 
			    							" at " + new Date()));
			    			
			    			
			    		//This will be used to create new connections to the server: forever open			    			
			    			try {
			    				while (true) {
			    					//Listening to a new client request
			    					//Then add to list 
			    					Socket clientSocket = serverSocket.accept();
			    					
			    					new ServerThread(clientSocket).start(); //for simple message to client
			    					ClientConnection connection = new ClientConnection (clientSocket, this);
			    					connectionList.add(connection); //add to list
			    					System.out.println(connection);
			    					
			    					//New Thread
			    					Thread thread = new Thread (connection);
			    					thread.start();
			    					
			    					
			    				}
			    			}
			    			finally {
			    				
			    			//	serverSocket.close();
			    			}
			    			
			    			
			    		} catch (Exception e) {
			    			e.printStackTrace();
			    		}
			        }).start();
				}
				
				
			});
			
			//Stop button: 
			stopButton.setOnAction(value -> {
				stopButton.setVisible(false);
				startButton.setVisible(true);
				portNumberText.setText("Enter port to connect \n");
				
				try {
					if (!serverSocket.isClosed()) {
						messageList.add("The server is closed");
					} else {
						messageList.add("There is no server to close");
					}
					serverSocket.close();
					Platform.runLater(()
							-> System.out.println("Server has been closed"));
				} catch (Exception e) {
					System.out.println("There was no server to close");
					e.printStackTrace();
				}

			});
			
			
			
			
			
			primaryStage.setTitle("Server GUI");
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
	
	
	//This has been copied from source for now:
    //send message to all connected clients
    public void broadcast(String message) {
        for (ClientConnection clientConnection : this.connectionList) {
            clientConnection.sendMessage(message);
        }
    }
	
}
