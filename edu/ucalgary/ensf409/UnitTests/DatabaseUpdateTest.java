/**
 * @author Braden Foley <a href="mailto:braden.foley@ucalgary.ca">
 *         braden.foley@ucalgary.ca</a>
 * 
 * @version 1.4
 * 
 * @since 1.0
 * 
 * This program will test to make sure that our DataBaseUpdate properly removes the items ordered from inventory
 * Group 13: Adam Pezoulas, Braden Foley, Rober Dewar, Muhammad Shakeel
 */
package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import edu.ucalgary.ensf409.DataBaseUpdate;

public class DatabaseUpdateTest {

    static final String DBURL = "jdbc:mysql://localhost/inventory";
    static final String USER = "scm";
    static final String PASS = "ensf409";

    @Test
    /**
     * This will test to see if we actually delete an ordered mesh chair
     * Constructor with 3 arguments 
     * deleteItem() with one argument
     */
    public void testRemove1MeshChair() {
        // creating new DataBaseUpdate object
        DataBaseUpdate test = new DataBaseUpdate(DBURL, USER, PASS);
        // String arrray with the ID of the item we want to delete
        String[] ids = { "C9890" };
        test.deleteItem("chair", ids);
        // checking to see if the mesh chair was actually deleted
        assertFalse("The item still exists in the database", searchDatabase(ids, "chair"));

    }

    @Test
    /**
     * This test trys to delete 2 items of the same type from the database
     * Constructor with 3 arguments 
     * deleteItem() with one argument
     */
    public void testRemove2TaskChairs() {
        // creating new DataBaseUpdate object
        DataBaseUpdate test = new DataBaseUpdate(DBURL, USER, PASS);
        // String arrray with the IDs of the items we want to delete
        String[] ids = { "C0914", "C1148" };
        test.deleteItem("chair", ids);
        // checking to see if both items were properly deleted
        assertFalse("One or both of the items are still in the database", searchDatabase(ids, "chair"));
    }

    @Test
    /**
     * This test trys to remove 2 items of differing types, i.e desk and study lamps
     * Constructor with 3 arguments deleteItem() with one argument
     */
    public void testRemoveDeskLampStudyLamp() {
        // creating new DataBaseUpdate object
        DataBaseUpdate test = new DataBaseUpdate(DBURL, USER, PASS);
        // String arrray with the IDs of the items we want to delete
        String[] ids = { "L112", "L223" };
        test.deleteItem("lamp", ids);
        // checking to see if both items were properly deleted
        assertFalse("One or both of the items are still in the database", searchDatabase(ids, "lamp"));
    }

    /**
     * This method compares the passed in ID array to the IDs in the database for a
     * specified category and returns true if there is a matching ID
     * 
     * @param ID     an array of IDs that we want to see if they are in the database
     * @param object the category we will search for the ID in
     * @return returns true if a match is found, otherwise false
     */
    public boolean searchDatabase(String[] ID, String object) {
        try { // establishing connection to database
            Connection dbConnection = DriverManager.getConnection(DBURL, USER, PASS);
            try {
                ArrayList<String> databaseIds = new ArrayList<>();
                // getting our statement ready
                Statement myStmt = dbConnection.createStatement();
                // checking which category we are looking at
                if (object.equals("chair")) {
                    ResultSet results = myStmt.executeQuery("SELECT * FROM chair");
                    // storing the IDs on the database in a array list for easy of adding an unknown
                    // amount of elements
                    while (results.next()) {
                        databaseIds.add(results.getString("ID"));
                    }
                    // will loops through the passed in ID array and check to see if any of them
                    // match the IDs in database
                    for (int i = 0; i < ID.length; i++) {
                        for (int j = 0; j < databaseIds.size(); j++) {
                            if (ID[i].equals(databaseIds.get(j))) {
                                myStmt.close();
                                dbConnection.close();
                                return true;
                            }
                        }
                    }

                } else if (object.equals("desk")) {
                    ResultSet results = myStmt.executeQuery("SELECT * FROM desk");
                    // storing the IDs on the database in a array list for easy of adding an unknown
                    // amount of elements
                    while (results.next()) {
                        databaseIds.add(results.getString("ID"));
                    }
                    // will loops through the passed in ID array and check to see if any of them
                    // match the IDs in database
                    for (int i = 0; i < ID.length; i++) {
                        for (int j = 0; j < databaseIds.size(); j++) {
                            if (ID[i].equals(databaseIds.get(j))) {
                                myStmt.close();
                                dbConnection.close();
                                return true;
                            }
                        }
                    }
                } else if (object.equals("lamp")) {
                    ResultSet results = myStmt.executeQuery("SELECT * FROM lamp");
                    // storing the IDs on the database in a array list for easy of adding an unknown
                    // amount of elements
                    while (results.next()) {
                        databaseIds.add(results.getString("ID"));
                    }
                    // will loops through the passed in ID array and check to see if any of them
                    // match the IDs in database
                    for (int i = 0; i < ID.length; i++) {
                        for (int j = 0; j < databaseIds.size(); j++) {
                            if (ID[i].equals(databaseIds.get(j))) {
                                myStmt.close();
                                dbConnection.close();
                                return true;
                            }
                        }
                    }
                } else {
                    ResultSet results = myStmt.executeQuery("SELECT * FROM filing");
                    // storing the IDs on the database in a array list for easy of adding an unknown
                    // amount of elements
                    while (results.next()) {
                        databaseIds.add(results.getString("ID"));
                    }
                    // will loops through the passed in ID array and check to see if any of them
                    // match the IDs in database
                    for (int i = 0; i < ID.length; i++) {
                        for (int j = 0; j < databaseIds.size(); j++) {
                            if (ID[i].equals(databaseIds.get(j))) {
                                myStmt.close();
                                dbConnection.close();
                                return true;
                            }
                        }
                    }
                }
                myStmt.close();
                dbConnection.close();
            } catch (SQLException e) {
                // TODO: handle exception
            }

        } catch (SQLException e) {
            // TODO: handle exception
        }

        return false; // returning false because we could not find a ID match
    }
}
