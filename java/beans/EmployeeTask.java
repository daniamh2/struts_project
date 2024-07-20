package beans;

import java.io.Serializable;

/**
 * Represents an association between an employee and a task.
 */
public class EmployeeTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int employeeId; // The ID of the employee associated with the task
	private int taskId; // The ID of the task associated with the employee

	// Getters and Setters

	/**
	 * Retrieves the ID of the employee associated with the task.
	 * 
	 * @return The employee ID.
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * Sets the ID of the employee associated with the task.
	 * 
	 * @param employeeId The employee ID to set.
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Retrieves the ID of the task associated with the employee.
	 * 
	 * @return The task ID.
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * Sets the ID of the task associated with the employee.
	 * 
	 * @param taskId The task ID to set.
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * Returns a string representation of the EmployeeTask object.
	 * 
	 * @return A string containing the employee ID and task ID.
	 */
	@Override
	public String toString() {
		return "EmployeeTask{" + "employeeId=" + employeeId + ", taskId=" + taskId + '}';
	}
}
