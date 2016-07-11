package com.joel.data;

public class CapacityDetails 
{
	private final int orchestraRows=25;
	private final int orchestraSeatsPerRow=50;
	
	private final int mainRows=20;
	private final int mainSeatsPerRow=100;
	
	private final int balcony1Rows=15;
	private final int balcony1SeatsPerRow=100;
	
	private final int balcony2Rows=15;
	private final int balcony2SeatsPerRow=100;
		
	public int getBalcony2SeatingCapacity() 
	{
		return balcony2Rows*balcony2SeatsPerRow;
	}

	public int getBalcony1SeatingCapacity() 
	{
		return balcony1Rows*balcony1SeatsPerRow;
	}

	public int getMainSeatingCapacity()
	{
		return mainRows*mainSeatsPerRow;
	}

	public int getOrchestraSeatingCapacity()
	{
		return orchestraRows*orchestraSeatsPerRow;
	}	
}