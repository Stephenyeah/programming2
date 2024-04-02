package student_programs;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

import data_access.ConnectionParameters;
import data_access.DbUtils;

/**
 * Deleting a student by ID
 * 
 * @author Kari
 * @version 1.0
 */
public class SimpleStudentDeleteProgram {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Delete Student by ID ===");
        System.out.print("Enter student id: ");
        int studentIdToDelete = scanner.nextInt();

        try {
            // 1. Open a database connection
            connection = DriverManager.getConnection(ConnectionParameters.connectionString,
                    ConnectionParameters.username, ConnectionParameters.password);

            // 2. Define the SQL query to delete a student by ID
            String sqlText = "DELETE FROM Student WHERE id = ?";

            // 3. Create a prepared statement based on the SQL query text
            preparedStatement = connection.prepareStatement(sqlText);

            // 4. Set the query parameter value(s) based on the place-holder number(s)
            preparedStatement.setInt(1, studentIdToDelete);

            // 5. Execute the SQL query with the PreparedStatement object
            int rowsAffected = preparedStatement.executeUpdate();

            // 6. Check if any rows were affected (i.e., if the student was deleted)
            if (rowsAffected > 0) {
                System.out.println("Student deleted!");
            } else {
                System.out.println("Nothing deleted. Unknown student id (" + studentIdToDelete + ").");
            }

        } catch (SQLException sqle) {
            // If any JDBC operation fails, display an error message
            System.out.println("\n[ERROR] Database error: " + sqle.getMessage());
        } finally {
            // 7. Close the resources
            DbUtils.closeQuietly(preparedStatement, connection);
            scanner.close();
        }
    }
}
