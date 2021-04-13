/**
 * @author Adam Pezoulas <a href="mailto:adam.pezoulas@ucalgary.ca">
 *         adam.pezoulas@ucalgary.ca</a>
 * 
 * @version 1.4
 * 
 * @since 1.0
 * 
 * Tests the source method of FindCheapestLamp
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class FileWritingTest 
{

	private String location = System.getProperty("user.dir") + "\\";
	private String fileName = "OrderSummary.txt";
  @Test
  /**
 * Test to make sure that a file is created when given Item IDs
 */
  public void testWriteFileBasic() 
  {
    
	String[] testIDs = {"L001", "L002", "L003"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, 5);
	
	test.writeFile();
	
	File theFile = new File(addPath(fileName));
    assertTrue("File " + fileName + " was not created by writeFile() with item IDs", theFile.exists());
	}
	
	@Test
 /**
 * Test to make sure file is created when given ManuIDs
 */
  public void testWriteFileManuIDs() 
  {
    
	String[] testIDs = {"001", "002", "003"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, -1);
	
	test.writeFile();
	
	File theFile = new File(addPath(fileName));
    assertTrue("File " + fileName + " was not created by writeFile() with ManuIDs", theFile.exists());
	}
	
	@Test
  /**
 * Test to make sure correct message is written when given Item IDs
 */
  public void testItemsWritten() 
  {
    
	String[] testIDs = {"L002", "L432", "L413"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, 20);
	
	test.writeFile();
	
	String expected = "Items";
    assertTrue("Item IDs were not written", test.getTypeWritten().equals(expected));
	}
	
		@Test
  /**
 * Test to make sure correct message is written when given ManuIDs
 */
  public void testManuWritten() 
  {
    
	String[] testIDs = {"002", "005", "005"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, -1);
	
	test.writeFile();
	
	String expected = "Manufacturers";
    assertTrue("Item IDs were not written", test.getTypeWritten().equals(expected));
	}
	
	
	public String addPath(String file) 
	{
		File path = new File(location);
		File full = new File(path, file);
		return full.getPath();
	}
}