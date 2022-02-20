package application;

import java.util.Random;
/**
 * The AiPlayer class extend Player class.
 * This class has its own methods for placing ships and checks for occupied spaces and boundaries of randomly placed ships.
 * @author billk
 *
 */
public class AiPlayer extends Player {
	
	private Destroyer destroyer = new Destroyer();
	private Cruiser cruiser = new Cruiser();
	private Carrier carrier = new Carrier();
	private Battleship battleship = new Battleship();
	private Submarine submarine = new Submarine();
	private final int SIZE = 5;
	private final int GRID_SIZE = 10;
	private Ships[] inventory = new Ships[SIZE];
	private char[][] aiArray = new char[GRID_SIZE][GRID_SIZE];
	
	public AiPlayer() {
		setShip();
		setGrid();
		loadShips();
	}
	
	@Override
	public void setShip() {
		inventory[0] = destroyer;
		inventory[1] = cruiser;
		inventory[2] = submarine;
		inventory[3] = battleship;
		inventory[4] = carrier;
	}
	
	public void loadShips() {
		int shipCount = 0;
		int shipLength;
		char shipName;
		Ships currentShip;
		//Adds ships to array
		while(shipCount < 5) {
			currentShip = getShip(shipCount);
			shipLength = currentShip.getShipLength();
			shipName = currentShip.getShipName();
			placeShip(shipLength,shipName);
			
			shipCount++;
		}
	}
	
	public boolean occupied(int row, int col, int shipLength, boolean vertical) {
		int count = 0;
		boolean taken = true;
		
		if(vertical) {
			for(int x = row; x < row + shipLength; x++) {
				char spotChar = getArray(x,col);
				if(spotChar != '-') {
					count++;
				}
			}
		} else {
			for(int y = col; y < col + shipLength; y++) {
				char spotChar = getArray(row,y);
				if(spotChar != '-') {
					count++;
				}
			}
		}
		
		if(count > 0) {
			taken = true;
		} else {
			taken = false;
		}
		
		return taken;
	}
	
	public void placeShip(int shipLength, char shipName) {
		//GENERATE RANDOM BOOLEAN
		Random random = new Random();
		boolean vertical = random.nextBoolean();
		boolean check = true;
		
		//generate random spot
		int row = (int)(Math.random() * 10);
		int col = (int)(Math.random() * 10);
		

		while(row + shipLength > GRID_SIZE) {
			row = (int)(Math.random() * 10);
		}
		while(col + shipLength > GRID_SIZE) {
			col = (int)(Math.random() * 10);
		}
		check = occupied(row,col,shipLength, vertical);
				
		if(vertical && !check) {
			while(shipLength > 0) {
				setArray(row,col,shipName);
				row++;
				shipLength--;
			}
		} else if (!vertical && !check) {
			while(shipLength > 0) {
				setArray(row,col,shipName);
				col++;
				shipLength--;
			}
		} else {
			placeShip(shipLength,shipName);
		}
	}
	
	public void setGrid() {
		for(int row=0 ; row<GRID_SIZE; row++) {
			for(int col=0; col<GRID_SIZE; col++) {
				aiArray[row][col] = '-';
			}
		}
	}
	@Override
	public void setArray(int row, int col, char c) {
		aiArray[row][col] = c;
	}
	@Override
	public char getArray(int row, int col) {
		return aiArray[row][col];
	}

	@Override
	public Ships getShip(int num) {
		return inventory[num];
	}

}
