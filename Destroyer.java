package application;
/**
 * Destroyer is a child from the ship class 
 * Inherits the parent's methods like set and get for both the ship's name and length
 * When a new Destroyer object is created, it is assigned it's default values: name = "d" and length = 2
 */
public class Destroyer implements Ships {
	
	private char name;
	private int length = 2;	//spaces it takes
	
	public Destroyer() {
		setShipName('d');
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
