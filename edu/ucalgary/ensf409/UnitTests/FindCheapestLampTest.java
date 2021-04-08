/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.4
 * 
 * @since 1.0
 * 
 * Tests the source method of FindCheapestLamp
 */

package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class FindCheapestLampTest 
{

	static final String DBURL = "jdbc:mysql://localhost/inventory";
	static final String USER = "adam";
	static final String PASS = "ENSF409";

  @Test
  // Constructor created with all arguments
  // attempt to source 1 Desk lamp
  public void testSource1DeskLamp()
  {
    FindCheapestLamp test = new FindCheapestLamp(DBURL,USER,PASS,"Desk",1);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceLamp();
	
	//expected returned array
	String[] expected = new String[] {"L013", "L208", "20"};
	
    // See if the arrays are the same
    assertTrue("Did not return cheapest lamp combination", Arrays.equals(returned, expected));
  }
  
	@Test
	//Tests sourcing 2 desk lamps
  public void testSource2DeskLamp()
  {
    FindCheapestLamp test = new FindCheapestLamp(DBURL, USER, PASS,"Desk",2);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceLamp();
	
	//expected returned array
	String[] expected = new String[] {"L013", "L208", "L112", "L342", "40"};
	
    // See if the arrays are the same
    assertTrue("Did not return cheapest lamp combination", Arrays.equals(returned, expected));
  }
  
  @Test
	//Tests sourcing 3 desk lamps
  public void testSource3DeskLamp()
  {
    FindCheapestLamp test = new FindCheapestLamp(DBURL, USER, PASS,"Desk",3);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceLamp();

	//expected returned array
	String[] expected = new String[] {"L013", "L208", "L112", "L342", "L564", "60"};
	
    // See if the arrays are the same
    assertTrue("Did not return cheapest lamp combination", Arrays.equals(returned, expected));
  }
  
    @Test
	//Tests sourcing 4 desk lamps, expecting failure
  public void testSource4DeskLamp()
  {
    FindCheapestLamp test = new FindCheapestLamp(DBURL, USER, PASS,"Desk",4);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceLamp();
	
	//expected returned array
	String[] expected = new String[] {"002", "004", "005", "-1"};
	
    // See if the arrays are the same
    assertTrue("Did not return cheapest lamp combination", Arrays.equals(returned, expected));
  }
  
   @Test
	//Tests sourcing 2 Study lamps
  public void testSource2StudyLamp()
  {
    FindCheapestLamp test = new FindCheapestLamp(DBURL, USER, PASS,"Study",2);
	
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceLamp();
	

	//expected returned array
	String[] expected = new String[] {"L928", "L982", "L223", "20"};
	
    // See if the arrays are the same
    assertTrue("Did not return cheapest lamp combination", Arrays.equals(returned, expected));
  }
  
  
  
}

  