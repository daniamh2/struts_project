package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Employee;
import beans.EmployeeTask;
import beans.Task;
import dbcon.DbConnection;

public class EmployeeTaskDaoImpl implements EmployeeTaskDao {

	// Constants for SQL queries
	private static final String ASSIGN_TASK = "INSERT INTO employees_task (EMPLOYEE_ID, TASK_ID) VALUES (?, ?)";
	private static final String GET_ASSIGN = "SELECT * FROM employees_task et WHERE EMPLOYEE_ID = ? and TASK_ID=?";
	private static final String GET_TASKS_FOR_EMPLOYEE = "SELECT TASK_ID FROM employees_task et WHERE EMPLOYEE_ID = ? ORDER BY et.TASK_ID;";
	private static final String GET_PENDING_TASKS_FOR_EMPLOYEE = "SELECT TASK_ID FROM employees_task et "
			+ "JOIN tasks t ON et.TASK_ID = t.TASK_ID WHERE EMPLOEE_ID = ? AND t.PENDING = 1   ;";
	private static final String GET_PENDING_TASKS = "SELECT *  FROM employees_task et  "
			+ "left JOIN tasks t ON et.TASK_ID = t.TASK_ID  right JOIN employees e ON et.EMPLOYEE_ID = e.EMPLOEE_ID "
			+ " WHERE (t.PENDING != 0 and e.TEAM_ID=? and e.EMPLOEE_ID!=?) ORDER BY et.TASK_ID;\r\n";
	private static final String GET_PENDING_TASKS2 = "SELECT *  FROM employees_task et  "
			+ "left JOIN tasks t ON et.TASK_ID = t.TASK_ID    WHERE (t.PENDING != 0 ) ORDER BY et.TASK_ID;\r\n";
	private static final String APPROVE_TASK = "UPDATE tasks SET PENDING = 0 WHERE TASK_ID = ?";
	private static final EmployeeTaskDao INSTANCE = new EmployeeTaskDaoImpl();

	/**
	 * Returns the singleton instance of the EmployeeTaskDao class.
	 * 
	 * @return The singleton instance.
	 */
	public static EmployeeTaskDao getInstance() {
		return INSTANCE;
	}

	/**
	 * Retrieves a list of employee IDs assigned to a specific task.
	 * 
	 * @param taskId The ID of the task.
	 * @return A list of employee IDs assigned to the task.
	 */
	@Override
	public List<Employee> getEmployeesForTask(int taskId) {
		List<Employee> employeeIds = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(
				"SELECT *  FROM employees e WHERE e.EMPLOEE_ID IN (  SELECT et.EMPLOYEE_ID  FROM employees_task et"
						+ "  WHERE et.TASK_ID = ?);")) {
			stmt.setInt(1, taskId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					employeeIds.add(extractEmployeeFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employeeIds;
	}

	@Override
	public List<Task> getTsksForTeams(int taskId) {
		List<Task> employees = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT t.* FROM tasks t LEFT JOIN employees_task "
				+ "et ON t.TASK_ID = et.TASK_ID RIGHT JOIN employees e ON et.EMPLOYEE_ID = e.EMPLOEE_ID WHERE e.TEAM_ID = ? "
				+ "ORDER BY t.TASK_ID")) {

			stmt.setInt(1, taskId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					employees.add(extractTaskFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return employees;
	}

	/**
	 * Retrieves a list of assignments for a specific employee and task.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param taskId     The ID of the task.
	 * @return A list of EmployeeTask objects representing assignments.
	 */
	@Override
	public List<EmployeeTask> getAssign(int employeeId, int taskId) {
		List<EmployeeTask> assign = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(GET_ASSIGN)) {
			stmt.setInt(1, employeeId);
			stmt.setInt(2, taskId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					EmployeeTask employeeTask = new EmployeeTask();
					employeeTask.setEmployeeId(rs.getInt("employee_id")); // Assuming the column name is "employee_id"
					employeeTask.setTaskId(rs.getInt("task_id")); // Assuming the column name is "task_id"
					assign.add(employeeTask);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return assign;
	}

	/**
	 * Assigns a task to an employee.
	 * 
	 * @param employeeId The ID of the employee to whom the task is assigned.
	 * @param taskId     The ID of the task to be assigned.
	 */
	@Override
	public void assignTask(int employeeId, int taskId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(ASSIGN_TASK)) {
			stmt.setInt(1, employeeId);
			stmt.setInt(2, taskId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes an assignment of a task from an employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param taskId     The ID of the task to be unassigned.
	 */
	@Override
	public void delete(int employeeId, int taskId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM employees_task WHERE EMPLOYEE_ID=? AND TASK_ID=?")) {
			stmt.setInt(1, employeeId);
			stmt.setInt(2, taskId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Retrieves a list of task IDs assigned to a specific employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return A list of task IDs assigned to the employee.
	 */
	@Override
	public List<Integer> getTasksForEmployee(int employeeId) {
		List<Integer> tasks = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(GET_TASKS_FOR_EMPLOYEE)) {
			stmt.setInt(1, employeeId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return tasks;
	}

	/**
	 * Retrieves a list of task IDs for pending tasks assigned to a specific
	 * employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return A list of task IDs for pending tasks assigned to the employee.
	 */
	@Override
	public List<Integer> getPendingTasksForEmployee(int employeeId) {
		List<Integer> tasks = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(GET_PENDING_TASKS_FOR_EMPLOYEE)) {
			stmt.setInt(1, employeeId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return tasks;
	}

	/**
	 * Retrieves a list of pending task IDs for a specific employee and team.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param teamId     The ID of the team.
	 * @param manager    A flag indicating whether the employee is a manager.
	 * @return A list of pending task IDs for the employee and team.
	 */
	@Override
	public List<Integer> getPendingTasks(int employeeId, int teamId, boolean manager) {
		List<Integer> tasks = new ArrayList<>();
		Connection connection = DbConnection.getConnection();

		try {
			ResultSet rs = null;
			if (manager) {
				PreparedStatement stmt = connection.prepareStatement(GET_PENDING_TASKS2);
				rs = stmt.executeQuery();
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			} else {
				PreparedStatement stmt = connection.prepareStatement(GET_PENDING_TASKS);
				stmt.setInt(1, teamId);
				stmt.setInt(2, employeeId);

				rs = stmt.executeQuery();
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return tasks;

	}

	/**
	 * Retrieves a list of Rjected task IDs for a specific employee and team.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param teamId     The ID of the team.
	 * @param manager    A flag indicating whether the employee is a manager.
	 * @return A list of pending task IDs for the employee and team.
	 */
	@Override
	public List<Integer> getRjectedTasks(int employeeId, int teamId, boolean manager) {
		List<Integer> tasks = new ArrayList<>();
		Connection connection = DbConnection.getConnection();

		try {
			ResultSet rs = null;
			if (manager) {
				PreparedStatement stmt = connection.prepareStatement("SELECT *  FROM employees_task et  "
						+ "left JOIN tasks t ON et.TASK_ID = t.TASK_ID    WHERE (t.PENDING = 2 ) ORDER BY et.TASK_ID;\r\n");
				rs = stmt.executeQuery();
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			} else {
				PreparedStatement stmt = connection.prepareStatement("SELECT *  FROM employees_task et  "
						+ "left JOIN tasks t ON et.TASK_ID = t.TASK_ID  right JOIN employees e ON et.EMPLOYEE_ID = e.EMPLOEE_ID "
						+ " WHERE (t.PENDING = 2 and e.TEAM_ID=? and e.EMPLOEE_ID!=?) ORDER BY et.TASK_ID;\r\n");
				stmt.setInt(1, teamId);
				stmt.setInt(2, employeeId);

				rs = stmt.executeQuery();
				while (rs.next()) {
					tasks.add(rs.getInt("TASK_ID"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return tasks;
	}

	/**
	 * Approves a task by updating its status.
	 * 
	 * @param taskId         The ID of the task to be approved.
	 * @param approverRoleId The role ID of the employee approving the task.
	 */
	@Override
	public void approveTask(int taskId, int approverRoleId) {
		// Assuming approverRoleId checks are implemented elsewhere
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(APPROVE_TASK)) {
			stmt.setInt(1, taskId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Extracts an employee object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The employee object extracted from the ResultSet.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private Task extractTaskFromResultSet(ResultSet rs) throws SQLException {
		Task task = new Task();
		task.setTaskId(rs.getInt("TASK_ID"));
		task.setTaskName(rs.getString("TASK_NAME"));
		task.setTaskDescription(rs.getString("TASK_DESCRIPTION"));
		task.setPending(rs.getInt("PENDING"));
		task.setStatusId(rs.getInt("STATUS_ID"));
		task.setCreationDate(rs.getDate("CREATION_DATE"));
		return task;
	}

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

}
