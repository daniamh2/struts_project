package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Employee;
import beans.Team;
import dbcon.DbConnection;

/**
 * The TeamDaoImpl class provides methods for accessing and modifying team data
 * in the database. It implements the TeamDao interface.
 */
public class TeamDaoImpl implements TeamDao {

	// Constants for SQL queries
	private static final String SELECT_BY_ID = "SELECT * FROM teams WHERE TEAM_ID = ?";
	private static final String SELECT_ALL = "SELECT * FROM teams";
	private static final String INSERT = "INSERT INTO teams (TEAM_NAME, LEADER_ID) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE teams SET TEAM_NAME=?, LEADER_ID=? WHERE TEAM_ID=?";
	private static final String DELETE = "DELETE FROM teams WHERE TEAM_ID=?";
	private static final TeamDao INSTANCE = new TeamDaoImpl();

	private TeamDaoImpl() {
	}

	/**
	 * Retrieves the singleton instance of the TeamDaoImpl class.
	 * 
	 * @return The singleton instance of TeamDaoImpl.
	 */
	public static TeamDao getInstance() {
		return INSTANCE;
	}

	// Implementation of TeamDao methods...

	/**
	 * Retrieves a team by its unique identifier.
	 * 
	 * @param teamId The unique identifier of the team.
	 * @return The team object if found, null otherwise.
	 */
	@Override
	public Team getById(int teamId) {
		Team team = null;
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
			stmt.setInt(1, teamId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					team = extractTeamFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return team;
	}

	/**
	 * Retrieves all teams from the database.
	 * 
	 * @return A list of all teams.
	 */
	@Override
	public List<Team> getAll() {
		List<Team> teams = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
			while (rs.next()) {
				teams.add(extractTeamFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
		return teams;
	}

	/**
	 * Adds a new team to the database.
	 * 
	 * @param team The team to be added.
	 * @return The auto-generated ID of the newly added team.
	 */
	@Override
	public int add(Team team) {
		Connection connection = DbConnection.getConnection();
		int generatedTeamId = -1; // Default value indicating failure
		try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, team.getTeamName());
			stmt.setInt(2, team.getLeaderId());
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
	 * Updates an existing team in the database.
	 * 
	 * @param team The team to be updated.
	 */
	@Override
	public void update(Team team) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
			stmt.setString(1, team.getTeamName());
			stmt.setInt(2, team.getLeaderId());
			stmt.setInt(3, team.getTeamId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Deletes a team from the database.
	 * 
	 * @param teamId The unique identifier of the team to be deleted.
	 */
	@Override
	public void delete(int teamId) {
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
			stmt.setInt(1, teamId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connection);
		}
	}

	/**
	 * Extracts a team object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The team object extracted from the ResultSet.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private Team extractTeamFromResultSet(ResultSet rs) throws SQLException {
		Team team = new Team();
		team.setTeamId(rs.getInt("TEAM_ID"));
		team.setTeamName(rs.getString("TEAM_NAME"));
		team.setLeaderId(rs.getInt("LEADER_ID"));
		return team;
	}

	/**
	 * Retrieves all employees belonging to a specific team.
	 * 
	 * @param teamId The unique identifier of the team.
	 * @return A list of employees belonging to the specified team.
	 */
	@Override
	public List<Employee> getEmployeesByTeamId(int teamId) {
		List<Employee> employees = new ArrayList<>();
		Connection connection = DbConnection.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(
				"SELECT FIRST_NAME, LAST_NAME, ROLE_ID,EMPLOEE_ID FROM employees WHERE TEAM_ID = ?")) {
			stmt.setInt(1, teamId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Employee employee = extractEmployeeFromResultSet(rs);
					employees.add(employee);
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
	 * Extracts a Team object from the given ResultSet.
	 * 
	 * @param rs The ResultSet containing team data.
	 * @return The Team object extracted from the ResultSet.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
		Employee employee = new Employee();
		try {
			employee.setEmployeeId(rs.getInt("EMPLOEE_ID"));
			employee.setFirstName(rs.getString("FIRST_NAME"));
			employee.setLastName(rs.getString("LAST_NAME"));
			employee.setRoleId(rs.getInt("ROLE_ID"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return employee;
	}

}
