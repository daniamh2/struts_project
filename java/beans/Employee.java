package beans;

import java.io.Serializable;

/**
 * Represents an employee entity with various attributes such as employee ID,
 * role ID, team ID, address, first name, last name, email, and password.
 */
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int employeeId;
	private int roleId;
	private Integer teamId; // Identifier for the team to which the employee belongs (nullable)
	private String address;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	// Getters and Setters

	/**
	 * Retrieves the employee ID.
	 * 
	 * @return The employee ID.
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * Sets the employee ID.
	 * 
	 * @param employeeId The employee ID to set.
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Retrieves the role ID.
	 * 
	 * @return The role ID.
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role ID.
	 * 
	 * @param roleId The role ID to set.
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * Retrieves the team ID.
	 * 
	 * @return The team ID.
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * Sets the team ID.
	 * 
	 * @param teamId The team ID to set.
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/**
	 * Retrieves the address.
	 * 
	 * @return The address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Retrieves the first name.
	 * 
	 * @return The first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName The first name to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name.
	 * 
	 * @return The last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName The last name to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Retrieves the email address.
	 * 
	 * @return The email address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address.
	 * 
	 * @param email The email address to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retrieves the password.
	 * 
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
