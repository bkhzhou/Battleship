package application;
/**
 * The parent ship class 
 * Has methods like set and get for both the ship's name and length
 */
public interface Ships {

	public void setShipName(char name);
	public void setShipLength(int length);
	public char getShipName();
	public int getShipLength();
}
