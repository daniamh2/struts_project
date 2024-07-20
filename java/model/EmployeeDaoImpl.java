package model;

import beans.Employee;
import java.sql.*;
import dbcon.DbConnection;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

	// Constants for SQL queries
	private static final String SELECT_BY_ID = "SELECT * FROM employees WHERE EMPLOEE_ID = ?";
	private static final String SELECT_ALL = "SELECT * FROM employees";
	// private static final String SELECT_LEADER = "SELECT * FROM employees WHERE
	// ROLE_ID=1";
	// private static final String SELECT_DEVELOPERS = "SELECT * FROM employees
	// WHERE ROLE_ID = 2";
	private static final String SELECT_BY_ROLENAME = "SELECT *" + "FROM employees e "
			+ "JOIN roles r ON e.ROLE_ID = r.ROLE_ID " + "WHERE r.ROLE_NAME = ?";
	private static final String SELECT_BY_ROLENAME_1 = "SELECT *" + "FROM employees e "
			+ "JOIN roles r ON e.ROLE_ID = r.ROLE_ID " + "WHERE r.ROLE_NAME = ? and TEAM_ID=?";

	private static final String SELECT_BY_TEAM = "SELECT *" + "FROM employees e "
			+ "JOIN roles r ON e.ROLE_ID = r.ROLE_ID " + "WHERE  e.TEAM_ID=? ";
	private static final String INSERT = "INSERT INTO employees (ROLE_ID, TEAM_ID, ADDRESS, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE employees SET ROLE_ID=?, TEAM_ID=?, ADDRESS=?, FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=? WHERE EMPLOEE_ID=?";
	private static final String DELETE = "DELETE FROM employees WHERE EMPLOEE_ID=?";
	private static final String CHECK_LOGIN = "SELECT * FROM employees WHERE EMAIL = ? and PASSWORD=?";

	private static final EmployeeDaoImpl INSTANCE = new EmployeeDaoImpl();

	private EmployeeDaoImpl() {
	}

	public static EmployeeDaoImpl getInstance() {
		return INSTANCE;
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param employeeId The ID of the employee to retrieve.
	 * @return The employee with the specified ID, or null if not found.
	 */
	@Override
	public Employee getById(int employeeId) {
		Employee employee = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = DbConnection.getConnection();
			stmt = connection.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, employeeId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				employee = extractEmployeeFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employee;
	}

	/**
	 * Retrieves all employees from the database.
	 * 
	 * @return A list containing all employees.
	 */
	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DbConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				employees.add(extractEmployeeFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employees;
	}

	/**
	 * Adds a new employee to the database.
	 * 
	 * @param employee The employee to add.
	 */
	@Override
	public void add(Employee employee) {
		Connection connection = null;
		PreparedStatement stmt = null;
		System.out.print(employee);
		try {
			connection = DbConnection.getConnection();
			stmt = connection.prepareStatement(INSERT);
			stmt.setInt(1, employee.getRoleId());
			stmt.setInt(2, employee.getTeamId());
			stmt.setString(3, employee.getAddress());
			stmt.setString(4, employee.getFirstName());
			stmt.setString(5, employee.getLastName());
			stmt.setString(6, employee.getEmail());
			stmt.setString(7, employee.getPassword());
			stmt.executeUpdate();
			System.out.print(employee.getFirstName());
		} catch (SQLException e) {
			System.out.print(e);
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Updates an existing employee in the database.
	 * 
	 * @param employee The updated employee information.
	 */
	@Override
	public void update(Employee employee) {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = DbConnection.getConnection();
			stmt = connection.prepareStatement(UPDATE);
			stmt.setInt(1, employee.getRoleId());
			if ((Integer) employee.getTeamId() != null) {
				stmt.setInt(2, employee.getTeamId());
			} else {
				stmt.setNull(2, java.sql.Types.INTEGER); // Set the parameter to NULL in the database
			}
			stmt.setString(3, employee.getAddress());
			stmt.setString(4, employee.getFirstName());
			stmt.setString(5, employee.getLastName());
			stmt.setString(6, employee.getEmail());
			stmt.setString(7, employee.getPassword());
			stmt.setInt(8, employee.getEmployeeId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @param employeeId The ID of the employee to delete.
	 */
	@Override
	public void delete(int employeeId) {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = DbConnection.getConnection();
			stmt = connection.prepareStatement(DELETE);
			stmt.setInt(1, employeeId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Checks login credentials by verifying email and password.
	 * 
	 * @param email    The email of the employee.
	 * @param password The password of the employee.
	 * @return The employee if login is successful, null otherwise.
	 */
	@Override
	public Employee checkLogin(String email, String password) {
		Employee employee = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = DbConnection.getConnection();
			stmt = connection.prepareStatement(CHECK_LOGIN);
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				employee = extractEmployeeFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);

		}
		return employee;
	}

	/**
	 * Retrieves employees by role names.
	 * 
	 * @param roleNames The names of the roles.
	 * @return A list of employees with the specified roles.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public List<Employee> getByRoleNames(String roleNames) throws SQLException {
		List<Employee> employees = new ArrayList<>();
		Connection connection = null;
		try {
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			connection = DbConnection.getConnection();
			statement = connection.prepareStatement(SELECT_BY_ROLENAME);
			statement.setString(1, roleNames);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				employees.add(extractEmployeeFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employees;
	}

	/**
	 * Retrieves employees by team ID.
	 * 
	 * @param teamId The ID of the team.
	 * @return A list of employees in the specified team.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public List<Employee> getByfTeam(int teamId) {
		List<Employee> employees = new ArrayList<>();
		Connection connection = null;
		try {
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			connection = DbConnection.getConnection();
			statement = connection.prepareStatement(SELECT_BY_TEAM);
			statement.setInt(1, teamId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				employees.add(extractEmployeeFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employees;
	}

	/**
	 * Extracts an employee object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The employee object extracted from the ResultSet.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(rs.getInt("EMPLOEE_ID"));
		employee.setRoleId(rs.getInt("ROLE_ID"));
		employee.setTeamId(rs.getInt("TEAM_ID"));
		employee.setAddress(rs.getString("ADDRESS"));
		employee.setFirstName(rs.getString("FIRST_NAME"));
		employee.setLastName(rs.getString("LAST_NAME"));
		employee.setEmail(rs.getString("EMAIL"));
		employee.setPassword(rs.getString("PASSWORD"));
		return employee;
	}

	/**
	 * Retrieves employees by role name within a specific team.
	 * 
	 * @param roleName The name of the role.
	 * @param teamId   The ID of the team.
	 * @return A list of employees with the specified role in the given team.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public List<Employee> getByRoleNameOfTeam(String roleName, int teamId) throws SQLException {
		List<Employee> employees = new ArrayList<>();
		Connection connection = null;
		try {
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			connection = DbConnection.getConnection();
			statement = connection.prepareStatement(SELECT_BY_ROLENAME_1);
			statement.setString(1, roleName);
			statement.setInt(2, teamId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				employees.add(extractEmployeeFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employees;
	}

}