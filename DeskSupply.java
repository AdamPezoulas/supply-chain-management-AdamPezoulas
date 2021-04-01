import java.sql.*;
import java.util.*;  

public class DeskSupply{
	
	public final String DBURL; 
    public final String USERNAME; 
    public final String PASSWORD;
	public Connection dbConnect;
	public ResultSet results;
	public Boolean check = false;
	public int totalcost=0;
	
	public static ArrayList<String> deskIDs = new ArrayList<String>();
	public static ArrayList<String> deskLegs = new ArrayList<String>();
	public static ArrayList<String> deskTops = new ArrayList<String>();
	public static ArrayList<String> deskDrawers = new ArrayList<String>();
	public static ArrayList<Integer> deskPrices = new ArrayList<Integer>();
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
	
	
	
	public DeskSupply(String DBURL, String USERNAME, String PASSWORD) {
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	public String[] cheapestDesk ( String Type, int quantity) {
		
	    String[] error = {"The request cannot be filled. Suggested manufacturers: Academic Desks, Office Furnishings, Furniture Goods and Fine Office Supplies.", "-1"};
		int cheapestpair=10000;
		int cheapestpairIndex =0;
		
		
		try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM desk ");
			
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
							
						}
					
				}
				
				
				check = true;
				
			}
			
			
					
			
			
			
			
			for( int i =0; i< deskIDs.size(); i++) {
				if((deskLegs.get(i).equals( "Y")) && (deskTops.get(i).equals( "Y"))&& (deskDrawers.get(i).equals( "Y"))) {
								
								pairs.add(Integer.toString(i));
								
				}
			
			
			}
			
			if(pairs.size() != 0) {
				for( int i =0; i< pairs.size(); i++) {
					if(deskPrices.get(Integer.parseInt(pairs.get(i))) < cheapestpair) {
						cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
						cheapestpairIndex = i;
					}
				}
				
				
				BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex))));
				totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex)));
				if(deskIDs.size()==0){
						
					return error;
				}
					
				deskIDs.remove(pairs.get(cheapestpairIndex));
				if(deskIDs.size()==0){
					
					return error;
				}
				deskLegs.remove(pairs.get(cheapestpairIndex));
				deskTops.remove(pairs.get(cheapestpairIndex));
				deskDrawers.remove(pairs.get(cheapestpairIndex));
				deskPrices.remove(pairs.get(cheapestpairIndex));
				pairs.clear();
				
				quantity-=1;
			}
			
			else if(pairs.size() ==0) {
				for( int i =0; i< deskIDs.size(); i++) {
					for( int j =i+1; j< deskIDs.size(); j++) {			
							
							if((deskLegs.get(i).equals( "Y")||deskLegs.get(j).equals( "Y")) && (deskTops.get(i).equals( "Y")||deskTops.get(j).equals( "Y"))&& (deskDrawers.get(i).equals( "Y")||deskDrawers.get(j).equals( "Y"))) {
								
								pairs.add(Integer.toString(i)+Integer.toString(j));
								
							}
									
					}		
				}
				if(pairs.size() !=0) {
					for( int i =0; i< pairs.size(); i++) {
						if(deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) < cheapestpair) {
							cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2)));
							cheapestpairIndex = i;
						}
					}
					
					BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
					BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
					totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(deskIDs.size()==0){
					
					return error;
					}
					deskIDs.remove(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2)));
					
					if(deskIDs.size()==0){
					
					return error;
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
					if(pairs.size() == 0) {
						return error;
						
					}
					else if (pairs.size() != 0) {
						for( int i =0; i< pairs.size(); i++) {
							if(deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(2,3))) < cheapestpair) {
								cheapestpair = deskPrices.get(Integer.parseInt(pairs.get(i).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(i).substring(2,3)));
								cheapestpairIndex = i;
							}
						}
						
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))));
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))));
						
						BuyList.add(deskIDs.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3))));
						
						totalcost += deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(0,1))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(1,2))) + deskPrices.get(Integer.parseInt(pairs.get(cheapestpairIndex).substring(2,3)));
						
						
						deskIDs.remove(pairs.get(cheapestpairIndex).substring(0,1));
						
						deskIDs.remove(pairs.get(cheapestpairIndex).substring(1,2));
						
						deskIDs.remove(pairs.get(cheapestpairIndex).substring(2,3));
						
						deskLegs.remove(pairs.get(cheapestpairIndex).substring(2,3));
						deskLegs.remove(pairs.get(cheapestpairIndex).substring(1,2));
						deskLegs.remove(pairs.get(cheapestpairIndex).substring(0,1));
						deskTops.remove(pairs.get(cheapestpairIndex).substring(2,3));
						deskTops.remove(pairs.get(cheapestpairIndex).substring(1,2));
						deskTops.remove(pairs.get(cheapestpairIndex).substring(0,1));
						deskDrawers.remove(pairs.get(cheapestpairIndex).substring(2,3));
						deskDrawers.remove(pairs.get(cheapestpairIndex).substring(1,2));
						deskDrawers.remove(pairs.get(cheapestpairIndex).substring(0,1));
						deskPrices.remove(pairs.get(cheapestpairIndex).substring(2,3));
						deskPrices.remove(pairs.get(cheapestpairIndex).substring(1,2));
						deskPrices.remove(pairs.get(cheapestpairIndex).substring(0,1));
						pairs.clear();
						quantity-=1;
					
					}
				}
				
				
			}
			
			
			if(quantity !=0) {
				return cheapestDesk(Type, quantity);
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
		DeskSupply myJDBC = new DeskSupply("jdbc:mysql://localhost/inventory","root","82he9os12");
        myJDBC.initializeConnection();
        
		
		
		System.out.println(Arrays.toString(myJDBC.cheapestDesk("Adjustable", 2)));
		
    }
}