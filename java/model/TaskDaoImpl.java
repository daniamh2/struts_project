package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Task;
import dbcon.DbConnection;

/**
 * Implementation of the TaskDao interface for interacting with the database.
 */
public class TaskDaoImpl implements TaskDao {

	// Constants for SQL queries
	private static final String SELECT_BY_ID = "SELECT * FROM tasks WHERE TASK_ID = ?";
	private static final String SELECT_ALL = "SELECT * FROM tasks ORDER BY TASK_ID";
	private static final String INSERT = "INSERT INTO tasks (TASK_NAME, TASK_DESCRIPTION, PENDING, STATUS_ID, CREATION_DATE) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE tasks SET TASK_NAME=?, TASK_DESCRIPTION=?, PENDING=?, STATUS_ID=?, CREATION_DATE=? WHERE TASK_ID=?";
	private static final String DELETE = "DELETE FROM tasks WHERE TASK_ID=?";
	private static final TaskDao INSTANCE = new TaskDaoImpl();

	private TaskDaoImpl() {
	}

	/**
	 * Returns the singleton instance of the TaskDaoImpl class.
	 * 
	 * @return The singleton instance.
	 */
	public static TaskDao getInstance() {
		return INSTANCE;
	}

	/**
	 * Retrieves a task by its ID.
	 * 
	 * @param taskId The ID of the task.
	 * @return The Task object corresponding to the given ID, or null if not found.
	 */
	@Override
	public Task getById(int taskId) {
		Task task = null;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
			stmt.setInt(1, taskId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					task = extractTaskFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return task;
	}

	/**
	 * Retrieves a list of all tasks in the system.
	 * 
	 * @return A list containing all tasks.
	 */
	@Override
	public List<Task> getAll() {
		List<Task> tasks = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
			while (rs.next()) {
				tasks.add(extractTaskFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return tasks;
	}

	/**
	 * Adds a new task to the system.
	 * 
	 * @param task The Task object to add.
	 * @return The ID of the newly added task.
	 */
	@Override
	public int add(Task task) {
		Connection connection = DbConnection.getConnection();
		int generatedTeamId = -1; // Default value indicating failure
		try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, task.getTaskName());
			stmt.setString(2, task.getTaskDescription());
			stmt.setInt(3, task.getPending());
			stmt.setInt(4, task.getStatusId());
			stmt.setDate(5, new java.sql.Date(task.getCreationDate().getTime()));
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				generatedTeamId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return generatedTeamId;
	}

	/**
	 * Updates an existing task in the system.
	 * 
	 * @param task The Task object to update.
	 */
	@Override
	public void update(Task task) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
			stmt.setString(1, task.getTaskName());
			stmt.setString(2, task.getTaskDescription());
			stmt.setInt(3, task.getPending());
			stmt.setInt(4, task.getStatusId());
			stmt.setDate(5, new java.sql.Date(task.getCreationDate().getTime()));
			stmt.setInt(6, task.getTaskId());
			stmt.executeUpdate();
			System.out.println(task.getStatusId() + "TAS");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes a task from the system by its ID.
	 * 
	 * @param taskId The ID of the task to delete.
	 */
	@Override
	public void delete(int taskId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
			stmt.setInt(1, taskId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Extracts a task object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The task object extracted from the ResultSet.
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
}
