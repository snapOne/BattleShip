package battleShip;

import java.util.ArrayList;
import java.util.Random;

public class Game 
{
	private final int NUMBEROFROWS 		= 7;
	private final int NUMBEROFCOLUMNS 	= 7;
	private final int GUESSED	= -2;
	protected SimpleDotCom petsDotCom;
	protected SimpleDotCom go2DotCom;
	protected SimpleDotCom askMeDotCom;
	private Player player;
	private int[][] fieldGrid;
	private ArrayList<SimpleDotCom> arrayListOfDotComs;
	private int numberOfDotComs;
	private int maxGuesses = 50;
	
	private int[][] initialiseLocationGrid() 
	{
		int[][] location = new int[NUMBEROFROWS][NUMBEROFCOLUMNS];

		for ( int row = 0; row < location.length; row++ )
		{
			for ( int column = 0; column < location[row].length; column++ )
				location[row][column] = StaticConstants.MISS;
		}

		return location;
	}

	private int getRandomNumberInRange( int minimum, int maximum )
	{
		Random rand = new Random();

		int range = maximum - minimum + 1;
		int randomNumber = rand.nextInt( range );

		return randomNumber;
	}

	public int playGame( ) 
	{
		System.out.println( "You are playing a command line battleship in which you need to sink three DotComs.");
		System.out.println( "It's a 7x7 grid where the size of each DotCom is 3 cells.");
		System.out.println( "Take a guess like B2 or F7 to start.  Type \"quit\" to exit.");
		
		while ( numberOfDotComs > 0 && player.getNumberOfGuesses() < maxGuesses )
		{
			String guess 			= player.getGuess();
			
			if ( guess.equals( "quit" ) )
			{
				System.out.println( "Exiting");
				break;
			}
			
			String[] rowAndColumn 	= guess.split( "(?<=[A-Z])" );
			int rowNumber 			= convertCharToInt( rowAndColumn[0].toLowerCase().toCharArray() );
			int columnNumber 		= Integer.parseInt( rowAndColumn[1] );
			
			columnNumber--;
			
			int hitOrMiss = fieldGrid[rowNumber][columnNumber];

			System.out.println( "Current number of DotComs = "
					+ numberOfDotComs + ", Number of Guesses = "
					+ player.getNumberOfGuesses() );

			if ( checkIfAlreadyGuessed( rowNumber, columnNumber ) )
			{
				numberOfDotComs = checkHitOrMiss( numberOfDotComs, hitOrMiss );
				
				fieldGrid[rowNumber][columnNumber] = GUESSED;
			}
			else
				System.out.println( "You have already guessed " + guess );

			player.incrementGuessCount();
		}

		return numberOfDotComs;
	}

	public boolean checkIfAlreadyGuessed( int rowNumber, int columnNumber )
	{
		return fieldGrid[rowNumber][columnNumber] != GUESSED;
	}

	public int convertCharToInt( char[] rowNumberChar )
	{
		return rowNumberChar[0] - 'a' + 1;
	}

	private int checkHitOrMiss( int numberOfDotComs, int hitOrMiss )
	{
		if ( hitOrMiss != StaticConstants.MISS )
		{
			SimpleDotCom dotCom = arrayListOfDotComs.get( hitOrMiss );
			dotCom.markHit();
			System.out.println( dotCom.getName() + " hit!" );

			if ( dotCom.checkIfDotComIsKilled() == true )
			{
				System.out.println( dotCom.getName() + " popped!" );
				numberOfDotComs--;
			}
		}
		else
			System.out.println( "Miss" );

		return numberOfDotComs;
	}

	public void initialiseUniquePositionsForDotComs()
	{
		for ( int index = 0; index < arrayListOfDotComs.size(); index++ )
		{
			int[] position = new int[3];

			SimpleDotCom dotCom = arrayListOfDotComs.get( index );
			dotCom.setUniqueID( index );

			position = generateUniquePosition( dotCom, fieldGrid );

			dotCom.setPosition( position, fieldGrid );
		}
	}

	private int[] generateUniquePosition( SimpleDotCom dotCom, int[][] fieldGrid )
	{
		boolean dotComInterectsAnotherDotCom = true;
		int[] position = new int[3];

		while ( dotComInterectsAnotherDotCom != false )
		{
			position = generateRandomPosition();

			dotComInterectsAnotherDotCom = dotCom
					.checkIfLocationIntersectsAnotherDotCom( position,
							fieldGrid );
		}

		return position;
	}

	private int[] generateRandomPosition()
	{
		int rowNumber = 0;
		int columnNumber = 0;
		int rightOrDown = 0;

		rightOrDown = getRandomNumberInRange( 0, 1 );

		if ( rightOrDown == StaticConstants.DOWN )
		{
			rowNumber = getRandomNumberInRange( 0, 3 );
			columnNumber = getRandomNumberInRange( 0, 6 );
		}
		else if ( rightOrDown == StaticConstants.RIGHT )
		{
			rowNumber = getRandomNumberInRange( 0, 6 );
			columnNumber = getRandomNumberInRange( 0, 3 );
		}

		int[] position = { rowNumber, columnNumber, rightOrDown };

		return position;
	}

	public void intialiseGameVariables()
	{
		petsDotCom = new SimpleDotCom( "pets.com" );
		go2DotCom = new SimpleDotCom( "go2.com" );
		askMeDotCom = new SimpleDotCom( "askMe.com" );

		arrayListOfDotComs = new ArrayList<SimpleDotCom>();

		arrayListOfDotComs.add( petsDotCom );
		arrayListOfDotComs.add( go2DotCom );
		arrayListOfDotComs.add( askMeDotCom );

		player = new Player();

		fieldGrid = initialiseLocationGrid();
		
		numberOfDotComs = arrayListOfDotComs.size();
		
		initialiseUniquePositionsForDotComs();
	}
}
