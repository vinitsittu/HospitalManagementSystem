package Hospital;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    final private Connection connection;
    final private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add a new patient
    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine(); // Use nextLine() to capture full name with spaces
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character from nextInt()
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.nextLine(); // Use nextLine() to capture gender

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all patients
    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update patient details
    public void updatePatient() {
        System.out.print("Enter Patient ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character from nextInt()

        if (!getPatientById(id)) {
            System.out.println("Patient with ID " + id + " does not exist.");
            return;
        }

        System.out.print("Enter new Patient Name: ");
        String name = scanner.nextLine(); // Use nextLine() to capture full name with spaces
        System.out.print("Enter new Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character from nextInt()
        System.out.print("Enter new Patient Gender: ");
        String gender = scanner.nextLine(); // Use nextLine() to capture gender

        try {
            String query = "UPDATE patients SET name = ?, age = ?, gender = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Updated Successfully!!");
            } else {
                System.out.println("Failed to update Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a patient
    // Delete multiple patients
    // Delete multiple patients
    // Delete multiple patients
    // Check if a patient has appointments
    public boolean hasAppointmentsForPatient(int patientId) {
        String query = "SELECT COUNT(*) FROM appointments WHERE patient_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a patient
    public void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        int id = scanner.nextInt();

        if (!getPatientById(id)) {
            System.out.println("Patient with ID " + id + " does not exist.");
            return;
        }

        if (hasAppointmentsForPatient(id)) {
            System.out.println("Cannot delete this patient because they have appointments scheduled. Please delete the appointments first.");
            return;
        }

        String query = "DELETE FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete all patients
    public void deleteAllPatients() {
        System.out.print("Are you sure you want to delete all patients? (yes/no): ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        // Check for any associated appointments
        String checkQuery = "SELECT COUNT(*) FROM appointments WHERE patient_id IS NOT NULL";
        try {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Cannot delete all patients because some have appointments scheduled. Please delete the appointments first.");
                return;
            }

            String deleteQuery = "DELETE FROM patients";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            int affectedRows = deleteStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("All Patients Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Patients!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Get patient by ID
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
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

    public Scanner getScanner() {
        return scanner;
    }
}
