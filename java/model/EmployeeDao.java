package model;

import beans.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * The EmployeeDao interface defines methods for interacting with employee data
 * in the database. Implementations of this interface provide functionality to
 * perform CRUD (Create, Read, Update, Delete) operations on employee records,
 * as well as methods for authentication and querying based on roles and team
 * membership.
 */
public interface EmployeeDao {

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param employeeId The ID of the employee to retrieve.
	 * @return The employee with the specified ID, or null if not found.
	 */
	Employee getById(int employeeId);

	/**
	 * Retrieves all employees from the database.
	 * 
	 * @return A list containing all employees.
	 */
	List<Employee> getAll();

	/**
	 * Adds a new employee to the database.
	 * 
	 * @param employee The employee to add.
	 */
	void add(Employee employee);

	/**
	 * Updates an existing employee in the database.
	 * 
	 * @param employee The updated employee information.
	 */
	void update(Employee employee);

	/**
	 * Deletes an employee from the database.
	 * 
	 * @param employeeId The ID of the employee to delete.
	 */
	void delete(int employeeId);

	/**
	 * Checks login credentials by verifying email and password.
	 * 
	 * @param email    The email of the employee.
	 * @param password The password of the employee.
	 * @return The employee if login is successful, null otherwise.
	 */
	Employee checkLogin(String email, String password);

	/**
	 * Retrieves employees by role name within a specific team.
	 * 
	 * @param roleName The name of the role.
	 * @param teamId   The ID of the team.
	 * @return A list of employees with the specified role in the given team.
	 * @throws SQLException If an SQL exception occurs.
	 */
	List<Employee> getByRoleNameOfTeam(String roleName, int teamId) throws SQLException;

	/**
	 * Retrieves employees by team ID.
	 * 
	 * @param teamId The ID of the team.
	 * @return A list of employees in the specified team.
	 * @throws SQLException If an SQL exception occurs.
	 */
	List<Employee> getByfTeam(int teamId) throws SQLException;

	/**
	 * Retrieves employees by role names.
	 * 
	 * @param roleNames The names of the roles.
	 * @return A list of employees with the specified roles.
	 * @throws SQLException If an SQL exception occurs.
	 */
	List<Employee> getByRoleNames(String roleNames) throws SQLException;
}
