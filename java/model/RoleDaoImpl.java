package model;

import dbcon.DbConnection;
import beans.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the RoleDao interface for interacting with the database.
 */
public class RoleDaoImpl implements RoleDao {

	// Constants for SQL queries
	private static final String SELECT_BY_ID = "SELECT * FROM roles WHERE ROLE_ID = ?";
	private static final String SELECT_NAME_BY_ID = "SELECT * FROM roles WHERE ROLE_ID = ?";
	private static final String SELECT_BY_NAME = "SELECT * FROM roles WHERE ROLE_NAME = ?";
	private static final String SELECT_ALL = "SELECT * FROM roles";
	private static final String INSERT = "INSERT INTO roles (ROLE_NAME) VALUES (?)";
	private static final String UPDATE = "UPDATE roles SET ROLE_NAME=? WHERE ROLE_ID=?";
	private static final String DELETE = "DELETE FROM roles WHERE ROLE_ID=?";
	private static final RoleDao INSTANCE = new RoleDaoImpl();

	private RoleDaoImpl() {
	}

	/**
	 * Returns the singleton instance of the RoleDaoImpl class.
	 * 
	 * @return The singleton instance.
	 */
	public static RoleDao getInstance() {
		return INSTANCE;
	}
	// Implementation of TeamDao methods...

	/**
	 * Retrieves a role by its unique identifier.
	 * 
	 * @param roleId The unique identifier of the eole.
	 * @return The role object if found, null otherwise.
	 */
	@Override
	public Role getById(int roleId) {
		Role role = null;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
			stmt.setInt(1, roleId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					role = extractRoleFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Ensure that the database connection is closed
			DbConnection.closeConnection(connection);
		}
		return role;
	}

	/**
	 * Retrieves the ID of a role by its name.
	 * 
	 * @param roleName The name of the role.
	 * @return The ID of the role, or -1 if not found.
	 */
	@Override
	public int getByNmae(String roleName) {
		int role = -1;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME)) {
			stmt.setString(1, roleName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					role = rs.getInt("role_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Ensure that the database connection is closed
			DbConnection.closeConnection(connection);
		}
		return role;
	}

	/**
	 * Checks if a role is a leader.
	 * 
	 * @param id The ID of the role to check.
	 * @return true if the role is a leader, false otherwise.
	 */
	@Override
	public boolean isLeader(int emp) {
		boolean isLeader = false;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(
				"SELECT e.*, r.ROLE_NAME FROM employees e JOIN roles r ON e.ROLE_ID = r.ROLE_ID WHERE r.ROLE_NAME = 'leader' and e.EMPLOEE_ID =?")) {
			stmt.setInt(1, emp);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					isLeader = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Ensure that the database connection is closed
			DbConnection.closeConnection(connection);
		}
		return isLeader;
	}

	/**
	 * Checks if a role is a developer.
	 * 
	 * @param emp The ID of the employee whose role to check.
	 * @return true if the role is a developer, false otherwise.
	 */
	@Override

	public boolean isDeveloper(int emp) {
		boolean isDeveloper = false;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(
				"SELECT e.*, r.ROLE_NAME FROM employees e JOIN roles r ON e.ROLE_ID = r.ROLE_ID WHERE r.ROLE_NAME = 'developer' AND e.EMPLOEE_ID = ?");) {
			stmt.setInt(1, emp);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					isDeveloper = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return isDeveloper;
	}

	/**
	 * Checks if a role is a manager.
	 * 
	 * @param emp The ID of the employee whose role to check.
	 * @return true if the role is a manager, false otherwise.
	 */
	@Override

	public boolean isManager(int emp) {
		boolean isManager = false;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(
				"SELECT e.*, r.ROLE_NAME FROM employees e JOIN roles r ON e.ROLE_ID = r.ROLE_ID WHERE r.ROLE_NAME = 'manager' AND e.EMPLOEE_ID = ?");) {
			stmt.setInt(1, emp);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					isManager = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return isManager;
	}

	/**
	 * Retrieves role name for Role Id..
	 * 
	 * @return role name.
	 */
	@Override
	public String role(int id) {
		String role = "";
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_NAME_BY_ID)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					role = rs.getString("ROLE_NAME");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return role;
	}
	
	/**
	 * Retrieves all roles from the database.
	 * 
	 * @return A list of all roles.
	 */
	@Override
	public List<Role> getAll() {
		List<Role> roles = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
			while (rs.next()) {
				roles.add(extractRoleFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return roles;
	}

	/**
	 * Adds a new role to the database.
	 * 
	 * @param role The role to be added.
	 * @return The auto-generated ID of the newly added role.
	 */
	@Override
	public void add(Role role) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(INSERT)) {
			stmt.setString(1, role.getRoleName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Updates an existing role in the database.
	 * 
	 * @param role The role to be updated.
	 */
	@Override
	public void update(Role role) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
			stmt.setString(1, role.getRoleName());
			stmt.setInt(2, role.getRoleId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes a role from the database.
	 * 
	 * @param roleId The unique identifier of the roles to be deleted.
	 */
	@Override
	public void delete(int roleId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
			stmt.setInt(1, roleId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Extract role information from the ResultSet and create a Role object
	 * 
	 * @param rs The ResultSet containing role data.
	 * @return A Role object extracted from the ResultSet.
	 * @throws SQLException If an SQL error occurs.
	 */
	private Role extractRoleFromResultSet(ResultSet rs) throws SQLException {

		Role role = new Role();
		role.setRoleId(rs.getInt("ROLE_ID"));
		role.setRoleName(rs.getString("ROLE_NAME"));
		return role;
	}
}
