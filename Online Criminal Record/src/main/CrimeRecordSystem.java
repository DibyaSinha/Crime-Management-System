package main;

import dao.*;
import model.*;
import util.DatabaseConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CrimeRecordSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CriminalDAO criminalDAO = new CriminalDAO();
    private static final ComplaintDAO complaintDAO = new ComplaintDAO();
    private static final UserDAO userDAO = new UserDAO();
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        System.out.println("=== Crime Record Management System ===");

        if (!authenticateUser()) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }

        while (true) {
            showMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: if(isAdmin) addCriminal(); break;
                case 2: fileComplaint(); break;
                case 3: viewPendingComplaints(); break;
                case 4: if (isAdmin) convertToFIR(); break;
                case 5: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static boolean authenticateUser() {
        try {
            while (true) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                if (userDAO.validateUser(username, password)) {
                    isAdmin = userDAO.isAdmin(username);
                    return true;
                }
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    private static void showMenu() {
        System.out.println("\n===== Main Menu =====");
        if(isAdmin) System.out.println("1. Add Criminal Record");
        System.out.println("2. File Complaint");
        System.out.println("3. View Pending Complaints");
        if (isAdmin) System.out.println("4. Convert Complaint to FIR");
        System.out.println("5. Exit");
    }

    private static void addCriminal() {
        try {
            Criminal criminal = new Criminal();
            criminal.setName(getStringInput("Criminal Name: "));
            criminal.setCrimeType(getStringInput("Crime Type: "));
            criminal.setCrimeDate(parseDate("Crime Date (yyyy-MM-dd): "));
            criminal.setPunishmentYears(getIntInput("Punishment Years: "));

            criminalDAO.addCriminal(criminal);
            System.out.println("Criminal added successfully!");
        } catch (Exception e) {
            handleError("Error adding criminal: ", e);
        }
    }

    private static void fileComplaint() {
        try {
            Complaint complaint = new Complaint();
            complaint.setComplainantName(getStringInput("Your Name: "));
            complaint.setDescription(getStringInput("Incident Description: "));
            complaint.setIncidentDate(parseDate("Incident Date (yyyy-MM-dd): "));
            complaint.setPoliceStationId(getIntInput("Police Station ID: "));

            complaintDAO.fileComplaint(complaint);
            System.out.println("Complaint filed successfully!");
        } catch (Exception e) {
            handleError("Error filing complaint: ", e);
        }
    }

    private static void viewPendingComplaints() {
        try {
            List<Complaint> complaints = complaintDAO.getPendingComplaints();
            if (complaints.isEmpty()) {
                System.out.println("No pending complaints.");
                return;
            }

            System.out.println("\n=== Pending Complaints ===");
            for (Complaint c : complaints) {
                System.out.printf("ID: %d | Name: %s | Date: %s%n",
                        c.getId(), c.getComplainantName(), c.getIncidentDate());
            }
        } catch (SQLException e) {
            handleError("Error retrieving complaints: ", e);
        }
    }

    private static void convertToFIR() {
        try {
            int complaintId = getIntInput("Enter Complaint ID to convert: ");
            complaintDAO.convertToFIR(complaintId);
            System.out.println("FIR registered successfully!");
        } catch (SQLException e) {
            handleError("Error converting to FIR: ", e);
        }
    }

    // Helper methods
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static Date parseDate(String prompt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        while (true) {
            try {
                System.out.print(prompt);
                return sdf.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    private static void handleError(String message, Exception e) {
        System.out.println(message + e.getMessage());
        if (e instanceof SQLException) {
            System.out.println("SQL State: " + ((SQLException) e).getSQLState());
        }
    }
}