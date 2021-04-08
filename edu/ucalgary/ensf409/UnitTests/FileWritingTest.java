package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class FileWritingTest 
{

	private String location = "C:\\Users\\Pezfa\\Desktop\\HYL\\ProjectFork\\supply-chain-management-AdamPezoulas\\";
	private String fileName = "OrderSummary.txt";
  @Test
  // tests basic constructor and makes sure a file is created 
  // when writeFile is ran with item IDs
  public void testWriteFileBasic() 
  {
    
	String[] testIDs = {"L001", "L002", "L003"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, 5);
	
	test.writeFile();
	
	File theFile = new File(addPath(fileName));
    assertTrue("File " + fileName + " was not created by writeFile() with item IDs", theFile.exists());
	}
	
	@Test
  // tests basic constructor and makes sure a file is created 
  // when writeFile is ran with manufacturer IDs
  public void testWriteFileManuIDs() 
  {
    
	String[] testIDs = {"001", "002", "003"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, -1);
	
	test.writeFile();
	
	File theFile = new File(addPath(fileName));
    assertTrue("File " + fileName + " was not created by writeFile() with ManuIDs", theFile.exists());
	}
	
	@Test
  // tests to make sure item ids are written using typeWritten member
  public void testItemsWritten() 
  {
    
	String[] testIDs = {"L002", "L432", "L413"};
	
	FileWriting test = new FileWriting("Lamp", 1, testIDs, location, 20);
	
	test.writeFile();
	
	String expected = "Items";
    assertTrue("Item IDs were not written", test.getTypeWritten().equals(expected));
	}
	
		@Test
  // tests to make sure Manu ids are written using typeWritten member
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