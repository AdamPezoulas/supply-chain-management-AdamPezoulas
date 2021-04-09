/**
 * @author Braden Foley <a href="mailto:braden.foley@ucalgary.ca">
 *         braden.foley@ucalgary.ca</a>
 * 
 * @version 1.2
 * 
 * @since 1.0
 * 
 * This program will test to make sure that our DataBaseUpdate properly removes the items ordered from inventory
 * 
 */
package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class DatabaseUpdateTest {

    static final String DBURL = "jdbc:mysql://localhost/inventory";
    static final String USER = "Braden";
    static final String PASS = "ensf409";

    @Test
    /**
     * This will test to see if we actually delete an ordered mesh chair
     */
    public void testRemove1MeshChair() {
        // creating new DataBaseUpdate object
        DataBaseUpdate test = new DataBaseUpdate(DBURL, USER, PASS);

        String[] ids = { "C9890" };
        test.deleteItem("chair", ids);
        // checking to see if the mesh chair was actually deleted
        assertFalse("The item still exists in the database", searchDatabase(ids, "chair"));

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
        try {
            Connection dbConnection = DriverManager.getConnection(DBURL, USER, PASS);
            try {
                ArrayList<String> databaseIds = new ArrayList<>();
                // connecting to database
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
