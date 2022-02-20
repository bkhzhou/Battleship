package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Cell;
import javafx.scene.Parent;


public class Main extends Application {
	//Application components
	public Stage primaryStage;
	public BorderPane borderPane;
	public Scene scene;
	//Menu components
	private MenuBar menuBar;
	private Menu buildFileMenu;
	private Menu buildMoreMenu;
	private MenuItem newGame;
	private MenuItem openSavedGame;
	private MenuItem saveGame;
	private MenuItem exitItem;
	private MenuItem about;
	private SeparatorMenuItem sep = new SeparatorMenuItem();
	private FieldBoard board;
	
	//Size of window
	final double WIDTH = 550, HEIGHT = 1000;
	
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * This start method will initialize the stage, scene, and menu bar.
	 */
	@Override
	public void start(Stage primaryStage) {
		menuBar = new MenuBar();
		buildFileMenu(primaryStage);
		buildMoreMenu(primaryStage);
		menuBar.getMenus().addAll(buildFileMenu,buildMoreMenu);
		
		primaryStage.setTitle("Battleship Retro");	//Sets title
		Image image = new Image("file:bslogo.jpg");	//Load image of game
		ImageView imageView = new ImageView(image);
		VBox vbox = new VBox(imageView);
		vbox.setAlignment(Pos.BASELINE_CENTER);
		
		borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		borderPane.setCenter(vbox);
		borderPane.setStyle("-fx-background-color: #000000;");
		
		Button exitGame = new Button("Exit");
		exitGame.setOnAction(new exitButtonHandler());
		
		HBox hboxMenu = new HBox(exitGame);
		borderPane.setBottom(hboxMenu);
		hboxMenu.setAlignment(Pos.CENTER);
		hboxMenu.setPadding(new Insets(25,350,250,350));
		hboxMenu.setSpacing(20);
		
		//Scene display
		scene = new Scene(borderPane, HEIGHT, WIDTH);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Building the menu bar with functionalities of New game, Open game, Save game, and exit game.
	 * @param primaryStage	- The primary stage that will be running.
	 */
	private void buildFileMenu(Stage primaryStage) {
		buildFileMenu = new Menu("File");
		newGame = new MenuItem("New Game");
		openSavedGame = new MenuItem("Open...");
		saveGame = new MenuItem("Save...");
		exitItem = new MenuItem("Exit");
		
		newGame.setOnAction(event ->
		{
			board = new FieldBoard();
			borderPane = new BorderPane();
			borderPane.setTop(menuBar);
			borderPane.setCenter(board.getBoard());
			Scene newGame = new Scene(borderPane, HEIGHT, WIDTH);
			primaryStage.setScene(newGame);
			primaryStage.show();
		});
		
		openSavedGame.setOnAction(event ->
		{
			try {
				FileInputStream inStream = new FileInputStream("game.obj");
				ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
				
				board = new FieldBoard();
				board.playerArray = (char[][]) objectInputFile.readObject();
				board.computerArray = (char[][])objectInputFile.readObject();
				board.shipCount = (int)objectInputFile.readObject(); 
				board.loadPreviousGrid();
				
				BorderPane borderPane1 = new BorderPane();
				borderPane1.setTop(menuBar);
				borderPane1.setCenter(board.getBoard());
				Scene newGame1 = new Scene(borderPane1, HEIGHT, WIDTH);
				primaryStage.setScene(newGame1);
				primaryStage.show();
				//System.out.println("File successfully opened.");
				
				objectInputFile.close();
			} catch (Exception e) {
				//System.out.println("Something went wrong with the file write.");
				e.printStackTrace();
			}
		});
		
		saveGame.setOnAction(event ->
		{
			try {
				
				FileOutputStream outStream = new FileOutputStream("game.obj");
				ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
				objectOutputFile.writeObject(board.playerArray);
				objectOutputFile.writeObject(board.computerArray);
				objectOutputFile.writeObject(board.getShipCount());
				objectOutputFile.close();
				//System.out.println("File successfully saved.");
			} catch (Exception e) {
				//System.out.println("Something went wrong with the file write.");
				e.printStackTrace();
			}
		});
		
		exitItem.setOnAction(event ->
		{
			primaryStage.close();
			//System.out.println("Game terminated.");
			System.exit(0);
		});
		
		//adding into menu
		buildFileMenu.getItems().addAll(newGame,openSavedGame,saveGame,sep,exitItem);
	}
	
	/**
	 * Another section of the menu bar for About section with the group names.
	 * @param primaryStage	- The primary stage that will be running.
	 */
	private void buildMoreMenu(Stage primaryStage) {
		buildMoreMenu = new Menu("More");
		about = new MenuItem("About");
		
		about.setOnAction(event ->
		{
			Label outputLabel = new Label("Coded by:\nBhavya Premachandran, Anthony Sanchez, Bill Zhou");
			BorderPane aboutPane = new BorderPane();
			aboutPane.setTop(menuBar);
			aboutPane.setCenter(outputLabel);
			
			//Set new scene for displaying
			Scene newScene = new Scene(aboutPane,HEIGHT,WIDTH);
			primaryStage.setScene(newScene);
			primaryStage.show();
		});
		
		buildMoreMenu.getItems().add(about);
	}
	/**
	 * The exit button created on the main screen to make users/player exit faster instead of the menu bar. There will be a confirmation for exiting the game.
	 * @author billk
	 *
	 */
	class exitButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.CONFIRMATION);	//Confirmation to exit
			alert.setTitle("Exit");
			alert.setHeaderText("You're about to exit the game.");
			alert.setContentText("Are you sure you want to exit the game?");
			
			if(alert.showAndWait().get() == ButtonType.OK) {
				primaryStage = (Stage) borderPane.getScene().getWindow();
				primaryStage.close();
			}
		}
	}
	
}
