/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Unit tests for DeskSupply.java, tests findCheapest methods
 */
package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class DeskSupplyTest 
{

	static final String DBURL = "jdbc:mysql://localhost/inventory";
	static final String USER = "adam";
	static final String PASS = "ENSF409";

  @Test
  /**
 * Test to make sure cheapest combination of 1 desk is chosen
 */
  public void testSource1AdjustableDesk()
  {
	  //create filing supply object to test
    DeskSupply test = new DeskSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestDesk("Adjustable", 1);

	String[] expected = new String[] {"D1030","D2746", "400"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Desk combination for 1 adjustable desk", Arrays.equals(returned, expected));
  }

  @Test
    /**
 * Test to make sure cheapest combination of 2 desks is chosen
 */
  public void testSource2AdjustableDesk()
  {
	  //create filing supply object to test
    DeskSupply test = new DeskSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestDesk("Adjustable", 2);

	String[] expected = new String[] {"D1030", "D2746", "D3682", "D7373", "800"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Desk combination for 2 adjustable desks", Arrays.equals(returned, expected));
  }
  
    @Test
    /**
 * Test to make sure cheapest combination of 3 desks is chosen
 */
  public void testSource3AdjustableDesk()
  {
	  //create filing supply object to test
    DeskSupply test = new DeskSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestDesk("Adjustable", 3);

	String[] expected = new String[] {"D1030", "D2746", "D3682", "D7373", "D4475", "D5437", "1200"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Desk combination for 3 adjustable desks", Arrays.equals(returned, expected));
  }
  
    @Test
    /**
 * Test to make sure cheapest combination of 4 desk is chosen
 * Not a possible combination, so expecting ManuIDs returned
 */
  public void testSource4AdjustableDesk()
  {
	  //create filing supply object to test
    DeskSupply test = new DeskSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestDesk("Adjustable", 4);

	
	String[] expected = new String[] {"002", "004", "005","001","-1"};
    // See if the arrays are the same
    assertTrue("Did not return ManuIDs for 4 Adjustable desks", Arrays.equals(returned, expected));
  }
  
    @Test
   /**
 * Test to make sure cheapest combination of 2 standing desks is chosen
 */
  public void testSource2StandingDesk()
  {
	  //create filing supply object to test
    DeskSupply test = new DeskSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestDesk("Standing", 2);

	
	String[] expected = new String[] {"D1927", "D2341", "D3820", "D4438", "600"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Desk combination for 2 standing desks", Arrays.equals(returned, expected));
  }
}

  