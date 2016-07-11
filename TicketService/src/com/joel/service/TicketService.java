package com.joel.service;

import java.util.Optional;
import com.joel.serviceManager.ReservationsManager;

public class TicketService implements TicketServiceInterface 
{
	ReservationsManager reservationsManager;
	String notProcessed="Unable to process request. Could not find enough seats to allocate.";
	
	public TicketService() 
	{
		reservationsManager=new ReservationsManager();
	}
	
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) 
	{
		int level=venueLevel.orElse(0);
		int numSeats=0;
		if(level>=1&&level<=4)
		{
			if(level==1)
				numSeats= reservationsManager.getOrchestraAvailability();
			else if(level==2)
				numSeats= reservationsManager.getMainAvailability();
			else if(level==3)
				numSeats= reservationsManager.getBalcony1Availability();
			else if(level==4)
				numSeats= reservationsManager.getBalcony2Availability();
		}
		return numSeats;
	}


	@Override
	public String findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) 
	{
		String returnValue=notProcessed;
		int minlvl = minLevel.orElse(1);
		int maxlvl = maxLevel.orElse(4);
		if(minlvl<1||minlvl>4||maxlvl<1||maxlvl>4||numSeats<1||customerEmail.isEmpty())
		{
			returnValue="Unable to process request. Invalid input.";
		}
		else
		{
			if(minlvl==1&&minlvl<=maxlvl)
			{
				returnValue=reservationsManager.holdAvaiableSeats("orchestra", numSeats, customerEmail);
				if(returnValue.equals(notProcessed))
					minlvl++;
			}
			if(minlvl==2&&minlvl<=maxlvl)
			{
				returnValue=reservationsManager.holdAvaiableSeats("main", numSeats, customerEmail);
				if(returnValue.equals(notProcessed))
					minlvl++;
			}
			if(minlvl==3&&minlvl<=maxlvl)
			{
				returnValue=reservationsManager.holdAvaiableSeats("balcony1", numSeats, customerEmail);
				if(returnValue.equals(notProcessed))
					minlvl++;
			}
			if(minlvl==4&&minlvl<=maxlvl)
			{
				returnValue=reservationsManager.holdAvaiableSeats("balcony2", numSeats, customerEmail);
			}
		}
		return returnValue;
	}
	
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) 
	{	
		return reservationsManager.reserveHeldSeats(seatHoldId, customerEmail);	
	}
	
	public String getReservationDetails(int reservationId)
	{
		return reservationsManager.getReservationDetails(reservationId);
	}
}
