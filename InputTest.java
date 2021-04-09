/**
 * @author Robert Dewar <a href="mailto:robert.dewar@ucalgary.ca">
 *         robert.dewar@ucalgary.ca</a>
 * 
 * @version 1.1
 * 
 * @since 1.0
 * 
 * Unit tests for Input.java
 */
 
 package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import  java.io.*;
import  java.util.*;



public class InputTest {
	
	
	 /**
	* Test to ensure that checkInput identifies an incorrect category selection
	*/
	@Test
	public void testIncorrectCategory() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("1", "category select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect category selection when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies an incorrect chair type selection
	*/
	@Test
	public void testIncorrectChair() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("tall", "chair select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect chair type selection when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies an incorrect desk type selection
	*/
	@Test
	public void testIncorrectdesk() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("!!", "desk select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect desk type selection when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies an incorrect filing type selection
	*/
	@Test
	public void testIncorrectfiling() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("very small", "filing select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect filing type selection when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies an incorrect lamp type selection
	*/
	@Test
	public void testIncorrectlamp() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("ujj", "lamp select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect lamp type selection when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies an insufficient quantity input
	*/
	@Test
	public void testInsufficientquantity() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("-4", "quantity select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for a quantity of less than 1 when it is expected to return false", returned == expected);
	 
	}
	/**
	* Test to ensure that checkInput identifies a quantity input of an incorrect type
	*/
	@Test
	public void wrongquantityType() {
		
		//create variable of type Input
		Input test = new Input();
		//call checkInput to get expected return value
		Boolean returned = test.checkInput("aaa", "quantity select");
		 
		Boolean expected = false; 
		//check to verify correct output
		assertTrue("returned true for an incorrect variable type for quantity when it is expected to return false", returned == expected);
	 
	}
}