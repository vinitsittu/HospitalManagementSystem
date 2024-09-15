package Hospital;

import java.sql.*;
import java.util.Scanner;

public class Appointment {
    public Connection connection;
    public Scanner scanner;

    public Appointment(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add a new appointment
    public void addAppointment() {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String date = scanner.next();

        try {
            String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, date);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment Added Successfully!!");
            } else {
                System.out.println("Failed to add Appointment!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all appointments
    public void viewAppointments() {
        String query = "SELECT * FROM appointments";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Appointments: ");
            System.out.println("+---------------+------------+------------+-----------------+");
            System.out.println("| Appointment ID| Patient ID | Doctor ID  | Appointment Date |");
            System.out.println("+---------------+------------+------------+-----------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");
                String date = resultSet.getString("appointment_date");
                System.out.printf("| %-13s | %-10s | %-10s | %-15s |\n", id, patientId, doctorId, date);
                System.out.println("+---------------+------------+------------+-----------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an appointment
    public void updateAppointment() {
        System.out.print("Enter Appointment ID to update: ");
        int id = scanner.nextInt();

        if (!getAppointmentById(id)) {
            System.out.println("Appointment with ID " + id + " does not exist.");
            return;
        }

        System.out.print("Enter new Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter new Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
        String date = scanner.next();

        try {
            String query = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment Updated Successfully!!");
            } else {
                System.out.println("Failed to update Appointment!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an appointment
    public void deleteAppointment() {
        System.out.print("Enter Appointment ID to delete: ");
        int id = scanner.nextInt();

        if (!getAppointmentById(id)) {
            System.out.println("Appointment with ID " + id + " does not exist.");
            return;
        }

        String query = "DELETE FROM appointments WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Appointment!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get appointment by ID
    public boolean getAppointmentById(int id) {
        String query = "SELECT * FROM appointments WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete all appointments
    public void deleteAllAppointments() {
        System.out.print("Are you sure you want to delete all appointments? (yes/no): ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        String query = "DELETE FROM appointments";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("All Appointments Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Appointments!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Scanner getScanner() {
        return scanner;
    }
}
