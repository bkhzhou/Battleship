package application;
/**
 * Battleship is a child from the ship class 
 * Inherits the parent's methods like set and get for both the ship's name and length
 * When a new Battleship object is created, it is assigned it's default values: name = "b" and length = 4
 */
public class Battleship implements Ships {

	private char name;
	private int length = 4;	//spaces it takes
	
	public Battleship() {
		setShipName('b');
		setShipLength(length);
	}
	
	@Override
	public void setShipName(char name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public void setShipLength(int length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char getShipName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getShipLength() {
		// TODO Auto-generated method stub
		return length;
	}
}
