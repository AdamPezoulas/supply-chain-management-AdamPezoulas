
/**
 * Add a catch so they can keep trying to put input the correct way into the file
 */
package edu.ucalgary.ensf409;

import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Input {

	public static void main(String[] args) {

		acceptOrder();

	}
	


	private static String category; // category of furniture
	private static String type; // type of furniture
	private static int number;// amount of furniture requested

	static Scanner scan = new Scanner(System.in);// Scanner to allow user input

	public static String[] acceptOrder() {
		/**
		 * Function used to validate user input
		 * 
		 */

		// creating a holder for easier checking for correct boolean input
		boolean correctCategory = false;
		// to hold returned IDs and totalPrice from internal class methods
		String[] IDs = null;
		// displaying furniture category
		System.out.println("Please enter one category of furniture (Chair, Desk, Filing, or Lamp), or type Quit to exit");
		while (!correctCategory) { // looping through until the user provides an appropriate input
			// storing furniture category
			category = scan.next().toLowerCase();

			// checking to see if user requested chair
			if (category.equals("chair")) {
				correctCategory = true; // found a category so we wanna exit our loop after
				// displaying types of chair
				System.out.println("Please enter one type of chair (Kneeling, Task, Mesh, Executive, Ergonomic)");
				// storing user input for types of chair
				type = scan.next();
				// keep prompting user for a correct input or to quit the program
				while (!(type.equals("Kneeling")) && !(type.equals("Task")) && !(type.equals("Mesh"))
						&& !(type.equals("Executive")) && !(type.equals("Ergonomic"))) {
					System.out.println("Invalid input. Please try again. Or type quit to exit");
					type = scan.next();
					if (type.equals("Quit") || type.equals("quit")) {
						System.out.println("Terminating program.");
						System.exit(1);
					}
				}
				// displaying amount of furniture that user wants to purchase
				System.out.println("Please enter number of chair(s) you would like to purchase");
				// storing amount of furniture being requested in variable called number
				try { // trying to catch if the user doesnt input an integer
					number = scan.nextInt();
					if (number <= 0) { // checking if the integer was greater than zero
						while (number <= 0) {
							System.out.println("Please enter a quantity greater than zero.");
							try {
								number = scan.nextInt();
							} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
								System.err.println("Not a valid quantity.");
								System.exit(1);
							}
						}
					}
				} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
					System.err.println("Not a valid quantity. Terminating Program.");
					System.exit(1);
				}

				// Calling function to find cheapest chair
				FindCheapestChair findChair = new FindCheapestChair("jdbc:mysql://localhost/inventory", "adam",
						"ENSF409", type, number);

				IDs = findChair.sourceChair();

			}
			// checking to see if user requested desk
			else if (category.equals("desk")) {
				correctCategory = true; // found a category so we wanna exit our loop after
				// displaying types of desk
				System.out.println("Please enter one type of desk (Standing, Adjustable, Traditional)");
				// storing user input for type of desk
				type = scan.next();
				// keep prompting user for a correct input or to quit the program
				while (!(type.equals("Standing")) && !(type.equals("Adjustable")) && !(type.equals("Traditional"))) {
					System.out.println("Invalid input. Please try again. Or type quit to exit");
					type = scan.next();
					if (type.equals("Quit") || type.equals("quit")) {
						System.out.println("Terminating program.");
						System.exit(1);
					}
				}
				// displaying amount of furniture that user wants to purchase
				System.out.println("Please enter number of desk(s) you would like to purchase");
				// storing amount of furniture being requested in variable called number
				try { // trying to catch if the user doesnt input an integer
					number = scan.nextInt();
					if (number <= 0) { // checking if the integer was greater than zero
						while (number <= 0) {
							System.out.println("Please enter a quantity greater than zero.");
							try {
								number = scan.nextInt();
							} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
								System.err.println("Not a valid quantity.");
								System.exit(1);
							}
						}
					}
				} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
					System.err.println("Not a valid quantity. Terminating Program.");
					System.exit(1);
				}
				// Calling function to find cheapest desk

				DeskSupply findDesk = new DeskSupply("jdbc:mysql://localhost/inventory", "adam", "ENSF409");

				findDesk.initializeConnection();
				IDs = findDesk.cheapestDesk(type, number);
			}
			// checking to see if user requested filing
			else if (category.equals("filing")) {
				correctCategory = true; // found a category so we wanna exit our loop after
				// displaying types of filing
				System.out.println("Please Enter one type of filing (Small, Medium, Large)");
				// storing user input for type of filing
				type = scan.next();
				// keep prompting user for a correct input or to quit the program
				while (!(type.equals("Small")) && !(type.equals("Medium")) && !(type.equals("Large"))) {
					System.out.println("Invalid input. Please try again. Or type quit to exit");
					type = scan.next();
					if (type.equals("Quit") || type.equals("quit")) {
						System.out.println("Terminating program.");
						System.exit(1);
					}
				}
				// displaying amount of furniture that user wants to purchase
				System.out.println("Please enter number of filing(s)");
				// storing amount of furniture being requested in variable called number
				try { // trying to catch if the user doesnt input an integer
					number = scan.nextInt();
					if (number <= 0) { // checking if the integer was greater than zero
						while (number <= 0) {
							System.out.println("Please enter a quantity greater than zero.");
							try {
								number = scan.nextInt();
							} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
								System.err.println("Not a valid quantity.");
								System.exit(1);
							}
						}
					}
				} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
					System.err.println("Not a valid quantity. Terminating Program.");
					System.exit(1);
				}
				// Calling function to find cheapest filing

				FilingSupply findFiling = new FilingSupply("jdbc:mysql://localhost/inventory", "adam", "ENSF409");

				findFiling.initializeConnection();

				IDs = findFiling.cheapestFiling(type, number);
			}
			// checking to see if user requested lamp
			else if (category.equals("lamp")) {
				correctCategory = true; // found a category so we wanna exit our loop after
				// displaying types of lamp
				System.out.println("Please Enter one type of lamp (Desk, Study, Swing Arm)");
				// storing user input for type of lamp
				type = scan.next();
				while (!(type.equals("Desk")) && !(type.equals("Study")) && !(type.equals("Swing Arm"))) {
					System.out.println("Invalid input. Please try again. Or type quit to exit");
					type = scan.next();
					if (type.equals("Quit") || type.equals("quit")) {
						System.out.println("Terminating program.");
						System.exit(1);
					}
				}
				// displaying amount of furniture that user wants to purchase
				System.out.println("Please enter number of lamp(s) you would like to purchase");
				// storing amount of furniture being requested in variable called number
				try { // trying to catch if the user doesnt input an integer
					number = scan.nextInt();
					if (number <= 0) { // checking if the integer was greater than zero
						while (number <= 0) {
							System.out.println("Please enter a quantity greater than zero.");
							try {
								number = scan.nextInt();
							} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
								System.err.println("Not a valid quantity.");
								System.exit(1);
							}
						}
					}
				} catch (InputMismatchException e) { // if user doesnt enter a number, terminate the program
					System.err.println("Not a valid quantity. Terminating Program.");
					System.exit(1);
				}
				// Calling function to find cheapest lamp
				FindCheapestLamp findLamp = new FindCheapestLamp("jdbc:mysql://localhost/inventory", "adam", "ENSF409",
						type, number);

				IDs = findLamp.sourceLamp();
			} else if (category.equals("Quit") || category.equals("quit")) {// user entered quit, so program will
																			// terminate
				System.out.println("Terminating program.");
				System.exit(1);
			} else {// user input was incorrect
				System.out.println("Incorrect input. Please try again. Or type Quit to exit.");

			}
		}
		scan.close();

		int totalPrice = Integer.parseInt(IDs[IDs.length - 1]);

		String[] IDsOnly = Arrays.copyOf(IDs, IDs.length - 1);

		String path = "C:\\Users\\Pezfa\\Desktop\\HYL\\ProjectFork\\supply-chain-management-AdamPezoulas\\";
		FileWriting fileOut = new FileWriting(type, number, IDsOnly, path, totalPrice);

		fileOut.writeFile();
		
		return IDs;
	}

}
