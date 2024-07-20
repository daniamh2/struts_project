package model;

import java.util.List;

import beans.Employee;
import beans.EmployeeTask;
import beans.Task;

/**
 * The EmployeeTaskDao interface provides methods for managing the assignment of
 * tasks to employees. Implementations of this interface handle operations such
 * as assigning tasks to employees, retrieving employees assigned to a task,
 * retrieving tasks assigned to an employee, approving tasks, and more.
 */
public interface EmployeeTaskDao {

	/**
	 * Assigns a task to an employee.
	 * 
	 * @param employeeId The ID of the employee to whom the task is assigned.
	 * @param taskId     The ID of the task to be assigned.
	 */
	void assignTask(int employeeId, int taskId);

	/**
	 * Retrieves a list of employee IDs assigned to a specific task.
	 * 
	 * @param taskId The ID of the task.
	 * @return A list of employee IDs assigned to the task.
	 */
	List<Employee> getEmployeesForTask(int taskId);

	/**
	 * Retrieves a list of task IDs assigned to a specific employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return A list of task IDs assigned to the employee.
	 */
	List<Integer> getTasksForEmployee(int employeeId);

	/**
	 * Retrieves a list of task IDs for pending tasks assigned to a specific
	 * employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return A list of task IDs for pending tasks assigned to the employee.
	 */
	List<Integer> getPendingTasksForEmployee(int employeeId);

	/**
	 * Approves a task by updating its status.
	 * 
	 * @param taskId         The ID of the task to be approved.
	 * @param approverRoleId The role ID of the employee approving the task.
	 */
	void approveTask(int taskId, int approverRoleId);

	/**
	 * Retrieves a list of assignments for a specific employee and task.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param taskId     The ID of the task.
	 * @return A list of EmployeeTask objects representing assignments.
	 */
	List<EmployeeTask> getAssign(int employeeId, int taskId);

	/**
	 * Retrieves a list of pending task IDs for a specific employee and team.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param teamId     The ID of the team.
	 * @param manager    A flag indicating whether the employee is a manager.
	 * @return A list of pending task IDs for the employee and team.
	 */
	List<Integer> getPendingTasks(int employeeId, int teamId, boolean manager);

	/**
	 * Deletes an assignment of a task from an employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param taskId     The ID of the task to be unassigned.
	 */
	void delete(int employeeId, int taskId);

	List<Task> getTsksForTeams(int taskId);

	/**
	 * Retrieves a list of pending task IDs for a specific employee and team.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param teamId     The ID of the team.
	 * @param manager    A flag indicating whether the employee is a manager.
	 * @return A list of pending task IDs for the employee and team.
	 */
	List<Integer> getRjectedTasks(int employeeId, int teamId, boolean manager);
}
