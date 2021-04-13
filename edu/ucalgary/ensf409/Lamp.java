/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Class to store Lamp information
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
 package edu.ucalgary.ensf409;

public class Lamp {
    // Persistent Fields:
    private String id;
    private String base;
	private String bulb;
	private int price;
	private String manuID;

    
    Lamp () {
    }

    // Accessor Methods:
    public String getID()
	{
		return this.id;
	}
    
	public String getBase()
	{
		return this.base;
	}
	
	public String getBulb()
	{
		return this.bulb;
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
    
	public String setBase(String base)
	{
		return this.base = base;
	}
	
	public String setBulb(String bulb)
	{
		return this.bulb = bulb;
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