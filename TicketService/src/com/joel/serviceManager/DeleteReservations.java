package com.joel.serviceManager;

public class DeleteReservations extends Thread
{
	private ReservationsManager reservationsManager;
	private int seatHoldId;
	private final int seconds=60;
	
	public DeleteReservations(int seatHoldId, ReservationsManager reservationsManager) 
	{
		this.reservationsManager=reservationsManager;
		this.seatHoldId=seatHoldId;
		start();
	}
	
	@Override
	public void run() 
	{
		try
		{
			Thread.sleep(seconds*1000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		reservationsManager.releaseHeldSeats(seatHoldId);
	}
}