package com.joel.serviceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator 
{
	Random random;
	DateFormat date;
	
	public IdGenerator() {
		random = new Random();
		date = new SimpleDateFormat("yyMMddhhmm");
	}
	
	public int getId(String customerEmail)
	{
		String currentDateTime= date.format(new Date()) ;
		int dateHashcode=Math.abs(currentDateTime.hashCode())%10000;
		int emailHashcode=Math.abs(customerEmail.hashCode())%10000;
		String finalCode=dateHashcode+""+emailHashcode;
		return Integer.parseInt(finalCode);
	}
}