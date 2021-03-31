/**
 * This program updates the database based on what the user has successfully
 * ordered
 */
package edu.ucalgary.ensf409;

import java.sql.*;


/**
 * Do we need to have the ids and object as class variables? or should we have
 * them passed into the method deleteItem?
 */

public class DataBaseUpdate {
    private String object;
    private String[] ids;
    public final String DBURL; // store the database url information
    public final String USERNAME; // store the user's account username
    public final String PASSWORD; // store the user's account password

    /**
     * Default constructor
     */
    public DataBaseUpdate() {
        this.object = null;
        this.ids = null;
        this.DBURL = null;
        this.USERNAME = null;
        this.PASSWORD = null;
    }

    /**
     * Constructor initializes all the class variables
     * 
     * @param object   the object type so we know what table to look into
     * @param ids      a array of the different objects that were ordered
     * @param dburl
     * @param username
     * @param password
     */
    public DataBaseUpdate(String object, String[] ids, String dburl, String username, String password) {
        this.ids = ids;
        this.object = object;
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /**
     * This function will remove the items bought by the user from the database
     */
    public void deleteItem() {
        try {
            //trying to establish a connection to the database
            Connection connectToDatabase = DriverManager.getConnection(getDBURL(), getUSERNAME(), getPASSWORD());
            for (int i = 0; i < ids.length; i++) {
                String tempItem = ids[i];   //sorting the id at index i in the array
                try {
                    String query = "DELETE FROM ? WHERE ID = ?";    //preparing the general SQL command
                    PreparedStatement statement = connectToDatabase.prepareStatement(query);
                    //setting the ? to the right parameters?
                    statement.setString(1, object);
                    statement.setString(2, tempItem);

                    statement.executeUpdate();
                    statement.close();
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @return the class variable DBURL to the caller
     */
    public String getDBURL() {
        return this.DBURL;
    }

    /**
     * 
     * @return the class variavle PASSWORD to the caller
     */
    public String getPASSWORD() {
        return this.PASSWORD;
    }

    /**
     * 
     * @return the class variavle USERNAME to the caller
     */
    public String getUSERNAME() {
        return USERNAME;
    }
}
