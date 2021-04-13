/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.3
 * 
 * @since 1.0
 * 
 * This program selects the cheapest combination of chairs to complete an order
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
 package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;

public class FindCheapestChair
{
	public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
	private Connection dbConnect; //connection to DB
	private String type; //Type of Chair wanted
	private int number; //Amount of chairs wanted
	private ResultSet results; //stores all chairs of correct type
	List<Chair> chairs = new ArrayList<Chair>(); 	//array list of chair class
	
	FindCheapestChair(String dburl, String user, String pass, String type, int number)
	{
		/** 
		Constructor that assigns database properties, order type, and number
		Creates a connection to the database as well
		
		@param 	dburl	URL of desired database
		@param	user	username to access the database
		@param 	pass	Database password
		@param 	type	type of chair wanted
		@param	number	number of complete chairs wanted
		*/
		this.DBURL = dburl;
		this.USERNAME = user;
		this.PASSWORD = pass;
		this.type = type;
		this.number = number;
		this.initializeConnection();
		
	}
	
	
	
	private void initializeConnection()
	{
		/** 
		Initializes the connection to the server and stores the connection 
		*/
		//connect to the given database using credentials
		try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
			System.out.println("Error initializing database connection");
            e.printStackTrace();
        }
	}
	
	
	
	
	public String[] sourceChair()
	{
		  /**
     * Calls getCheapest the required number of times to create a string array
     * 	of items required for purchase and total price
     *
     * @return an array of item IDs and final value is total price
     */
		
		
		int cost = 0; //cost of all the chairs
		ArrayList<String> ids = new ArrayList<String>(); //String array that will be returned
		

			this.readData();	//reads correct rows from the server
			
		//runs as many times as needed for the number of chairs
		for(int i = 0; i < this.number; i++)
		{
			
			//call get cheapest to get array of IDs
			String[] temp = getCheapest(chairs);
			
			
			//if returns an array of manufacturer IDs 
			if(Character.isLetter(temp[0].charAt(0)) != true)
			{
				//return manufacturer IDs and price of -1
				return new String[] {"002", "003", "004", "005", "-1"};
			}
			else
			{
				//add IDs gathered to IDs list
				for(int j = 0; j < temp.length; j++)
				{
					ids.add(temp[j]);
				}
				

				
				int[] toRemove = new int[] {-1, -1, -1, -1};
				int remIndex = 0;
				
				//determines the index of chairs used
				for(int j = 0; j < chairs.size(); j++)
				{
					for(int x = 0; x < temp.length; x++)
					{
						String tempChair = chairs.get(j).getID();
						if(temp[x].equals(tempChair))
						{
							toRemove[remIndex] = j;
							remIndex++;
							
							cost = cost + chairs.get(j).getPrice();
						}
					}
				}
				
				//remove the laps used from the array list
				for(int j = 0; j < toRemove.length; j++)
				{
					
					if(toRemove[j] != -1)
					{
						chairs.remove(toRemove[j] - j);
					}
				}
				
			}
			
		}
		
		//add cost to the end and return array
		ids.add(Integer.toString(cost));
		String[] ret = new String[ids.size()];
		return ids.toArray(ret);
		
		
	}
	
	public String[] getCheapest(List<Chair> chairs)
	{
		   /**
     * returns a string array with cheapest available cahirs that complete 1 chair
	 * final value of array is total price of items 
     * @param ArrayList of all available chairs
     * @return array with IDs of chairs needed and total price at the end
     */
		
		int min = 999999; //Initial value of min, larger than any expected value
		String[] IDs = null;  //String array that will be returned
		
		
		
	
			
			//iterates through and finds if there is any chairs that has base and bulb
			//sets min to the price of it and adds to string array
			
			
			for(int i = 0; i < chairs.size(); i++)
			{
				//sees if there is one chair that meets requirements
				if(checkValid(new Chair[] {chairs.get(i)}))
				{
					if(min > chairs.get(i).getPrice())
					{
						min = chairs.get(i).getPrice();
						IDs = new String[] {chairs.get(i).getID()};
					}
					
				}
				
				for(int j = 0; j < chairs.size(); j++)
				{
					//checks if there are 2 chairs together that meet the requirement
					if(checkValid(new Chair[] {chairs.get(i), chairs.get(j)}))
					{
						if(min >= chairs.get(i).getPrice() + chairs.get(j).getPrice())
						{
							min = chairs.get(i).getPrice() + chairs.get(j).getPrice();
							IDs = new String[] {chairs.get(i).getID(), chairs.get(j).getID()};
						}
					}
					
					//checks if there are three chairs together that meet the requirement
					for(int x = 0; x < chairs.size(); x++)
					{
						if(checkValid(new Chair[] {chairs.get(i), chairs.get(j), chairs.get(x)}))
						{
							if(min >= chairs.get(i).getPrice() + chairs.get(j).getPrice() + chairs.get(x).getPrice())
							{
								min = chairs.get(i).getPrice() + chairs.get(j).getPrice() + chairs.get(x).getPrice();
								IDs = new String[] {chairs.get(i).getID(), chairs.get(j).getID(), chairs.get(x).getID()};
							}
						}
						
						//checks for a 4 chair combination that meet requirements
						for(int y = 0; y < chairs.size(); y++)
						{
							if(checkValid(new Chair[] {chairs.get(i), chairs.get(j), chairs.get(x), chairs.get(y)}))
							{
								if(min >= chairs.get(i).getPrice() + chairs.get(j).getPrice() + chairs.get(x).getPrice() + chairs.get(y).getPrice())
								{
									min = chairs.get(i).getPrice() + chairs.get(j).getPrice() + chairs.get(x).getPrice() + chairs.get(y).getPrice();
									IDs = new String[] {chairs.get(i).getID(), chairs.get(j).getID(), chairs.get(x).getID(), chairs.get(y).getID()};
								}
							}
						}
					}
				}
			}
			
			
		
		//if minimum has been unchanged return array of manufacturer IDs
		if(min == 999999)
		{
			IDs = new String[] {"002", "004", "005"};
		}
		
		return IDs;	
		
	}
	
	private void readData()
	{
		  /**
     * Creates an Array List of all chairs currently in database of desired type
     * 
     *
     */
		try {                    
            Statement state = dbConnect.createStatement();
            results = state.executeQuery("SELECT * FROM chair WHERE Type = " + "\"" + type + "\"" );


			while (results.next())
			{
				//Creates and adds chair objects to the arrayList
				Chair seat = new Chair();
				seat.setID(results.getString("ID"));
				seat.setLegs(results.getString("Legs"));
				seat.setArms(results.getString("Arms"));
				seat.setSeat(results.getString("Seat"));
				seat.setCushion(results.getString("Cushion"));
				seat.setPrice(results.getInt("Price"));
				seat.setManuID(results.getString("manuID"));
				chairs.add(seat);
			}

        } catch (SQLException ex) {
			System.out.println("Error in readData");
            ex.printStackTrace();
        }
	}
	
	
	public boolean checkValid(Chair[] input)
	{
		/** 
		*Checks array of chairs to see if it contains a valid combination
		* @param Array of chairs
		* @return boolean of if chairs contain all required parts
		*/
		
		//stores specific part information
		boolean legs = false;
		boolean arms = false;
		boolean seat = false;
		boolean cushion = false;
		
		//looks through array and updates part list
		for(int i = 0; i < input.length; i++)
		{
			if(input[i].getLegs().equals("Y"))
			{
				legs = true;
			}
			
			if(input[i].getArms().equals("Y"))
			{
				arms = true;
			}
			
			if(input[i].getSeat().equals("Y"))
			{
				seat = true;
			}
			
			if(input[i].getCushion().equals("Y"))
			{
				cushion = true;
			}
		}
		
		//checks if all parts are included
		if( legs == true && arms == true && seat == true && cushion == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}