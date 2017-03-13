package battleShip;

public class SimpleDotCom 
{
	private int numOfHits = 0;
	private String name;
	private int size = 3;
	private int uniqueID = 0;
	
	SimpleDotCom( String incomingName )
	{
		name = incomingName;
	}
	
	SimpleDotCom( String incomingName, int incomingSize )
	{
		name = incomingName;
		size = incomingSize;
	}
	
	public void markHit()
	{
		numOfHits++;
	}

	public String getName()
	{
		return name;
	}

	public boolean checkIfDotComIsKilled()
	{
		boolean result = false;

		if ( numOfHits == size )
			result = true;

		return result;
	}

	public int getSize()
	{
		return size;
	}

	public boolean checkIfLocationIntersectsAnotherDotCom(
			int[] incomingCordinates, int[][] fieldGrid )
	{
		boolean intersects 	= false;
		int row 			= incomingCordinates[0];
		int column 			= incomingCordinates[1];
		int rightOrDown 	= incomingCordinates[2];

		if ( rightOrDown == StaticConstants.DOWN )
		{
			for ( int rowCount = 0; rowCount < size; rowCount++ )
			{
				if ( fieldGrid[row + rowCount][column] != StaticConstants.MISS )
				{
					intersects = true;
					break;
				}
			}
		}
		else if ( rightOrDown == StaticConstants.RIGHT )
		{
			for ( int columnCount = 0; columnCount < size; columnCount++ )
			{
				if ( fieldGrid[row][column + columnCount] != StaticConstants.MISS )
				{
					intersects = true;
					break;
				}
			}
		}

		return intersects;
	}

	public void setUniqueID( int incomingUniqueID )
	{
		uniqueID = incomingUniqueID;
	}
	
	public int getUniqueID()
	{
		return uniqueID;
	}

	public void setPosition( int[] position, int[][] fieldGrid )
	{
		int dotComSize = size;
		int rowNumber = position[0];
		int columnNumber = position[1];
		int rightOrDown = position[2];
		
		if ( rightOrDown == StaticConstants.RIGHT )
		{
			for ( int columnCount = 0; columnCount < dotComSize; columnCount++ )
				fieldGrid[rowNumber][columnNumber + columnCount] = uniqueID;
		}
		else if ( rightOrDown == StaticConstants.DOWN )
		{
			for ( int rowCount = 0; rowCount < dotComSize; rowCount++ )
				fieldGrid[rowNumber + rowCount][columnNumber] = uniqueID;
		}
		
	}

}
