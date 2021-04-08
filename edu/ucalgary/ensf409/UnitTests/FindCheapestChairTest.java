
/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Tests Find cheapest chair source method
 */


package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class FindCheapestChairTest 
{

	static final String DBURL = "jdbc:mysql://localhost/inventory";
	static final String USER = "adam";
	static final String PASS = "ENSF409";

  @Test
  // Test to source 1 Mesh Chair
  public void testSource1MeshChair()
  {
    FindCheapestChair test = new FindCheapestChair(DBURL,USER,PASS,"Mesh",1);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceChair();
	
	//expected returned array
	String[] expected = new String[] {"C9890", "C8138", "C6748", "200"};

    // See if the arrays are the same
    assertTrue("Did not return cheapest Chair combination", Arrays.equals(returned, expected));
  }
  
    @Test
  // Test to find 2 Mesh chairs, expecting failure
  public void testSource2MeshChair()
  {
    FindCheapestChair test = new FindCheapestChair(DBURL,USER,PASS,"Mesh",2);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceChair();
	
	//expected returned array
	String[] expected = new String[] {"002", "003", "004", "005", "-1"};

    // See if the arrays are the same
    assertTrue("Did not return cheapest Chair combination", Arrays.equals(returned, expected));
  }
  
      @Test
  // Test to find 1 Kneeling chair, expecting failure
  public void testSource1KneelingChair()
  {
    FindCheapestChair test = new FindCheapestChair(DBURL,USER,PASS,"Kneeling",1);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceChair();
	
	//expected returned array
	String[] expected = new String[] {"002", "003", "004", "005", "-1"};

    // See if the arrays are the same
    assertTrue("Did not return cheapest Chair combination", Arrays.equals(returned, expected));
  }
  
        @Test
  // Test to find 1 Executive
  public void testSource1ExecutiveChair()
  {
    FindCheapestChair test = new FindCheapestChair(DBURL,USER,PASS,"Executive",1);

		
	//retrieve cheaepst lamp combination
	String[] returned = test.sourceChair();
	
	//expected returned array
	String[] expected = new String[] {"C7268", "C5784", "C2483", "400"};

    // See if the arrays are the same
    assertTrue("Did not return cheapest Chair combination", Arrays.equals(returned, expected));
  }
  
  
  

  
  
}

  