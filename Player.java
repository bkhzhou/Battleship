package application;
/**
 * The Player abstract class for Humans and AiPlayer.
 *
 */
public abstract class Player {

	public abstract void setShip();
	public abstract void setArray(int row, int col, char c);
	public abstract char getArray(int row, int col);
	public abstract Ships getShip(int num);
	
}
