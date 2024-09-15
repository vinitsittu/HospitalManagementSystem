package Hospital;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
    final private Connection connection;
    final private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner =scanner;
    }

    // View all doctors
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            executeQuery(): Used for executing SELECT queries and returns a ResultSet object containing the results.
//            ResultSet: Represents the result set of the query and provides methods to access and process the data.
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
//            Iteration: while (resultSet.next()) iterates through each row of the ResultSet,
//            retrieving a6nd printing the id, name, and specialization of each doctor.
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new doctor
    public void addDoctor() {
        System.out.print("Enter Doctor Id: ");
        int Id = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character from nextInt()

        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine(); // Use nextLine() to capture the full name

        System.out.print("Enter Doctor Specialization: ");
        String specialization = scanner.nextLine();

        try {
            String query = "INSERT INTO doctors(Id, name, specialization) VALUES(?, ?, ?)";
//            The PreparedStatement object allows you to execute parameterized SQL queries efficiently and securely.
//            It helps prevent SQL injection attacks by separating SQL code from data and improves performance by reusing the compiled query plan.
//            The method connection.prepareStatement(query) creates a PreparedStatement object that can be used to set parameters and execute the query.
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialization);
//            public int executeUpdate()	executes the query. It is used for create, drop, insert, update, delete etc.
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Added Successfully!!");
            } else {
                System.out.println("Failed to add Doctor!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Update doctor details
    public void updateDoctor() {
        System.out.print("Enter Doctor Id to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character from nextInt()

        System.out.print("Enter new Doctor Name: ");
        String name = scanner.nextLine(); // Use nextLine() to capture the full name

        System.out.print("Enter new Doctor Specialization: ");
        String specialization = scanner.nextLine();

        try {
            // Update query to set new values for name and specialization
            String query = "UPDATE doctors SET name = ?, specialization = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setInt(3, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Updated Successfully!!");
            } else {
                System.out.println("Failed to update Doctor or Doctor not found!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a doctor
    // Delete multiple doctors
    // Delete multiple doctors
    // Delete multiple doctors
    // Delete multiple doctors
    // Delete a doctor
    // Check if a doctor has appointments
    public boolean hasAppointmentsForDoctor(int doctorId) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
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

    // Delete a doctor
    public void deleteDoctor() {
        System.out.print("Enter Doctor ID to delete: ");
        int id = scanner.nextInt();

        if (!getDoctorById(id)) {
            System.out.println("Doctor with ID " + id + " does not exist.");
            return;
        }

        if (hasAppointmentsForDoctor(id)) {
            System.out.println("Cannot delete this doctor because they have appointments scheduled. Please delete the appointments first.");
            return;
        }

        String query = "DELETE FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Doctor!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Delete all doctors
    public void deleteAllDoctors() {
        System.out.print("Are you sure you want to delete all doctors? (yes/no): ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        // Check for any associated appointments
        String checkQuery = "SELECT COUNT(*) FROM appointments WHERE doctor_id IS NOT NULL";
        try {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Cannot delete all doctors because some have appointments scheduled. Please delete the appointments first.");
                return;
            }

            String deleteQuery = "DELETE FROM doctors";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            int affectedRows = deleteStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("All Doctors Deleted Successfully!!");
            } else {
                System.out.println("Failed to delete Doctors!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get doctor by ID
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
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
