package dbcon;

import java.sql.*;

public class DbConnection {
	// JDBC connection URL
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/company";
	private static final String USERNAME = "dania"; // Database username
	private static final String PASSWORD = "dania"; // Database password

	/**
	 * Establishes a connection to the MySQL database.
	 * 
	 * @return The established database connection.
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish the database connection
			connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// Print stack trace in case of any exceptions
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Closes the database connection.
	 * 
	 * @param connection The database connection to be closed.
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				// Close the database connection
				connection.close();
			} catch (SQLException e) {
				// Print stack trace in case of any exceptions during closing
				System.out.println("Connection didn't close ");
				e.printStackTrace();
			}
		}
	}
}
