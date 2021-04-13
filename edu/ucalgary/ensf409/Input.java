/**
 * @author Robert Dewar <a href="mailto:robert.dewar@ucalgary.ca">
 *         robert.dewar@ucalgary.ca</a>
 * 
 * @version 1.2
 * 
 * @since 1.0
 * 
 * Class to accept user inputs as furniture order specifications.
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
package edu.ucalgary.ensf409;

import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Input {
	
	private final static String DBURL = "jdbc:mysql://localhost/inventory"; //database URL
	private final static String USER = "scm";		//database username
	private final static String PASS = "ensf409";	//database password
	private static String category; // category of furniture
	private static String type; // type of furniture
	private static String number;// amount of furniture requested

	public static void main(String[] args) {

		acceptOrder();

	}
	

	
	

	static Scanner scan = new Scanner(System.in);// Scanner to allow user input


	/**
		 * Method to check user inputs for errors and exit the program if requested.
		 */
	public static Boolean checkInput( String Input, String Arg) {
		
		// if statement for when user wants to exit the program
		if( Input.equals("quit") || Input.equals("Quit") ) {
			System.out.println("Terminating program.");
			System.exit(1);
		}
		
		// if statement for category select input validation
		if( Arg.equals("category select")) {
			
			if(!Input.equals("chair") && !Input.equals("desk") && !Input.equals("filing") && !Input.equals("lamp")) {
				System.out.println("Not a valid category selection. Please try again Or type quit to exit");
				return false;
			}	
			return true;
		
		}
		// if statement for chair select input validation
		if( Arg.equals("chair select")) {
			
			if(!Input.equals("Kneeling") && !Input.equals("Task") && !Input.equals("Mesh") && !Input.equals("Executive") && !Input.equals("Ergonomic")) {
				System.out.println("Not a valid chair type selection. Please try again Or type quit to exit");
				return false;
			}
			return true;
		
		}
		// if statement for desk select input validation
		if( Arg.equals("desk select")) {
			
			if(!Input.equals("Standing") && !Input.equals("Adjustable") && !Input.equals("Traditional")) {
				System.out.println("Not a valid desk type selection. Please try again Or type quit to exit");
				return false;
			}
			return true;
		}
		// if statement for filing select input validation
		if( Arg.equals("filing select")) {
			
			if(!Input.equals("Small") && !Input.equals("Medium") && !Input.equals("Large")) {
				System.out.println("Not a valid filing type selection. Please try again Or type quit to exit");
				return false;
			}
			return true;
		
		}
		// if statement for lamp select input validation
		if( Arg.equals("lamp select")) {
			
			if(!Input.equals("Desk") && !Input.equals("Study") && !Input.equals("Swing Arm")) {
				System.out.println("Not a valid lamp type selection. Please try again Or type quit to exit");
				return false;
			}
			return true;
		}
		// if statement for quantity select input validation
		else if( Arg.equals("quantity select")) {
			try {
				int num = Integer.parseInt(Input);
				if(num <= 0) {
					System.out.println("Quantity must be greater than 0. Please try again Or type quit to exit");
					return false;
				} //check for non-integer value
				}catch(NumberFormatException e) {
				   System.out.println("Quantity must be an integer value. Please try again Or type quit to exit");
				   return false;
				}
			return true;
		}
		return false;
		
	}
	
	
	
		/**
		 * Method used to recieve user input and assign it to order specifications.
		 */
	
	public static String[] acceptOrder() {
		

		
		
		// to hold returned IDs and totalPrice from internal class methods
		String[] IDs = null;
		// displaying furniture category
		System.out.println("Please enter one category of furniture (Chair, Desk, Filing, or Lamp), or type Quit to exit");
		 // looping through until the user provides an appropriate input
			// storing furniture category
			category = scan.next().toLowerCase();
			while(checkInput(category, "category select") == false) {
				category = scan.next().toLowerCase();
			}
			// checking to see if user requested chair
			if (category.equals("chair")) {
				
				System.out.println("Please enter one type of chair (Kneeling, Task, Mesh, Executive, Ergonomic)");
				
				
				type = scan.next().toLowerCase();
				type = type.substring(0,1).toUpperCase() + type.substring(1);
				// check for correct user input for chair type
				while(checkInput(type, "chair select") == false) {
					type = scan.next().toLowerCase();
					type = type.substring(0,1).toUpperCase() + type.substring(1);
				}
				
				System.out.println("Please enter number of chair(s) you would like to purchase");
				
				number = scan.next();
				// check for correct user input for quantity
				while(checkInput(number, "quantity select") == false) {
					number = scan.next();
				}
				// call method to find cheapest set of chairs
				FindCheapestChair findChair = new FindCheapestChair(DBURL, USER, PASS, type, Integer.parseInt(number));
				// store ids of cheapest chairs
				IDs = findChair.sourceChair();

			}
			// checking to see if user requested desk
			else if (category.equals("desk")) {
				
				System.out.println("Please enter one type of desk (Standing, Adjustable, Traditional)");
				// storing type of desk in type
				type = scan.next().toLowerCase();
				type = type.substring(0,1).toUpperCase() + type.substring(1);
				
				// check for correct user input for desk type
				while(checkInput(type, "desk select") == false) {
					type = scan.next().toLowerCase();
					type = number.substring(0,1).toUpperCase() + type.substring(1);
				}
				
				
				System.out.println("Please enter number of desk(s) you would like to purchase");
				// storing amount of desks being requested in variable called number
				number = scan.next();
				// check for correct user input for quantity
				while(checkInput(number, "quantity select") == false) {
					number = scan.next();
				}		
				
				// call method to find cheapest set of desks
				
				DeskSupply findDesk = new DeskSupply(DBURL, USER, PASS);

				findDesk.initializeConnection();
				// store ids of cheapest desks
				IDs = findDesk.cheapestDesk(type, Integer.parseInt(number));
			}
			// checking to see if user requested filing
			else if (category.equals("filing")) {
				
		
				System.out.println("Please Enter one type of filing (Small, Medium, Large)");
				// storing type of filing in type.
				type = scan.next().toLowerCase();
				type = type.substring(0,1).toUpperCase() + type.substring(1);
				// check for correct user input for filing type
				while(checkInput(type, "filing select") == false) {
					type = scan.next().toLowerCase();
					type = type.substring(0,1).toUpperCase() + type.substring(1);
				}
				
				
				
				System.out.println("Please enter number of filing(s)");
				// storing amount of filings being requested in variable called number
				number = scan.next();
				// check for correct user input for quantity
				while(checkInput(number, "quantity select") == false) {
					number = scan.next();
				}
				// call method to find cheapest set of filings
				FilingSupply findFiling = new FilingSupply(DBURL, USER, PASS);

				findFiling.initializeConnection();
				// store ids of cheapest filings
				IDs = findFiling.cheapestFiling(type, Integer.parseInt(number));
			}
			// checking to see if user requested lamp
			else if (category.equals("lamp")) {
				
				System.out.println("Please Enter one type of lamp (Desk, Study, Swing Arm)");
				// storing type of lamp in type.
				type = scan.next().toLowerCase();
				type = type.substring(0,1).toUpperCase() + type.substring(1);
				// check for correct user input for lamp type
				while(checkInput(type, "lamp select") == false) {
					type = scan.next().toLowerCase();
					type = type.substring(0,1).toUpperCase() + type.substring(1);
				}
				
				
				System.out.println("Please enter number of lamp(s) you would like to purchase");
				// storing amount of lamps being requested in variable called number
				number = scan.next();
				// check for correct user input for quantity
				while(checkInput(number, "quantity select") == false) {
					number = scan.next();
				}
				
				// call method to find cheapest set of lamps
				
				FindCheapestLamp findLamp = new FindCheapestLamp(DBURL, USER, PASS,
						type, Integer.parseInt(number));
				// store ids of cheapest lamps
				IDs = findLamp.sourceLamp();
			}
			
		
		scan.close();
		// calculate total cost of all purchased items
		int totalPrice = Integer.parseInt(IDs[IDs.length - 1]);
		// copy list of ids
		String[] IDsOnly = Arrays.copyOf(IDs, IDs.length - 1);
	
		String path = System.getProperty("user.dir") + "\\";
		// calling FileWriting to write the order form
		FileWriting fileOut = new FileWriting(type, Integer.parseInt(number), IDsOnly, path, totalPrice);

		fileOut.writeFile();
		
		DataBaseUpdate updater = new DataBaseUpdate(DBURL,USER,PASS);
		
		updater.deleteItem(category, IDs);
		
		return IDs;
	}

}