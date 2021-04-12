/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Unit tests for FilingSupply.java, tests findCheapest methods
 */
package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class FilingSupplyTest 
{

	static final String DBURL = "jdbc:mysql://localhost/inventory";
	static final String USER = "scm";
	static final String PASS = "ensf409";

  @Test
  /**
 * Test to make sure cheapest combination of 1 small filing is chosen
 */
  public void testSource1SmallFiling()
  {
	  //create filing supply object to test
    FilingSupply test = new FilingSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestFiling("Small", 1);

	
	String[] expected = new String[] {"F001","F013", "100"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Filing combination for 1 small filing", Arrays.equals(returned, expected));
  }
  
    @Test
  /**
 * Test to make sure cheapest combination of 2 small filing is chosen
 */
  public void testSource2SmallFiling()
  {
	  //create filing supply object to test
    FilingSupply test = new FilingSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestFiling("Small", 2);

	
	String[] expected = new String[] {"F001", "F013","F004", "F006", "225"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Filing combination for 2 small filing", Arrays.equals(returned, expected));
  }

  @Test
  /**
 * Test to make sure cheapest combination of 3 small filing
 *Not possible, so expecting ManuIDs
 */
  public void testSource3SmallFiling()
  {
	  //create filing supply object to test
    FilingSupply test = new FilingSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestFiling("Small", 3);
	
	String[] expected = new String[] {"002", "005", "004", "-1"};
    // See if the arrays are the same
    assertTrue("Did not return ManuIDs for 3 small filing", Arrays.equals(returned, expected));
  }
  
    @Test
  /**
 * Test to make sure cheapest combination of 2 Large filing is chosen
 */
  public void testSource2LargeFiling()
  {
	  //create filing supply object to test
    FilingSupply test = new FilingSupply(DBURL, USER, PASS);

	//initilialize DB connection
	test.initializeConnection();

	//retrieve array of cheapest options
	String[] returned = test.cheapestFiling("Large", 2);


	
	String[] expected = new String[] {"F010","F012", "F011", "F015", "600"};
    // See if the arrays are the same
    assertTrue("Did not return cheapest Filing combination for 2 large filing", Arrays.equals(returned, expected));
  }



  
}

  