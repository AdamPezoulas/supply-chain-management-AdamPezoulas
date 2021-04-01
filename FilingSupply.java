import java.sql.*;
import java.util.*;  

public class FilingSupply{
	
	public final String DBURL; 
    public final String USERNAME; 
    public final String PASSWORD;
	public Connection dbConnect;
	public ResultSet results;
	public Boolean check = false;
	public int totalcost=0;
	
	public static ArrayList<String> filingIDs = new ArrayList<String>();
	public static ArrayList<String> filingRails = new ArrayList<String>();
	public static ArrayList<String> filingDrawers = new ArrayList<String>();
	public static ArrayList<String> filingCabinets = new ArrayList<String>();
	public static ArrayList<Integer> filingPrices = new ArrayList<Integer>();
	public static ArrayList<String> BuyList = new ArrayList<String>();
	public static ArrayList<String> pairs = new ArrayList<String>();
	
	
	public String[] getbuyList() {
		String[] Buy = new String[BuyList.size()+1];
		for (int i = 0; i < BuyList.size(); i++) {
			Buy[i] = BuyList.get(i);
		}
		Buy[BuyList.size()] = Integer.toString(totalcost);
		totalcost =0;
		
		return Buy;
	}
	
	
	
	public FilingSupply(String DBURL, String USERNAME, String PASSWORD) {
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	public String[] cheapestFiling ( String Type, int quantity) {
		
	    String[] error = {"The request cannot be filled. Suggested manufacturers: Office Furnishings, Furniture Goods and Fine Office Supplies.", "-1"};
		int cheapestpair=10000;
		int cheapestpairIndex =0;
		
		
		try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM filing ");
			
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
						
					}
					
				}
				
				
				check = true;
				
			}
			
			
					
			
			
			
			
			for( int i =0; i< filingIDs.size(); i++) {
				if((filingRails.get(i).equals( "Y")) && (filingDrawers.get(i).equals( "Y"))&& (filingCabinets.get(i).equals( "Y"))) {
								
								pairs.add(Integer.toString(i));
								
				}
			
			
			}
			
			if(pairs.size() != 0) {
				for( int i =0; i< pairs.size(); i++) {
					if(filingPrices.get(Integer.parseInt(pairs.get(i))) < cheapestpair) {
						cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
						cheapestpairIndex = i;
					}
				}
				
				
				BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex))));
				totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(filingIDs.size()==0){
						
					return error;
				}
					
				filingIDs.remove(pairs.get(cheapestpairIndex));
				if(filingIDs.size()==0){
					
					return error;
				}
				filingRails.remove(pairs.get(cheapestpairIndex));
				filingDrawers.remove(pairs.get(cheapestpairIndex));
				filingCabinets.remove(pairs.get(cheapestpairIndex));
				filingPrices.remove(pairs.get(cheapestpairIndex));
				pairs.clear();
				
				quantity-=1;
			}
			
			else if(pairs.size() ==0) {
				for( int i =0; i< filingIDs.size(); i++) {
					for( int j =i+1; j< filingIDs.size(); j++) {			
							
							if((filingRails.get(i).equals( "Y")||filingRails.get(j).equals( "Y")) && (filingDrawers.get(i).equals( "Y")||filingDrawers.get(j).equals( "Y"))&& (filingCabinets.get(i).equals( "Y")||filingCabinets.get(j).equals( "Y"))) {
								
								pairs.add(Integer.toString(i)+Integer.toString(j));
								
							}
									
					}		
				}
				if(pairs.size() !=0) {
					for( int i =0; i< pairs.size(); i++) {
						if(filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) < cheapestpair) {
							cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
							cheapestpairIndex = i;
						}
					}
					
					BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
					BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
					totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(filingIDs.size()==0){
					
					return error;
					}
					filingIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(filingIDs.size()==0){
					
					return error;
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
					if(pairs.size() == 0) {
						return error;
						
					}
					else if (pairs.size() != 0) {
						for( int i =0; i< pairs.size(); i++) {
							if(filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(2,3))) < cheapestpair) {
								cheapestpair = filingPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(i).substring(2,3)));
								cheapestpairIndex = i;
							}
						}
						
						
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
						
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
						
						BuyList.add(filingIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3))));
						
						totalcost += filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))) + filingPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						
						filingIDs.remove(pairs.get(cheapestpairIndex).substring(0,1));
						
						filingIDs.remove(pairs.get(cheapestpairIndex).substring(1,2));
						
						filingIDs.remove(pairs.get(cheapestpairIndex).substring(2,3));
						
						filingRails.remove(pairs.get(cheapestpairIndex).substring(2,3));
						filingRails.remove(pairs.get(cheapestpairIndex).substring(1,2));
						filingRails.remove(pairs.get(cheapestpairIndex).substring(0,1));
						filingDrawers.remove(pairs.get(cheapestpairIndex).substring(2,3));
						filingDrawers.remove(pairs.get(cheapestpairIndex).substring(1,2));
						filingDrawers.remove(pairs.get(cheapestpairIndex).substring(0,1));
						filingCabinets.remove(pairs.get(cheapestpairIndex).substring(2,3));
						filingCabinets.remove(pairs.get(cheapestpairIndex).substring(1,2));
						filingCabinets.remove(pairs.get(cheapestpairIndex).substring(0,1));
						filingPrices.remove(pairs.get(cheapestpairIndex).substring(2,3));
						filingPrices.remove(pairs.get(cheapestpairIndex).substring(1,2));
						filingPrices.remove(pairs.get(cheapestpairIndex).substring(0,1));
						pairs.clear();
						quantity-=1;
					
					}
				}
				
				
			}
			
			
			if(quantity !=0) {
				return cheapestFiling(Type, quantity);
			}
			
			
			
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		
		check = false;	
		return getbuyList();
		
	}
	
	public void initializeConnection(){
                
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		FilingSupply myJDBC = new FilingSupply("jdbc:mysql://localhost/inventory","root","82he9os12");
        myJDBC.initializeConnection();
        
		
		
		System.out.println(Arrays.toString(myJDBC.cheapestFiling("Small", 1)));
		
    }
}