package battleShip;

public class Launch
{
	public static void main( String[] args ) 
	{
		Game battleShipGame = new Game();

		battleShipGame.intialiseGameVariables();

		battleShipGame.playGame();

		System.out.println( "All done" );
		
	}
}