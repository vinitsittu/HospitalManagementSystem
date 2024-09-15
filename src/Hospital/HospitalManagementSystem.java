package Hospital;
//Provides the Connection interface, which is used to establish a connection to a database.
import java.sql.Connection;
//Contains the DriverManager class, which manages a list of database drivers.
import java.sql.DriverManager;
//Provides the SQLException class, which handles errors and exceptions that occur during database operations.Provides the SQLException class, which handles errors and exceptions that occur during database operations.
import java.sql.SQLException;
//Provides the Scanner class, which is used for reading user input
import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        try{
//            Establishes a connection to a MySQL database.
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Sumit@2659");
            Scanner scanner = new Scanner(System.in);
//            Creates an instance of the Doctor class.
//            This object allows you to perform operations related to doctors (such as adding, updating, viewing, and deleting doctors).
//            same for the below two
            Doctor doctor = new Doctor(connection, scanner);
            Patient patient = new Patient(connection, scanner);
            Appointment appointment = new Appointment(connection, scanner);

            while (true) {
//                It can be called because it is a static method
                showMainMenu();
                int mainChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (mainChoice) {
                    case 1 -> showDoctorMenu(doctor);
                    case 2 -> showPatientMenu(patient);
                    case 3 -> showAppointmentMenu(appointment);
                    case 4 -> {
                        System.out.println("Exiting...");
                        connection.close();
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show main menu
    public static void showMainMenu() {
        System.out.println("Hospital Management System");
        System.out.println("1. Manage Doctors");
        System.out.println("2. Manage Patients");
        System.out.println("3. Manage Appointments");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // Doctor management menu
    public static void showDoctorMenu(Doctor doctor) {
        Scanner scanner = doctor.getScanner();
        while (true) {
            System.out.println("Doctor Management:");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Delete All Doctors");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> doctor.addDoctor();
                case 2 -> doctor.viewDoctors();
                case 3 -> doctor.updateDoctor();
                case 4 -> doctor.deleteDoctor();
                case 5 -> doctor.deleteAllDoctors();
                case 6 -> {
                    return; // Go back to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Patient management menu
    public static void showPatientMenu(Patient patient) {
        Scanner scanner = patient.getScanner();
        while (true) {
            System.out.println("Patient Management:");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Delete All Patients");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> patient.addPatient();
                case 2 -> patient.viewPatients();
                case 3 -> patient.updatePatient();
                case 4 -> patient.deletePatient();
                case 5 -> patient.deleteAllPatients();
                case 6 -> {
                    return; // Go back to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Appointment management menu
    public static void showAppointmentMenu(Appointment appointment) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Appointment Management:");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Delete All Appointments");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> appointment.addAppointment();
                case 2 -> appointment.viewAppointments();
                case 3 -> appointment.updateAppointment();
                case 4 -> appointment.deleteAppointment();
                case 5 -> appointment.deleteAllAppointments();
                case 6 -> {
                    return; // Go back to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
