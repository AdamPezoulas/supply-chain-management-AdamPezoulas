/**
 * @author Braden Foley <a href="mailto:braden.foley@ucalgary.ca">
 *         braden.foley@ucalgary.ca</a>
 * 
 * @version 1.5
 * 
 * @since 1.0
 * 
 * This program gets data from findCheapest program in order to give the user a formated summary of their request.
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {
    private String type;
    private int quantity;
    private String[] ID;
    private String filePath;
    private int totalPrice;

    /**
     * Default constructor
     */
    public FileWriting() {
        this.type = null;
        this.quantity = -1;
        this.ID = null;
        this.filePath = null;
        this.totalPrice = -1;
    }

    /**
     * This constructor will intialize all our class variables
     * 
     * @param type       this is a string formatted as the object type, followed by
     *                   the object
     * @param quantity   the number of items requested by user
     * @param ID         a string of IDs, either the objects used or the
     *                   manufactures that produce the object
     * @param filePath   where you wanna save the output file to
     * @param totalPrice price of order
     */
    public FileWriting(String type, int quantity, String[] ID, String filePath, int totalPrice) {
        this.type = type;
        this.quantity = quantity;
        this.ID = ID;
        this.filePath = filePath;
        this.totalPrice = totalPrice;
    }

    /**
     * This method provides a ouput summary to the user in the form of .txt file
     * called "OrderSummary.txt"
     */
    public void writeFile() {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(filePath + "OrderSummary.txt"));
            output.write("Furniture Order Form Summary\n\n"); // Summary Title
            output.write("Faculty Name: \nContact: \nDate: \n\n"); // a fill in
            if (totalPrice > 0) {
                output.write("Items Requested: " + type + ", " + quantity + "\n\n"); // writing the original request
                output.write("Items Ordered\n");
                for (int i = 0; i < ID.length; i++) { // outputting the ID for the inventory that is used
                    output.write("ID: " + ID[i] + "\n");
                }
                output.write("\nTotal Price: $" + totalPrice); // writing the totatl price
            } else if (totalPrice == -1) { // in this case we know we couldn't meet the request requirements
                output.write(
                        "We cannot fulfill your order with the provided requirements based on our current inventory.");
                output.write(" We suggest you try contacting the following manufactures: \n");
                String[] manufactures = idToManufactures(ID);
                for (int i = 0; i < manufactures.length; i++) {
                    output.write(manufactures[i] + "\n");
                }
            } else {
                System.err.println("Somthing went wrong in the totalPrice calculation!");
                System.exit(1); // there was an error computing the total price
            }

            output.close();
        } catch (IOException e) {
            System.err.println("Could not write to file with path: " + filePath);
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * This method works to convert the manufactures ID to the actual companies name
     * so it can be printed out to the summary
     * 
     * @param manuId an array of manufactures id
     * @return an array of the names of the manufactures that correspond to the
     *         given ID number
     */
    public String[] idToManufactures(String[] manuId) {
        String[] temp = new String[manuId.length];
        for (int i = 0; i < manuId.length; i++) {
            if (manuId[i].equals("001")) {
                temp[i] = "Academic Desks";
            } else if (manuId[i].equals("002")) {
                temp[i] = "Office Furnishings";
            } else if (manuId[i].equals("003")) {
                temp[i] = "Chair R Us";
            } else if (manuId[i].equals("004")) {
                temp[i] = "Furniture Goods";
            } else if (manuId[i].equals("005")) {
                temp[i] = "Fine Office Supplies";
            } else {
                System.err.println("This ID does not match an ID in our database");
                System.exit(1); // if the ID in the array is not in the database exit the program
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        String id[] = { "001", "004", "005" };
        FileWriting writer = new FileWriting("test", 12, id, "C:\\ENSF409\\Final_Project\\edu\\ucalgary\\ensf409\\",
                -2);
        writer.writeFile();
    }
}