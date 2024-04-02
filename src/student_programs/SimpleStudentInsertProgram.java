package student_programs;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

import data_access.ConnectionParameters;
import data_access.DbUtils;

/**
 * A minimal example of executing an SQL INSERT statement
 * 
 * @author Kari
 * @version 1.1 3.11.2019
 */
public class SimpleStudentInsertProgram {
	
	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Scanner scanner = new Scanner(System.in);
		
	
		System.out.println("=== Add student === \n");
		
		System.out.print("Id: ");
		int studentId = scanner.nextInt();
		System.out.print("First name: ");
		String firstName = scanner.next();
		System.out.print("Last name: ");
		String lastName = scanner.next();
		System.out.print("Street: ");
		String streetAddress = scanner.next();
		System.out.print("Postcode: ");
		String postcode = scanner.next();
		System.out.print("Post office: ");
		String postoffice = scanner.next();
		
		try {
			connection = DriverManager.getConnection(ConnectionParameters.connectionString,
					ConnectionParameters.username, ConnectionParameters.password);
							
			String sqlText = "INSERT INTO Student (id, firstname, lastname, streetaddress, postcode, postoffice) VALUES (?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, studentId);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, streetAddress);
			preparedStatement.setString(5, postcode);
			preparedStatement.setString(6, postoffice);
			
			preparedStatement.executeUpdate();

			System.out.println("Student data added!");
			
		} catch (SQLException sqle) {
			// First, check if the problem is primary key violation (the error code is vendor-dependent)
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				System.out.println("Cannot add the student. " + "The student id " + studentId + " is already in use.");
			} else {
				System.out.println("\n[ERROR] Database error. " + sqle.getMessage());
			}
			
		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
	}
}
// End