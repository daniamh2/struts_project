package model;

import dbcon.DbConnection;
import beans.TaskStatus;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TaskStatusDaoImpl implements TaskStatusDao {

	// Constants for SQL queries
	private static final String SELECT_BY_ID = "SELECT * FROM task_status WHERE STATUS_ID = ?";
	private static final String SELECT_ALL = "SELECT * FROM task_status";
	private static final String INSERT = "INSERT INTO task_status (STATUS) VALUES (?)";
	private static final String UPDATE = "UPDATE task_status SET STATUS=? WHERE STATUS_ID=?";
	private static final String DELETE = "DELETE FROM task_status WHERE STATUS_ID=?";
	private static final TaskStatusDao INSTANCE = new TaskStatusDaoImpl();

	private TaskStatusDaoImpl() {
	}

	/**
	 * Retrieves the singleton instance of the TeamDaoImpl class.
	 * 
	 * @return The singleton instance of TeamDaoImpl.
	 */
	public static TaskStatusDao getInstance() {
		return INSTANCE;
	}

	/**
	 * Retrieves a task status by its ID.
	 * 
	 * @param statusId The ID of the task status.
	 * @return The TaskStatus object corresponding to the given ID, or null if not
	 *         found.
	 */
	@Override
	public TaskStatus getById(int statusId) {
		TaskStatus status = null;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
			stmt.setInt(1, statusId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					status = extractStatusFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return status;
	}

	/**
	 * Retrieves a list of all task statuses in the system.
	 * 
	 * @return A list containing all task statuses.
	 */
	@Override
	public List<TaskStatus> getAll() {
		List<TaskStatus> statuses = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
			while (rs.next()) {
				statuses.add(extractStatusFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return statuses;
	}

	/**
	 * Adds a new task status to the system.
	 * 
	 * @param status The TaskStatus object to add.
	 */
	@Override
	public void add(TaskStatus status) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(INSERT)) {
			stmt.setString(1, status.getStatus());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Updates an existing task status in the system.
	 * 
	 * @param status The TaskStatus object to update.
	 */
	@Override
	public void update(TaskStatus status) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
			stmt.setString(1, status.getStatus());
			stmt.setInt(2, status.getStatusId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes a task status from the system by its ID.
	 * 
	 * @param statusId The ID of the task status to delete.
	 */
	@Override
	public void delete(int statusId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
			stmt.setInt(1, statusId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Extracts a taskStatus object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The status object extracted from the ResultSet.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private TaskStatus extractStatusFromResultSet(ResultSet rs) throws SQLException {
		TaskStatus status = new TaskStatus();
		status.setStatusId(rs.getInt("STATUS_ID"));
		status.setStatus(rs.getString("STATUS"));
		return status;
	}
}
