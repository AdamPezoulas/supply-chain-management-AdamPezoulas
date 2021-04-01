package edu.ucalgary.ensf409;

import java.util.Scanner;

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
	
	//checking to see if user requested chair 
	if(category.equals("chair"))
		
	{	//displaying types of chair
		System.out.println("Please enter one type of chair (kneeling, task, mesh, executive, ergonomic)");
		//storing user input for types of chair
		type = scan.next().toLowerCase();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of chair(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
	}
	//checking to see if user requested desk 
	if(category.equals("desk"))
	{	//displaying types of desk
		System.out.println("Please enter one type of desk (standing, adjustable, traditional)");
		//storing user input for type of desk
		type = scan.next().toLowerCase();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of desk(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
	}
	//checking to see if user requested filing 
	if(category.equals("filing"))
	{	//displaying types of filing
		System.out.println("Please Enter one type of filing (small, medium, large)");
		//storing user input for type of filing
		type = scan.next().toLowerCase();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of filling(s)");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
	}
	//checking to see if user requested lamp
	if(category.equals("lamp"))
	{	//displaying types of lamp
		System.out.println("Please Enter one type of lamp (desk, study, swing arm)");
		//storing user input for type of lamp
		type = scan.nextLine();
		//displaying amount of furniture that user wants to purchase
		System.out.println("Please enter number of lamp(s) you would like to purchase");
		//storing amount of furniture being requested in variable called number
		number = scan.nextInt();
	}
	
  }
           
}


