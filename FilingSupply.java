/**
 * @author Robert Dewar <a href="mailto:robert.dewar@ucalgary.ca">
 *         robert.dewar@ucalgary.ca</a>
 * 
 * @version 1.3
 * 
 * @since 1.0
 * 
 * This program recieves an order for filing and selects the cheapest combination of parts to build them
 */

import java.sql.*;
import java.util.*;  

public class FilingSupply{
	
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
	public static ArrayList<String> filingIDs = new ArrayList<String>();
	public static ArrayList<String> filingRails = new ArrayList<String>();
	public static ArrayList<String> filingDrawers = new ArrayList<String>();
	public static ArrayList<String> filingCabinets = new ArrayList<String>();
	public static ArrayList<Integer> filingPrices = new ArrayList<Integer>();
	public static ArrayList<String> BuyList = new ArrayList<String>();
	public static ArrayList<String> pairs = new ArrayList<String>();
	public static ArrayList<String> filingManufacturers = new ArrayList<String>();
	
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
	*constructor to set user variables
	*/
	public FilingSupply(String DBURL, String USERNAME, String PASSWORD) {
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	/** 
	*method to generate an out of stock message with the relevant manufacturers
	*/
	public String[] errorMessage() {
		
		
		
		ArrayList<String> newfilingManufacturers = new ArrayList<String>();
		for (int i =0;i<filingManufacturers.size(); i++){
				if(!newfilingManufacturers.contains(filingManufacturers.get(i))) {
					newfilingManufacturers.add(filingManufacturers.get(i));
				}
			}
		
		for(int i =0;i < newfilingManufacturers.size();i++) {
		
			try {                    
				Statement myStmt = dbConnect.createStatement();
				results = myStmt.executeQuery("SELECT * FROM manufacturer ");	
				
			
			
				while (results.next()){
					if(results.getString("ManuID").equals(newfilingManufacturers.get(i))) {
						newfilingManufacturers.set(i, results.getString("ManuID"));
					}
				}
			 
			 myStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
	
		newfilingManufacturers.add("-1");
		String[] errorM = new String[newfilingManufacturers.size()];
		
		return newfilingManufacturers.toArray(errorM);
	}
	/** 
	*method to calculate a combination of cheapest possible filings
	*/
	public String[] cheapestFiling ( String Type, int quantity) {
		
	 
		int cheapestpair=10000; //variable to store prices of cheapest group of indices from pairs arraylist
		int cheapestpairIndex =0; //variable to store cheapest group of indices from pairs arraylist
		
		try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM filing ");//access desk table in database
			
			/** 
			*initial block to fill arraylists with database info
			*/
			if ( check == false) {
				filingIDs.clear();
				filingRails.clear();
				filingDrawers.clear();
				filingCabinets.clear();
				filingPrices.clear();
				BuyList.clear();
				pairs.clear();
				
				
				
				while (results.next()){
					 
					if(Type.equals(results.getString("Type"))) {
						
						filingIDs.add(results.getString("ID"));
						filingRails.add(results.getString("Rails"));
						filingDrawers.add(results.getString("Drawers"));
						filingCabinets.add(results.getString("Cabinet"));
						filingPrices.add(results.getInt("Price"));	
						filingManufacturers.add(results.getString("ManuID"));
						
					}
					
				}
				
				
				check = true;
				
			}
			
			
			
				
			
			
			/** 
			*block to store filings with all three parts available if any exist
			*/
			
			for( int i =0; i< filingIDs.size(); i++) {
				if((filingRails.get(i).equals( "Y")) && (filingDrawers.get(i).equals( "Y"))&& (filingCabinets.get(i).equals( "Y"))) {
								
								pairs.add(Integer.toString(i));
								
				}
			
			
			}
			/** 
			*block to get cheapest set of filings with all parts available
			*/
			if(pairs.size() != 0) {
				for( int i =0; i< pairs.size(); i++) {
					if(filingPrices.get(Integer.parseInt(pairs.get(i))) < cheapestpair) {
						cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i)));
						cheapestpairIndex = i;
					}
				}
				/** 
				*update all arraylists 
				*/
				
				BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex))));
				totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(filingIDs.size()==0){
					return errorMessage();
					
				}
					
				filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(filingIDs.size()==0){
					return errorMessage();
					
				}
				filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex)));
				pairs.clear();
				
				quantity-=1;
			}
			/** 
			*block to store sets of two filings that contains all parts
			*/
			else if(pairs.size() ==0) {
				for( int i =0; i< filingIDs.size(); i++) {
					for( int j =i+1; j< filingIDs.size(); j++) {			
							
							if((filingRails.get(i).equals( "Y")||filingRails.get(j).equals( "Y")) && (filingDrawers.get(i).equals( "Y")||filingDrawers.get(j).equals( "Y"))&& (filingCabinets.get(i).equals( "Y")||filingCabinets.get(j).equals( "Y"))) {
								
								pairs.add(Integer.toString(i)+Integer.toString(j));
								
							}
									
					}		
				}
				/** 
				*block to get cheapest set of two filings
				*/
				if(pairs.size() !=0) {
					for( int i =0; i< pairs.size(); i++) {
						if(filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) < cheapestpair) {
							cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
							cheapestpairIndex = i;
						}
					}
					/** 
					*update all arraylists
					*/
					
					BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
					BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
					totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(filingIDs.size()==0){
						return errorMessage();
					
					}
					filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(filingIDs.size()==0){
						return errorMessage();
					
					}	
					filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					
					
					filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
					pairs.clear();
					quantity-=1; 
					
					
				}
				/** 
				*block to store sets of three filings that contains all parts
				*/
				else if(pairs.size() ==0) {
					for( int i =0; i< filingIDs.size(); i++) {
						for( int j =i+1; j< filingIDs.size(); j++) {			
							for( int k =j+1; k< filingIDs.size(); k++) {			
								if((filingRails.get(i).equals( "Y")||filingRails.get(j).equals( "Y")||filingRails.get(k).equals( "Y")) && (filingDrawers.get(i).equals( "Y")||filingDrawers.get(j).equals( "Y")||filingDrawers.get(k).equals( "Y"))&& (filingCabinets.get(i).equals( "Y")||filingCabinets.get(j).equals( "Y")||filingCabinets.get(k).equals( "Y"))) {
									
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
					*block to get cheapest set of three filings
					*/
					else if (pairs.size() != 0) {
						for( int i =0; i< pairs.size(); i++) {
							if(filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(2,3))) < cheapestpair) {
								cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(2,3)));
								cheapestpairIndex = i;
							}
						}
						
						/** 
						*update all arraylists
						*/
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
						
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
						
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3))));
						
						totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						
						filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						
						filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						
						filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						filingRails.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						filingDrawers.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						filingCabinets.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
						filingPrices.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1)));
						pairs.clear();
						quantity-=1;
					
					}
				}
				
				
			}
			
			/** 
			*if there is another filing requested, cheapestFiling is called again
			*/
			if(quantity !=0) {
				return cheapestFiling(Type, quantity);
			}
			
			
			/** 
			*close instance of database access
			*/
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		/** 
		*return list of purchased filings
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
		FilingSupply myJDBC = new FilingSupply("jdbc:mysql://localhost/inventory","adam","ENSF409");
        myJDBC.initializeConnection();
        
		System.out.println(Arrays.toString(myJDBC.cheapestFiling("Large", 8)));
	
		
    }
}
