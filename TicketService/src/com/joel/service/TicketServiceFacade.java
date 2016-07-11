package com.joel.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class TicketServiceFacade 
{
	TicketService service;
	BufferedReader bufferedReader;
	
	public TicketServiceFacade() 
	{
		service=new TicketService();
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private void showAvailableSeats()
	{
		System.out.println("Enter a value between 1 to 4 to see the seat availability of that level:");
		try 
		{
			String input=bufferedReader.readLine();
			if(input.isEmpty())
				input="0";
			Optional<Integer> venueLevel =Optional.ofNullable(Integer.parseInt(input));
			System.out.println("Number of seats available at this level are: "
			+service.numSeatsAvailable(venueLevel));
		}
		catch (NumberFormatException | IOException e)
		{
			System.out.println("Unable to process request. Please try again.");
			e.printStackTrace();
		}
	}
	
	private void findAndHoldSeats()
	{
		String input="";
		try 
		{
			System.out.println("Enter the number of seats you would like to reserve: ");
			int numSeats=Integer.parseInt(bufferedReader.readLine());
			
			
			System.out.println("Optionally please specify minimum venue level or just press enter:");
			input=bufferedReader.readLine().trim();
			if(input.isEmpty()) input="1";
			Optional<Integer> minLevel=Optional.ofNullable(Integer.parseInt(input));
			
			System.out.println("Optionally please specify maximum venue level or just press enter:");
			input=bufferedReader.readLine().trim();
			if(input.isEmpty()) input="4";
			Optional<Integer> maxLevel=Optional.ofNullable(Integer.parseInt(input));
			
			
			System.out.println("Please enter your email:");
			String customerEmail=bufferedReader.readLine();
			System.out.println(
					service.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail)
					);
		} 
		catch (NumberFormatException | IOException e) 
		{
			System.out.println("Unable to process request. Please try again.");
			e.printStackTrace();
		}
	}
	
	private void confirmHeldSeats()
	{
		try 
		{
			System.out.print("Enter seat hold id: ");
			int seatHoldId=Integer.parseInt(bufferedReader.readLine());
			System.out.print("Enter customer email: ");
			String customerEmail=bufferedReader.readLine();
			System.out.println(service.reserveSeats(seatHoldId, customerEmail));
		}
		catch (IOException e)
		{
			System.out.println("Unable to process request. Please try again");
			e.printStackTrace();
			
		}
	}
	
	private void viewReservationDetails()
	{
		int reservationId=0;
		try 
		{
			System.out.println("Enter reservationId:");
			String input=bufferedReader.readLine();
			if(input.isEmpty())
				input="0";
			reservationId=Integer.parseInt(input);
			System.out.println(service.getReservationDetails(reservationId));
		} 
		catch (IOException e)
		{
			System.out.println("Unable to process request. Please try again");
			e.printStackTrace();	
		}
		
	}
	
	public void execute()
	{
		int ch=0;
		while(true)
		{
			ch=0;
			System.out.println("\nTicket Service System: ");
			System.out.println("1. Press 1 to see avaiable seats");
			System.out.println("2. Press 2 to hold seats");
			System.out.println("3. Press 3 to confirm held seats");
			System.out.println("4. Press 4 to see reservation details");
			System.out.println("5. Press 5 to exit the system.");
			try 
			{
				ch=Integer.parseInt(bufferedReader.readLine());
			} 
			catch (NumberFormatException | IOException e) 
			{
				System.out.println("Unable to process request. Please try again.");
				e.printStackTrace();
				ch=0;
				
			}
			switch(ch)
			{
				case 1:
					this.showAvailableSeats();
					break;
				
				case 2:
					this.findAndHoldSeats();
					break;
					
				case 3:
					this.confirmHeldSeats();
					break;
					
				case 4:
					this.viewReservationDetails();
					break;
				
				case 5:
					System.exit(0);
				
				default:
					System.out.println("Invalid entry. Try again.");
			}
		}
	}
}