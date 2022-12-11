package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainUser extends Application {
	
	/**
	 * 
	 * 
	 */
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
		//Left side text and buttons
			BorderPane root = new BorderPane();
			//NOTE: Possibly change this to current active IP Address
			Text text1 = new Text("Enter IP Address and ");
			Text text01 = new Text("Port to connect: \n");
			
			Text text001 = new Text("IP Address:");
			TextField textBox1 = new TextField ();
			Text text0001 = new Text("Port Number:");
			TextField textBox01 = new TextField ();
			Button button1 = new Button ("Connect to Server");

			
			
			
		//Organizing all the left side buttons:
			VBox leftPane = new VBox (text1, text01, text001, textBox1, text0001, textBox01,
					button1);
			
			
			leftPane.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
			leftPane.setAlignment(Pos.TOP_LEFT);
			leftPane.setPadding(new Insets(10,10,10,10));
			root.setLeft(leftPane);
			
			
			
			
			
			
			
			
			Scene scene = new Scene(root,400,400);
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
