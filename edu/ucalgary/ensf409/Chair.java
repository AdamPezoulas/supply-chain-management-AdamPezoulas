/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Class to store Chair information
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
 package edu.ucalgary.ensf409;
public class Chair {
    // Persistent Fields:
    private String id;
    private String legs;
	private String arms;
	private String seat;
	private String cushion;
	private int price;
	private String manuID;

    
    Chair () {
    }

    // Accessor Methods:
    public String getID()
	{
		return this.id;
	}
    
	public String getLegs()
	{
		return this.legs;
	}
	
	public String getArms()
	{
		return this.arms;
	}
	
	public String getSeat()
	{
		return this.seat;
	}
	
	public String getCushion()
	{
		return this.cushion;
	}
	
	public int getPrice()
	{
		return this.price;
	}
	
	public String getManuID()
	{
		return this.manuID;
	}
	
	public String setID(String ID)
	{
		return this.id = ID;
	}
    
	public String setLegs(String legs)
	{
		return this.legs = legs;
	}
	
	public String setArms(String arms)
	{
		return this.arms = arms;
	}
	
	public String setSeat(String seat)
	{
		return this.seat = seat;
	}
	
	public String setCushion(String cushion)
	{
		return this.cushion = cushion;
	}
	
	public int setPrice(int price)
	{
		return this.price = price;
	}
	
	public String setManuID(String manuID)
	{
		return this.manuID = manuID;
	}

}