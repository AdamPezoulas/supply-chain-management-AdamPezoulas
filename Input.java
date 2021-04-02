

import java.util.Scanner;
import java.util.Arrays;

public class Input {


public static void main(String[] args) {
	
	acceptOrder();
	

}

private static String category; //category of furniture
private static String type; //type of furniture
private static int number;//amount of furniture requested 

static Scanner scan = new Scanner(System.in);//Scanner to allow user input


public static void acceptOrder() {
	/**Function used to validate user input 
	 * 
	 */
	
	//displaying furniture category   
	System.out.println("Please enter one category of furniture (chair, desk, filing, or lamp)");
	//storing furniture category 
	category = scan.next().toLowerCase();
	String[] IDs = null;
	
	//checking to see if user requested chair 
	if(category.equals("chair"))
		
	{	//displaying types of chair
		System.out.println("Please enter one type of chair (Kneeling, Task, Mesh, Executive, Ergonomic)");
		//storing user input for types of chair
		type = scan.next();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of chair(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();

		//Calling function to find cheapest chair
		FindCheapestChair findChair = new FindCheapestChair("jdbc:mysql://localhost/inventory","adam",
			"ENSF409", type, number);
			
		IDs = findChair.sourceChair();

	}
	//checking to see if user requested desk 
	if(category.equals("desk"))
	{	//displaying types of desk
		System.out.println("Please enter one type of desk (Standing, Adjustable, Traditional)");
		//storing user input for type of desk
		type = scan.next();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of desk(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
		//Calling function to find cheapest desk
		
		DeskSupply findDesk = new DeskSupply("jdbc:mysql://localhost/inventory","adam","ENSF409");
		
		findDesk.initializeConnection();
		IDs = findDesk.cheapestDesk(type, number);
	}
	//checking to see if user requested filing 
	if(category.equals("filing"))
	{	//displaying types of filing
		System.out.println("Please Enter one type of filing (Small, Medium, Large)");
		//storing user input for type of filing
		type = scan.next();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of filling(s)");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
		//Calling function to find cheapest filing
		
		
		FilingSupply findFiling = new FilingSupply("jdbc:mysql://localhost/inventory","adam","ENSF409");
		
		findFiling.initializeConnection();
		
		IDs = findFiling.cheapestFiling(type, number);
	}
	//checking to see if user requested lamp
	if(category.equals("lamp"))
	{	//displaying types of lamp
		System.out.println("Please Enter one type of lamp (Desk, Study, Swing Arm)");
		//storing user input for type of lamp
		type = scan.next();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of lamp(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
		//Calling function to find cheapest lamp
		FindCheapestLamp findLamp = new FindCheapestLamp("jdbc:mysql://localhost/inventory","adam",
			"ENSF409", type, number);
			
		IDs = findLamp.sourceLamp();
	}
	
	  for(int i = 0; i < IDs.length; i++)
	  {
		  System.out.println(IDs[i]);
	  }
	  
	  int totalPrice = Integer.parseInt(IDs[IDs.length-1]);
	  
	  String[] IDsOnly = Arrays.copyOf(IDs, IDs.length-1);
	  
	  String path = "C:\\Users\\Pezfa\\Desktop\\HYL\\ProjectFork\\supply-chain-management-AdamPezoulas\\out";
	  FileWriting fileOut = new FileWriting(type, number, IDsOnly, path, totalPrice);
			
	  fileOut.writeFile();
	
  }
  

           
}


