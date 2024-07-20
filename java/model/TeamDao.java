package model;

import java.util.List;

import beans.Employee;
import beans.Team;

/**
 * The TeamDao interface provides methods for managing teams in the system.
 * Implementations of this interface handle operations such as retrieving teams
 * by ID, retrieving all teams, adding, updating, and deleting teams, and
 * retrieving employees associated with a particular team.
 */
public interface TeamDao {

	/**
	 * Retrieves a team by its ID.
	 * 
	 * @param teamId The ID of the team.
	 * @return The Team object corresponding to the given ID, or null if not found.
	 */
	Team getById(int teamId);

	/**
	 * Retrieves a list of all teams in the system.
	 * 
	 * @return A list containing all teams.
	 */
	List<Team> getAll();

	/**
	 * Adds a new team to the system.
	 * 
	 * @param team The Team object to add.
	 * @return The ID of the newly added team.
	 */
	int add(Team team);

	/**
	 * Updates an existing team in the system.
	 * 
	 * @param team The Team object to update.
	 */
	void update(Team team);

	/**
	 * Deletes a team from the system by its ID.
	 * 
	 * @param teamId The ID of the team to delete.
	 */
	void delete(int teamId);

	/**
	 * Retrieves a list of employees associated with the specified team.
	 * 
	 * @param teamId The ID of the team.
	 * @return A list containing employees who belong to the specified team.
	 */
	List<Employee> getEmployeesByTeamId(int teamId);
}
