package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	public TextArea textDisplay;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
		//Left side text and buttons
			BorderPane root = new BorderPane();
			//NOTE: Possibly change this to current active IP Address
			Text text1 = new Text("IP Address: localhost");
			Text text01 = new Text("Enter Port to connect \n");
			
			Text text001 = new Text("Port Number:");
			TextField textBox1 = new TextField ();
			//Server start and pause buttons:
			Text text2 = new Text("Server controls:");
			Button button1 = new Button ("Start");
			Button button2 = new Button ("Stop");
			Text space = new Text (" ");
			HBox serverControls = new HBox (button1, space, button2);
			
			
		//Organizing all the left side buttons:
			VBox leftPane = new VBox (text1, text01, text001, textBox1, text2, serverControls);
			
			
			leftPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			leftPane.setAlignment(Pos.TOP_LEFT);
			leftPane.setPadding(new Insets(10,10,10,10));
			root.setLeft(leftPane);
			
		//Middle area: Display text messages 
			textDisplay = new TextArea();
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(textDisplay);
			root.setCenter(textDisplay);
			
			
			
			
			
			
			primaryStage.setTitle("Server GUI");
			Scene scene = new Scene(root,500,400);
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
