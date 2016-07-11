package com.joel.data;

public class Ticket 
{
	private String customerEmail;
	private int numSeats;
	private String seatType;
	private int id;
	private double totalCost;
	
	public Ticket(String customerEmail, int numSeats, String seatType, int id, Double totalCost)
	{
		super();
		this.customerEmail = customerEmail;
		this.numSeats = numSeats;
		this.seatType = seatType;
		this.id = id;
		this.totalCost=totalCost;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
	
	public String getSeatType() {
		return seatType;
	}
	
	public int getId() {
		return id;
	}
	public double getTotalCost() {
		return totalCost;
	}
	
	public String toString()
	{
		return "ReservationId: "
				+getId()
				+",Customer email: "
				+getCustomerEmail()
				+",Seat type: "
				+getSeatType()
				+", Number of seats: "
				+getNumSeats()
				+". Total cost: $"
				+getTotalCost();
	}
}
