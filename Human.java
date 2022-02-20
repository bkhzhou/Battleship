package application;
/**
 * The Human class extend Player class.
 * @author billk
 *
 */
public class Human extends Player {
	
	private Destroyer destroyer = new Destroyer();
	private Cruiser cruiser = new Cruiser();
	private Carrier carrier = new Carrier();
	private Battleship battleship = new Battleship();
	private Submarine submarine = new Submarine();
	private final int SIZE = 5;
	private final int GRID_SIZE = 10;
	private Ships[] inventory = new Ships[SIZE];
	private char[][] playerArray = new char[GRID_SIZE][GRID_SIZE];
	
	public Human() {
		setShip();
	}
	
	public void setShip() {
		inventory[0] = destroyer;
		inventory[1] = cruiser;
		inventory[2] = submarine;
		inventory[3] = battleship;
		inventory[4] = carrier;
	}
	@Override
	public void setArray(int row, int col, char c) {
		playerArray[row][col] = c;
	}
	@Override
	public char getArray(int row, int col) {
		return playerArray[row][col];
	}
	@Override
	public Ships getShip(int num) {
		return inventory[num];
	}
}