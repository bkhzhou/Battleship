package application;
/**
 * Cruiser is a child from the ship class 
 * Inherits the parent's methods like set and get for both the ship's name and length
 * When a new Cruiser object is created, it is assigned it's default values: name = "r" and length = 3
 */
public class Cruiser implements Ships {

	private char name;
	private int length = 3;	//spaces it takes
	
	public Cruiser() {
		setShipName('r');
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
