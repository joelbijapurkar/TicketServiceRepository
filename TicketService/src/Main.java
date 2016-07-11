import java.io.IOException;

import com.joel.service.TicketServiceFacade;

public class Main 
{
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		TicketServiceFacade facade=new TicketServiceFacade();
		facade.execute();
	}
}
