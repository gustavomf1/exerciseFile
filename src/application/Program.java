package application;

import model.entities.Information;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Information> infoList = new ArrayList<>();

        // Prompt the user to enter the file path
        System.out.println("Enter the path: ");
        String strPath = sc.nextLine();

        // Try to read the file specified by the user
        try (BufferedReader br = new BufferedReader(new FileReader(strPath))) {
            String line = br.readLine();

            // Loop through each line in the file
            while (line != null) {
                String[] data = line.split(",");

                // Check if the line has at least three fields
                if (data.length >= 3) {
                    String name = data[0].trim(); // Get the first field and trim whitespace
                    Double price = Double.parseDouble(data[1].trim()); // Convert the second field to a Double
                    Integer quantity = Integer.parseInt(data[2].trim()); // Convert the third field to an Integer

                    // Create an Information object and add it to the list
                    Information info = new Information(name, price, quantity);
                    infoList.add(info);

                    // Create or update the CSV file with the data
                    createNewFile(name, price, quantity);
                }
                line = br.readLine(); // Read the next line
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an I/O error occurs
        }

        sc.close(); // Close the scanner
    }

    public static boolean createNewFile(String name, Double price, Integer quantity) {
        String folderName = "out";
        String fileName = "summary.csv";
        String directoryLink = "C:\\Users\\Gustavo\\Documents\\Java\\exerciseFile\\Documents";

        // Calculate the new price based on the quantity!
        Double newPrice = price * quantity;

        // Create the "out" directory if it doesn't exist
        File directory = new File(directoryLink, folderName);
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                System.out.println("Error creating directory: " + directory.getAbsolutePath());
                return false; // Return false if the directory could not be created
            }
        }

        // Create the "summary.csv" file in the "out" directory
        File file = new File(directory, fileName);
        try (FileWriter fw = new FileWriter(file, true); // true to append to the file if it already exists
             PrintWriter pw = new PrintWriter(fw)) {

            // Write the data to the file
            pw.println(name + "," + newPrice);

            System.out.println("File successfully created at: " + file.getAbsolutePath());
            return true; // Return true if the file was successfully created

        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an I/O error occurs
            return false; // Return false if an error occurs
        }
    }
}