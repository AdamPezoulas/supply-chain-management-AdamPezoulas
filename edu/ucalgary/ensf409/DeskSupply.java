/**
 * @author Robert Dewar <a href="mailto:robert.dewar@ucalgary.ca">
 *         robert.dewar@ucalgary.ca</a>
 * 
 * @version 1.3
 * 
 * @since 1.0
 * 
 * This program recieves an order for desks and selects the cheapest combination of parts to build them
 */

package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;  

public class DeskSupply{
	
	public final String DBURL; //stores the database URL
    public final String USERNAME; //stores the user's username
    public final String PASSWORD; //stores the user's password
	public Connection dbConnect; 
	public ResultSet results;
	public Boolean check = false;
	public int totalcost=0;
	
	/** 
	*Arraylists to store the database information
	*/
	public static ArrayList<String> deskIDs = new ArrayList<String>();
	public static ArrayList<String> deskLegs = new ArrayList<String>();
	public static ArrayList<String> deskTops = new ArrayList<String>();
	public static ArrayList<String> deskDrawers = new ArrayList<String>();
	public static ArrayList<Integer> deskPrices = new ArrayList<Integer>();
	public static ArrayList<String> BuyList = new ArrayList<String>();
	public static ArrayList<String> pairs = new ArrayList<String>();
	public static ArrayList<String> deskManufacturers = new ArrayList<String>();
	
	/** 
	*method to generate a string of the purchased furniture items
	*/
	
	public String[] getbuyList() {
		String[] Buy = new String[BuyList.size()+1];
		for (int i = 0; i < BuyList.size(); i++) {
			Buy[i] = BuyList.get(i);
		}
		Buy[BuyList.size()] = Integer.toString(totalcost);
		totalcost =0;
		
		return Buy;
	}
	
	/** 
	*method to generate an out of stock message with the relevant manufacturers
	*/
	public String[] errorMessage() {
		
		
		
		ArrayList<String> newdeskManufacturers = new ArrayList<String>();
		for (int i =0;i<deskManufacturers.size(); i++){
				if(!newdeskManufacturers.contains(deskManufacturers.get(i))) {
					newdeskManufacturers.add(deskManufacturers.get(i));
				}
			}
		
		for(int i =0;i < newdeskManufacturers.size();i++) {
		
			try {                    
				Statement myStmt = dbConnect.createStatement();
				results = myStmt.executeQuery("SELECT * FROM manufacturer ");	
				
			
			
				while (results.next()){
					if(results.getString("ManuID").equals(newdeskManufacturers.get(i))) {
						newdeskManufacturers.set(i, results.getString("ManuID"));
					}
				}
			 
			 myStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		newdeskManufacturers.add("-1");
		String[] errorM = new String[newdeskManufacturers.size()];
		
		return newdeskManufacturers.toArray(errorM);
	}
	
	/** 
	*constructor to set user variables
	*/
	
	public DeskSupply(String DBURL, String USERNAME, String PASSWORD) {
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	/** 
	*method to calculate a combination of cheapest possible desks
	*/
	
	public String[] cheapestDesk ( String Type, int quantity) {
		
	
	    
		int cheapestpair=10000; //variable to store prices of cheapest group of indices from pairs arraylist
		int cheapestpairIndex =0; //variable to store cheapest group of indices from pairs arraylist
		
		
		try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM desk ");//access desk table in database
			
			/** 
			*initial block to fill arraylists with database info
			*/
			if ( check == false) {
				deskIDs.clear();
				deskLegs.clear();
				deskTops.clear();
				deskDrawers.clear();
				deskPrices.clear();
				BuyList.clear();
				pairs.clear();
				
				
				
				while (results.next()){
					 
						if(Type.equals(results.getString("Type"))) {
							
							deskIDs.add(results.getString("ID"));
							deskLegs.add(results.getString("Legs"));
							deskTops.add(results.getString("Top"));
							deskDrawers.add(results.getString("Drawer"));
							deskPrices.add(results.getInt("Price"));	
							deskManufacturers.add(results.getString("ManuID"));
						}
					
				}
				
				
				check = true;
				
			}
			
			
					
			
			/** 
			*block to store desks with all three parts available if any exist
			*/
			
			for( int i =0; i< deskIDs.size(); i++) {
				if((deskLegs.get(i).equals( "Y")) && (deskTops.get(i).equals( "Y"))&& (deskDrawers.get(i).equals( "Y"))) {
								
								pairs.add(Integer.toString(i));
								
				}
			
			
			}
			/** 
			*block to get cheapest set of desks with all parts available
			*/
			if(pairs.size() != 0) {
				for( int i =0; i< pairs.size(); i++) {
					if(deskPrices.get(Integer.parseInt(pairs.get(i))) < cheapestpair) {
						cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i)));
						cheapestpairIndex = i;
					}
				}
				
				/** 
				*update all arraylists 
				*/
				BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex))));
				totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(deskIDs.size()==0){
						
					return errorMessage();
				}
					
				deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(deskIDs.size()==0){
					
					return errorMessage();
				}
				deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				pairs.clear();
				
				quantity-=1;
			}
			/** 
			*block to store sets of two desks that contains all parts
			*/
			else if(pairs.size() ==0) {
				for( int i =0; i< deskIDs.size(); i++) {
					for( int j =i+1; j< deskIDs.size(); j++) {			
							
							if((deskLegs.get(i).equals( "Y")||deskLegs.get(j).equals( "Y")) && (deskTops.get(i).equals( "Y")||deskTops.get(j).equals( "Y"))&& (deskDrawers.get(i).equals( "Y")||deskDrawers.get(j).equals( "Y"))) {
								
								pairs.add(Integer.toString(i)+Integer.toString(j));
								
							}
									
					}		
				}
				/** 
				*block to get cheapest set of two desks
				*/
				if(pairs.size() !=0) {
					for( int i =0; i< pairs.size(); i++) {
						if(deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) < cheapestpair) {
							cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
							cheapestpairIndex = i;
						}
					}
					/** 
					*update all arraylists
					*/
					BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
					BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
					totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(deskIDs.size()==0){
					
					return errorMessage();
					}
					deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(deskIDs.size()==0){
					
					return errorMessage();
					}	
					deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					
					
					deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					pairs.clear();
					quantity-=1; 
					
					
				}
				/** 
				*block to store sets of three desks that contains all parts
				*/
				else if(pairs.size() ==0) {
					for( int i =0; i< deskIDs.size(); i++) {
						for( int j =i+1; j< deskIDs.size(); j++) {			
							for( int k =j+1; k< deskIDs.size(); k++) {			
								if((deskLegs.get(i).equals( "Y")||deskLegs.get(j).equals( "Y")||deskLegs.get(k).equals( "Y")) && (deskTops.get(i).equals( "Y")||deskTops.get(j).equals( "Y")||deskTops.get(k).equals( "Y"))&& (deskDrawers.get(i).equals( "Y")||deskDrawers.get(j).equals( "Y")||deskDrawers.get(k).equals( "Y"))) {
									
									pairs.add(Integer.toString(i)+Integer.toString(j)+Integer.toString(k));
									
								}
							}			
						}		
					}	
					/** 
					*return error message if no sets of three or less exist
					*/
					if(pairs.size() == 0) {
						return errorMessage();
						
					}
					/** 
					*block to get cheapest set of three desks
					*/
					else if (pairs.size() != 0) {
						for( int i =0; i< pairs.size(); i++) {
							if(deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(2,3))) < cheapestpair) {
								cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(2,3)));
								cheapestpairIndex = i;
							}
						}
						/** 
						*update all arraylists
						*/
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3))));
						
						totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						
						deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						
						deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						
						deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						deskLegs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						deskTops.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						deskDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						deskPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						pairs.clear();
						quantity-=1;
					
					}
				}
				
				
			}
			
			/** 
			*if there is another desk requested, cheapestDesk is called again
			*/
			
			if(quantity !=0) {
				return cheapestDesk(Type, quantity);
			}
			
			
			/** 
			*close instance of database access
			*/
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		/** 
		*return list of purchased desks
		*/
		check = false;	
		return getbuyList();
		
	}
	/** 
	*method to initialize database connection with login information
	*/
	public void initializeConnection(){
                
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	/** 
	*main method
	*/
	public static void main(String[] args) {
		DeskSupply myJDBC = new DeskSupply("jdbc:mysql://localhost/inventory","root","82he9os12");
        myJDBC.initializeConnection();
        
		
		
		System.out.println(Arrays.toString(myJDBC.cheapestDesk("Adjustable", 5)));
		
    }
}