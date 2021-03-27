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
	
	FindCheapestLamp(String dburl, String user, String pass, String type, int number)
	{
		/** 
		Constructor that assigns database properties, order type, and number
		Creates a connection to the database as well
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
	
	public String[] sourceLamp()
	{
		/** 
		Returns a String array of the IDs of the lamps used in the cheapest option and price at end
		Returns the IDs of the possible manufacturers if there is no valid combination
		*/
		
		List<Lamp> lamps = new ArrayList<Lamp>(); 	//array list of Lamp class
		
		int cost = 0; //cost of all the lamps
		ArrayList<String> ids = new ArrayList<String>(); //String array that will be returned
		
		try
		{
			this.readData();	//reads correct rows from the server
			

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
		}
		catch(SQLException e)
		{
			System.out.println("Error in getCheapest");
		}
		
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
				for(int j = 0; j < temp.length - 1; j++)
				{
					ids.add(temp[j]);
				}
				
				//increment the cost
				cost = cost + Integer.parseInt(temp[temp.length -1]);
				
				//remove the used lamps from the arrayList
				for(int j = 0; j < lamps.size()-1; j++)
				{
					for(int x = 0; x < temp.length; x++)
					{
						if(temp[x].equals(lamps.get(j).getID()))
						{
							lamps.remove(j);
						}
					}
				}
				
			}
			
		}
		
		//add cost to the end and return array
		ids.add(Integer.toString(cost));
		String[] ret = new String[ids.size()];
		return ids.toArray(ret);
		
		
	}
	
	public String[] getCheapest(List<Lamp> lamps)
	{
		/** 
		Finds the cheapest combination of lamps currently in the arrayList
		Returns the ids of manufaturers if not valid combo is found
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
						IDs = new String[] {lamps.get(i).getID(), Integer.toString(min)};
					}
					
				}
			}
			
			
			//sees if a combination of 2 laps fulfills order for cheaper
			for(int i = 0; i < lamps.size(); i++)
			{
				for(int j = 0; j < lamps.size(); j++)
				{
					if( lamps.get(i).getBase().equals("Y") && lamps.get(j).getBulb().equals("Y"))
					{
						if(min >= lamps.get(i).getPrice() + lamps.get(j).getPrice())
						{
							min = lamps.get(i).getPrice() + lamps.get(j).getPrice();
							IDs = new String[] {lamps.get(i).getID(), lamps.get(j).getID(), Integer.toString(min)};
						}
					}
				}
			}
		
		//if minimum has been unchanged return array of manufacturer IDs
		if(min == 999999)
		{
			IDs = new String[] {"002", "004", "005"};
		}
		
		for(int i = 0; i < IDs.length; i++)
		{
			System.out.println(IDs[i]);
		}
		System.out.println();
		return IDs;	
		
	}
	
	private void readData()
	{
		/** 
		Places all the lamps of correct type in results
		*/
		try {                    
            Statement state = dbConnect.createStatement();
            results = state.executeQuery("SELECT * FROM lamp WHERE Type = " + "\"" + type + "\"" );
			

        } catch (SQLException ex) {
			System.out.println("Error in readData");
            ex.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) 
	{

        FindCheapestLamp test = new FindCheapestLamp("jdbc:mysql://localhost/inventory","adam","ENSF409","Desk",5);
        String[] data = test.sourceLamp();
		System.out.println(data[0] + "    " + data[1]);
	}
}