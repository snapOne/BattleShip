package battleShip;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Player
{
	private Scanner reader;
	private int numberOfGuesses = 0;

	public String getGuess()
	{
		String stringGuess = "";

		while ( stringGuess.isEmpty() )
		{
			System.out.print( "Guess: " );
			
			try
			{
				reader = new Scanner( System.in );
				stringGuess = reader.next();
				
				if ( !stringGuess.matches( "^([A-G][1-7]|quit)$" ) )
				{
					System.out.println("Invalid Format.  Rows must be A-G and columns 1-7.");
					stringGuess = "";				
				}				
			}
			catch ( NoSuchElementException exception)
			{
			    System.out.println("Something happened while trying to read user input - exiting");
			    stringGuess = "quit";
			}

		}

		return stringGuess;
	}

	public void incrementGuessCount()
	{
		numberOfGuesses++;
	}

	public int getNumberOfGuesses()
	{
		return numberOfGuesses;
	}
}