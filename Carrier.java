package application;
/**
 * Carrier is a child from the ship class 
 * Inherits the parent's methods like set and get for both the ship's name and length
 * When a new Carrier object is created, it is assigned it's default values: name = "c" and length = 5
 */
public class Carrier implements Ships {
	
	private char name;
	private int length = 5;	//spaces it takes
	/**
	 * Sets the name of the ship and length of the ship.
	 */
	public Carrier() {
		setShipName('c');
		setShipLength(length);
	}
	@Override
	public void setShipName(char name) {
		// TODO Auto-generated method stub
		this.name = name;
	}
	/**
	 * Sets the ship's length 
	 */
	@Override
	public void setShipLength(int length) {
		this.length = length;
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
