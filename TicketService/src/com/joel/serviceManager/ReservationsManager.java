package com.joel.serviceManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.joel.data.CapacityDetails;
import com.joel.data.PriceDetails;
import com.joel.data.Ticket;

public class ReservationsManager 
{
	private CapacityDetails capacityDetails;
	private PriceDetails priceDetails;
	private IdGenerator idGenerator;
	
	private int orchestraAvailability;
	private int mainAvailability;
	private int balcony1Availability;
	private int balcony2Availability;
	
	private String orchestra="orchestra";
	private String main="main";
	private String balcony1="balcony1";
	private String balcony2="balcony2";
	
	private Map<Integer,Ticket> reservedSeats=new ConcurrentHashMap<>();
	private Map<Integer,Ticket> heldSeats=new ConcurrentHashMap<>();
	
	public ReservationsManager()
	{
		capacityDetails=new CapacityDetails();
		priceDetails = new PriceDetails();
		idGenerator=new IdGenerator();
		
		orchestraAvailability=capacityDetails.getOrchestraSeatingCapacity();
		mainAvailability=capacityDetails.getMainSeatingCapacity();
		balcony1Availability=capacityDetails.getBalcony1SeatingCapacity();
		balcony2Availability=capacityDetails.getBalcony2SeatingCapacity();
	}
	
	public String holdAvaiableSeats(String seatType, int quantity, String customerEmail)
	{
		boolean reserved=false;
		Ticket ticket=null;
		int seatHoldId=0;
		Double price=0.0;
		String returnValue="Unable to process request. Could not find enough seats to allocate.";
		
		if(seatType.toLowerCase().trim().equals(orchestra)&&orchestraAvailability>=quantity)
		{	
				orchestraAvailability=orchestraAvailability-quantity;
				price=priceDetails.getOrchestraSeatPrice();
				reserved=true;
		}
		else if(seatType.toLowerCase().trim().equals(main)&&mainAvailability>=quantity)
		{
			mainAvailability=mainAvailability-quantity;
			price=priceDetails.getMainSeatPrice();
			reserved=true;
		}
		else if(seatType.toLowerCase().trim().equals(balcony1)&&balcony1Availability>=quantity)
		{
			balcony1Availability=balcony1Availability-quantity;
			price=priceDetails.getBalcony1SeatPrice();
			reserved=true;
		}
		else if(seatType.toLowerCase().trim().equals(balcony2)&&balcony2Availability>=quantity)
		{
			balcony2Availability=balcony2Availability-quantity;
			price=priceDetails.getBalcony1SeatPrice();
			reserved=true;
		}
		if(reserved==true)
		{
			seatHoldId=idGenerator.getId(customerEmail);
			ticket= new Ticket(customerEmail,quantity,seatType,seatHoldId,price*quantity);
			heldSeats.put(seatHoldId,ticket);
			returnValue= ("Seats reserved temporarily. "
					+ "Please confirm reservation in step 3 of main menu. "
					+ "Temporary seat hold id is:"+seatHoldId);
			new DeleteReservations(seatHoldId,this);
		}
		return returnValue;
	}
	
	public synchronized String reserveHeldSeats(int seatHoldId, String CustomerEmail)
	{
		String returnValue="Unable to process request. Record not found.";
		Ticket t=heldSeats.get(seatHoldId);
		if(t!=null&&t.getCustomerEmail().equals(CustomerEmail))
		{
			heldSeats.remove(seatHoldId);
			reservedSeats.put(seatHoldId, t);
			returnValue= "Reservation confirmed with id: "+seatHoldId;
		}
		return returnValue;
	}
	
	public String getReservationDetails(int reservationId)
	{
		String returnValue="Unable to process request. Record not found.";
		//returnValue= reservedSeats.get(reservationId).toString();
		Ticket ticket=reservedSeats.get(reservationId);
		if(ticket!=null)
			returnValue=ticket.toString();
		return returnValue;
	}
	
	public synchronized void releaseHeldSeats(int seatHoldId)
	{
		Ticket ticket=heldSeats.remove(seatHoldId);
		if(ticket!=null)
		{
			if(ticket.getSeatType().equals(orchestra))
				orchestraAvailability=orchestraAvailability+ticket.getNumSeats();
			else if(ticket.getSeatType().equals(main))
				mainAvailability=mainAvailability+ticket.getNumSeats();
			else if(ticket.getSeatType().equals(balcony1))
				balcony1Availability=balcony1Availability+ticket.getNumSeats();
			else if(ticket.getSeatType().equals(balcony2))
				balcony2Availability=balcony2Availability+ticket.getNumSeats();
		}
	}

	public int getOrchestraAvailability() {
		return orchestraAvailability;
	}

	public  int getMainAvailability() {
		return mainAvailability;
	}

	public int getBalcony1Availability() {
		return balcony1Availability;
	}

	public int getBalcony2Availability() {
		return balcony2Availability;
	}
}
