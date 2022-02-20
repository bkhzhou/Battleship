package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;

public class FieldBoard extends Parent {
	
	private HBox hbox;
	private VBox vbox1;
	private VBox vbox2;
	private GridPane playerBoard;
	private GridPane computerBoard;
	private final int GRID_SIZE = 10;
	private Label playerLabel;
	private Label computerLabel;
	protected char[][] playerArray;
	protected char[][] computerArray;
	private Rectangle playerCell;
	private Rectangle computerCell;
	private Label notice;
	private Label gameNotice;
	protected int shipCount = 0;
	private int shipLength = 0;
	private char shipName;
	private Human human = new Human();
	private AiPlayer ai = new AiPlayer();
	private boolean ready = false;
	private boolean loadGame = false;

	/**
	 * The constructor of FieldBoard is where we will initialize the board and setting up char 2-D arrays for the game.
	 * Both players Human and Computer/Ai will have their own char 2-D arrays and their respective labels below the view board.
	 * The GridPanes are set into VBoxes with their respective labels and placed into HBoxes for the view on the scene.
	 */
	public FieldBoard() {
		playerLabel = new Label("Player's Board");
		playerLabel.setFont(new Font("Courier New", 20));
		playerLabel.setTextFill(Color.web("#FFFFFF"));
		computerLabel = new Label("Computer's Board");
		computerLabel.setFont(new Font("Courier New", 20));
		computerLabel.setTextFill(Color.web("#FFFFFF"));
		notice = new Label("Place your ships on the board.");
        notice.setFont(new Font("Courier New",15));
        notice.setTextFill(Color.web("#FFFFFF"));
        gameNotice = new Label("");
        gameNotice.setFont(new Font("Courier New", 15));
        gameNotice.setTextFill(Color.web("#FFFFFF"));
        
        playerBoard = new GridPane();
        computerBoard = new GridPane();
        playerArray = new char[GRID_SIZE][GRID_SIZE];
        computerArray = new char[GRID_SIZE][GRID_SIZE];
        
        
        initializeBoard();
        
		vbox1 = new VBox(10, playerLabel, playerBoard, notice);
		vbox2 = new VBox(10, computerLabel, computerBoard, gameNotice);
		hbox = new HBox(50,vbox1, vbox2);
		hbox.setStyle("-fx-background-color:#0437F2;");
		hbox.setAlignment(Pos.CENTER);
		
	}
	
	public void initializeBoard() {
		
		if(shipCount == 5) {
			ready = true;	//If ready is true then you can click on Computer's Board
		}
		
		if(!loadGame) {
			setGrid();		//If you did not open a saved game you set the grid for playerArray.
		}
		
		for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
            	playerCell = new Rectangle(row,col,30,30);
            	playerCell.setStroke(Color.BLACK);
                char tempChar = playerArray[row][col];
                
                if(tempChar == 'b' || tempChar == 'r' || tempChar == 's' || tempChar == 'c' || tempChar == 'd') {
                	playerCell.setFill(Color.GREEN);
                } else if (tempChar == 'h') {
                	playerCell.setFill(Color.RED);
                	playerCell.setStroke(Color.GREEN);
                } else if (tempChar == 'm') {
                	playerCell.setFill(Color.BLACK);
                } else {
                	playerCell.setFill(Color.LIGHTBLUE);
                }
                
                playerBoard.add(playerCell, col, row);
                playerCell.setOnMouseClicked(event -> mouseClicked(event));
            }
        }
        
        for(int row1 = 0; row1 < GRID_SIZE; row1++) {
        	for(int col1 = 0; col1 < GRID_SIZE; col1++) {
        		computerCell = new Rectangle(row1,col1,30,30);
            	computerCell.setStroke(Color.BLACK);
            	
            	char tempChar1 = computerArray[row1][col1];
            	if (tempChar1 == 'h') {
            		computerCell.setFill(Color.RED);
            	} else if (tempChar1 == 'm') {
            		computerCell.setFill(Color.BLACK);
            	} else {
            		computerCell.setFill(Color.LIGHTGRAY);
            	}
            	
                computerBoard.add(computerCell, col1, row1);
                computerCell.setOnMouseClicked(event -> {
                	if(ready) {
	                	Node n = (Node) event.getSource();
	                	Integer row2 = GridPane.getRowIndex(n);
	                	Integer col2 = GridPane.getColumnIndex(n);
	                	Rectangle changeCell = (Rectangle)n;
	                	int r = row2.intValue();
	                	int c = col2.intValue();
	                	shoot(r,c,changeCell);
                	}
                	
                });
        	}
        }
        
	}
	
	/**
	 * This method shoot is for Human player and Computer player or Ai. This checks if the following player hits or misses on the object Rectangle 'cell' or position of the array and sets it as 'h' or 'm' respectively.
	 * If the player selects and hits the ship it will change  the color of the position/location clicked to a RED on the Computer's board (Right Side). If the player hits the same spot you will need to 
	 * place your mark on a different spot.
	 * After the human player goes, the computer selects a random spot on the grid and checks for the same criterias.
	 * Each turn it checks for if either are a winner!
	 * 
	 * @param row	- The row position the player selected.
	 * @param col	- The column position the player selected.
	 * @param changeCell	- The Node that is passed to this function which would be used to change the color of the specific cell of grid location.
	 */
	public void shoot(int row, int col, Rectangle changeCell) {
		char shoot;
		shoot = computerArray[row][col];
		boolean winner = false;
		boolean tryAgain = false;
		
		if((shoot == 'b') || (shoot == 'c') || (shoot == 'd') || (shoot == 'r') || (shoot == 's')) {
			computerArray[row][col] = 'h';
			ai.setArray(row, col,computerArray[row][col]);
			gameNotice.setText("");
			changeCell.setFill(Color.RED);
			winner = checkWinner(computerArray);
			if(winner) {
				gameNotice.setText("YOU WON!");
				ready = false;
			}
			
		} else if((shoot == 'm') || (shoot =='h')) {	//m for miss //h for hit
			gameNotice.setText("Please pick another spot.");
			tryAgain = true;
		} else {
			computerArray[row][col] = 'm';
			ai.setArray(row, col,computerArray[row][col]);
			changeCell.setFill(Color.BLACK);
			gameNotice.setText("");
		}
		if(!tryAgain) {
		//Computer move
			int row1 = (int)(Math.random() * 10);
			int col1 = (int)(Math.random() * 10);
			char computerShoots = playerArray[row1][col1];
			while(computerShoots == 'm' || computerShoots =='h') {
				row1 = (int)(Math.random() * 10);
				col1 = (int)(Math.random() * 10);
				computerShoots = playerArray[row1][col1];
			}
			for(Node node : playerBoard.getChildren()) {
				if(node instanceof Rectangle && GridPane.getColumnIndex(node)==col1 && GridPane.getRowIndex(node)==row1) {
					Rectangle computerPick = (Rectangle)node;
					if((computerShoots == 'b') || (computerShoots == 'c') || (computerShoots == 'd') || (computerShoots == 'r') || (computerShoots == 's')) {
						playerArray[row1][col1] = 'h';
						human.setArray(row, col, playerArray[row][col]);
						computerPick.setStroke(Color.GREEN);
						computerPick.setFill(Color.RED);
						winner = checkWinner(playerArray);
						if(winner) {
							gameNotice.setText("YOU LOSE...");
							ready = false;
						}
					} else {
						playerArray[row1][col1] = 'm';
						human.setArray(row, col, playerArray[row][col]);
						computerPick.setFill(Color.BLACK);
					}
					
				}
			}
		}
		for(int r1 = 0; r1 < GRID_SIZE; r1++) {
			for(int c1 = 0; c1 < GRID_SIZE; c1++) {
				computerArray[r1][c1] = ai.getArray(r1,c1);
			}
		}
	}
	/**
	 * This method placeShip lets the Human Player set its own ships from smallest ship to largest ship. Each ship placed on the Player's Board is set to the color GREEN.
	 * There are mouse click actions for this method where if the player left clicks it will set the ships vertically and if the player right clicks it will set the ship horizontally.
	 * Each ship has its' own ship name of one character and loads into the 2-D Array.
	 * 
	 * @param rowIndex	- The row index of where the player has clicked.
	 * @param colIndex	- The column index of where the player has clicked.
	 * @param e			- The mouse event from mouseClicked method.
	 */
	public void placeShip(Integer rowIndex,Integer colIndex, MouseEvent e) {
		int col = colIndex.intValue();
		int row = rowIndex.intValue();
		boolean vertical = true;
		boolean taken;
		boolean bound;
		Rectangle replaceMe;
		loadShips();
		
		if(e.getButton() == MouseButton.PRIMARY) {	//left click action
			vertical = true;
			bound = bounds(row,col,vertical);
			if(bound) {
				taken = occupied(row, col,vertical);
				if(!taken) {
					while(shipLength > 0) {
						for(Node node : playerBoard.getChildren()) {
							if(node instanceof Rectangle && playerBoard.getColumnIndex(node)==col && playerBoard.getRowIndex(node)==row) {
								replaceMe = (Rectangle)node;
								replaceMe.setFill(Color.GREEN);
								playerArray[row][col] = shipName;
								human.setArray(row, col, playerArray[row][col]);
								shipLength--;
							}
						}
						row++;
					}
				}
			}
		} else if (e.getButton() == MouseButton.SECONDARY) {	//right click action
			vertical = false;
			bound = bounds(row,col,vertical);
			if(bound) {
				taken = occupied(row, col,vertical);
				if(!taken) {
					while(shipLength > 0) {
						for(Node node : playerBoard.getChildren()) {
							if(node instanceof Rectangle && playerBoard.getColumnIndex(node) == col && playerBoard.getRowIndex(node)==row) {
								replaceMe = (Rectangle) node;
								replaceMe.setFill(Color.GREEN);
								playerArray[row][col] = shipName;
								human.setArray(row, col, playerArray[row][col]);
								shipLength--;
							}
						}
						col++;
					}
				}
			}
		}
	}
	
	/**
	 * The mouseClicked method is entered when the human player has click on his/her respective board.
	 * @param e	- The mouse click event caused by human player.
	 */
	public void mouseClicked(MouseEvent e) {
		Node source = (Node)e.getSource();
		Integer colIndex = GridPane.getColumnIndex(source);
		Integer rowIndex = GridPane.getRowIndex(source);
		placeShip(rowIndex, colIndex, e);
	}
	
	/**
	 * The method occupied is to make sure that the player does not select or overlap their placement of ships on the playerBoard.
	 * If the placement of ship is taken/occupied, the player will need to strategize again to place their ships for battle.
	 * 
	 * @param row	- The row location of where the mouse was clicked.
	 * @param col	- The column location of where the mouse was clicked.
	 * @param vertical	- If the ship is being placed vertically or horizontally.
	 * @return	- Returns if the spot is occupied or not.
	 */
	public boolean occupied(int row, int col, boolean vertical) {
		boolean taken = false;
		int count = 0;
		
		if(vertical) {	//checks if placement of ship have occupied spots vertically
			for(int i = row; i < row + shipLength; i++) {
				char occ = getGrid(playerArray,i,col);
				if(occ == ' ') {	
					notice.setText("");
				} else {
					count++;
				}
			}
		} else {	//checks if placement of ship have occupied spots horizontally
			for(int i = col; i < col + shipLength; i++) {
				char occ = getGrid(playerArray,row,i);
				if(occ == ' ') {
					notice.setText("");
				} else {
					count++;
				}
			}
		}
		if (count > 0) {
			taken = true;
			notice.setText("Spot is occupied.");
		}
		if(!taken) {
			loadShips();
			shipCount++;	//after loading ship check bounds to make sure its in bound
		}
		return taken;
	}
	
	/**
	 * The method bounds is to check if the player has set their respective ships within bound. It will make sure that the player will not select a ship that will be placing their ships outside their territory.
	 * If the ship is placed vertically we will check the row and make sure its in bound or if the ship is placed horizontally we will check for the column.
	 * 
	 * @param row	- The row location of the ship.
	 * @param col	- The column location of the ship.
	 * @param vertical	- If the ship is being placed vertically or horizontally.
	 * @return	- Return the boolean value if its within the bounds.
	 */
	public boolean bounds(int row,int col, boolean vertical) {
		boolean bound = true;	//within the bounds
		if(vertical) {	//checks if greater than GRID_SIZE
			if(row + shipLength > GRID_SIZE) {
				bound = false;
				notice.setText("Placement of ship is out of bounds!");
			}
		} else {
			if(col + shipLength > GRID_SIZE) {
				bound = false;
				notice.setText("Placement of ship is out of bounds!");
			}
		}
		return bound;
	}
	
	/**
	 * This method checks to see if there is a winner. It will check if there are 17 hits or 'h' on the 2-D arrays.
	 * @param player	- The char 2-D array board that is passed to this function.
	 * @return	- Returns the boolean value if the player has won.
	 */
	public boolean checkWinner(char[][] player) {
		int countHits = 0;
		for(int r=0; r < GRID_SIZE; r++) {
			for(int c=0; c<GRID_SIZE; c++) {
				if(player[r][c] == 'h') {
					countHits++;
				}
			}
		}
		if(countHits == 17) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method is for player to load the ships on the Player's Board (left side). It places the shortest length ship first to the largest length ship.
	 */
	public void loadShips() {
		Ships currentShip;
		
		switch(shipCount) {
		case 0:
			currentShip = human.getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			break;
		case 1:
			currentShip = human.getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			break;
		case 2:
			currentShip = human.getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			break;
		case 3:
			currentShip = human.getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			break;
		case 4:
			currentShip = human.getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			ready = true;
			break;
		default:
			notice.setText("All ships has been set.");
			break;
		}
		if(shipCount == 5) {
			notice.setText("All ships has been set.");
		}
	}
	
	/**
	 * Default set grid to empty characters for the char 2-D array.
	 */
	public void setGrid() {
		for(int row=0 ; row<GRID_SIZE; row++) {
			for(int col=0; col<GRID_SIZE; col++) {
				playerArray[row][col] = ' ';
				human.setArray(row, col, playerArray[row][col]);
			}
		}
	}
	/**
	 * This method sets the shipCount of the current game.
	 * @param sc	- The shipCount
	 */
	public void setShipCount(int sc) {
		this.shipCount = sc;
	}
	/**
	 * This method is to get the current shipCount status.
	 * @return	- The shipCount of ships the player has placed on the board.
	 */
	public int getShipCount() {
		return shipCount;
	}
	/**
	 * This method is called when loading a saved game to initialize with its shipCount and both 2-D array boards.
	 */
	public void loadPreviousGrid() {	//char[][] loadPlayer,char[][] loadComputer
		loadGame = true;
		
		initializeBoard();
        
        notice.setText("Game loaded.");
        vbox1 = new VBox(10, playerLabel, playerBoard, notice);
		vbox2 = new VBox(10, computerLabel, computerBoard, gameNotice);
		hbox = new HBox(50,vbox1, vbox2);
		hbox.setStyle("-fx-background-color:#0437F2;");
		hbox.setAlignment(Pos.CENTER);
	}
	
	/**
	 * The method getGrid returns the specific value/char in the specific location.
	 * @param array	- The char 2-D array passed to this method.
	 * @param x	- The x or row value.
	 * @param y	- The y or column value.
	 * @return	- Returns the value of the passed array.
	 */
	public char getGrid(char[][]array, int x, int y) {
		return array[x][y];
	}

	/**
	 * This method getBoard is to retrieve the board which is placed in an HBox and returning its HBox.
	 * @return
	 */
	public Pane getBoard() {
		return hbox;
	}
}