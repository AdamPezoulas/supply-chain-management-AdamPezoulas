/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * This program selects the cheapest combination of lamps to complete an order
 */
package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;

public class FindCheapestLamp
{
	public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
	private Connection dbConnect; //connection to DB
	private String type; //Type of lamp wanted
	private int number; //Amount of lamps wanted
	private ResultSet results; //stores all lamps of correct type
	List<Lamp> lamps = new ArrayList<Lamp>(); 	//array list of Lamp class
	
	FindCheapestLamp(String dburl, String user, String pass, String type, int number)
	{
		/** 
		Constructor that assigns database properties, order type, and number
		Creates a connection to the database as well
		
		@param 	dburl	URL of desired database
		@param	user	username to access the database
		@param 	pass	Database password
		@param 	type	type of lamp wanted
		@param	number	number of complete lamps wanted
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
     * Creates a connection to the database and stores the connection in dbconnect
     * 
     */
		//connect to the given database using credentials
		try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
			System.out.println("Error initializing database connection");
            e.printStackTrace();
        }
	}
	
	
	
	
	public String[] sourceLamp()
	{
		    /**
     * Calls getCheapest the required number of times to create a string array
     * 	of items required for purchase and total price
     *
     * @return an array of item IDs and final value is total price
     */
		
		
		
		int cost = 0; //cost of all the lamps
		ArrayList<String> ids = new ArrayList<String>(); //String array that will be returned
		

			this.readData();	//reads correct rows from the server
			
		//runs as many times as needed for the number of lamps
		for(int i = 0; i < this.number; i++)
		{
			
			//call get cheapest to get array of IDs
			String[] temp = getCheapest(lamps);
			
			
			//if returns an array of manufacturer IDs 
			if(Character.isLetter(temp[0].charAt(0)) != true)
			{
				//return manufacturer IDs and price of -1
				return new String[] {"002", "004", "005", "-1"};
			}
			else
			{
				//add IDs gathered to IDs list
				for(int j = 0; j < temp.length; j++)
				{
					ids.add(temp[j]);
				}
				

				
				int[] toRemove = new int[] {-1, -1};
				int remIndex = 0;
				
				//determines the index of lamps used
				for(int j = 0; j < lamps.size(); j++)
				{
					for(int x = 0; x < temp.length; x++)
					{
						String tempLamp = lamps.get(j).getID();
						if(temp[x].equals(tempLamp))
						{
							toRemove[remIndex] = j;
							remIndex++;
							
							cost = cost + lamps.get(j).getPrice();
						}
					}
				}
				
				//remove the laps used from the array list
				for(int j = 0; j < toRemove.length; j++)
				{
					
					if(toRemove[j] != -1)
					{
						lamps.remove(toRemove[j] - j);
					}
				}
				
			}
			
		}
		
		//add cost to the end and return array
		ids.add(Integer.toString(cost));
		String[] ret = new String[ids.size()];
		return ids.toArray(ret);
		
		
	}
	
	private String[] getCheapest(List<Lamp> lamps)
	{
		    /**
     * returns a string array with cheapest available lamps that complete 1 lamp
	 * final value of array is total price of items 
     * @param ArrayList of all available lamps
     * @return array with IDs of lamps needed and total price at the end
     */
		
		int min = 999999; //Initial value of min, larger than any expected value
		String[] IDs = null;  //String array that will be returned
		
		
		
	
			
			//iterates through and finds if there is any single lamp that has base and bulb
			//sets min to the price of it and adds to string array
			for(int i = 0; i < lamps.size(); i++)
			{
				if(lamps.get(i).getBase().equals("Y") && lamps.get(i).getBulb().equals("Y"))
				{
					if(min > lamps.get(i).getPrice())
					{
						min = lamps.get(i).getPrice();
						IDs = new String[] {lamps.get(i).getID()};
					}
					
				}
				
				for(int j = 0; j < lamps.size(); j++)
				{
					if( lamps.get(i).getBase().equals("Y") && lamps.get(j).getBulb().equals("Y"))
					{
						if(min > lamps.get(i).getPrice() + lamps.get(j).getPrice())
						{
							min = lamps.get(i).getPrice() + lamps.get(j).getPrice();
							IDs = new String[] {lamps.get(i).getID(), lamps.get(j).getID()};
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
     * Creates an Array List of all lamps currently in database of desired type
     * 
     *
     */
		try {                    
            Statement state = dbConnect.createStatement();
            results = state.executeQuery("SELECT * FROM lamp WHERE Type = " + "\"" + type + "\"" );


			while (results.next())
			{
				//Creates and adds Lamp objects to the arrayList
				Lamp lam = new Lamp();
				lam.setID(results.getString("ID"));
				lam.setBase(results.getString("Base"));
				lam.setBulb(results.getString("Bulb"));
				lam.setPrice(results.getInt("Price"));
				lam.setManuID(results.getString("manuID"));
				lamps.add(lam);
			}

        } catch (SQLException ex) {
			System.out.println("Error in readData");
            ex.printStackTrace();
        }
	}
	
}