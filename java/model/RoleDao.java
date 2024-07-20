package model;

import java.util.List;

import beans.Role;

/**
 * The RoleDao interface provides methods for managing roles in the system.
 * Implementations of this interface handle operations such as retrieving roles
 * by ID or name, adding, updating, and deleting roles, and checking whether a
 * role is a leader, developer, or manager.
 */
public interface RoleDao {

	/**
	 * Retrieves a role by its ID.
	 * 
	 * @param roleId The ID of the role.
	 * @return The Role object corresponding to the given ID, or null if not found.
	 */
	Role getById(int roleId);

	/**
	 * Retrieves a list of all roles in the system.
	 * 
	 * @return A list containing all roles.
	 */
	List<Role> getAll();

	/**
	 * Adds a new role to the system.
	 * 
	 * @param role The Role object to add.
	 */
	void add(Role role);

	/**
	 * Updates an existing role in the system.
	 * 
	 * @param role The Role object to update.
	 */
	void update(Role role);

	/**
	 * Deletes a role from the system by its ID.
	 * 
	 * @param roleId The ID of the role to delete.
	 */
	void delete(int roleId);

	/**
	 * Retrieves the ID of a role by its name.
	 * 
	 * @param roleName The name of the role.
	 * @return The ID of the role, or -1 if not found.
	 */
	int getByNmae(String roleName);

	/**
	 * Checks if a role is a leader.
	 * 
	 * @param id The ID of the role to check.
	 * @return true if the role is a leader, false otherwise.
	 */
	boolean isLeader(int id);

	/**
	 * Checks if a role is a developer.
	 * 
	 * @param emp The ID of the employee whose role to check.
	 * @return true if the role is a developer, false otherwise.
	 */
	boolean isDeveloper(int emp);

	/**
	 * Checks if a role is a manager.
	 * 
	 * @param emp The ID of the employee whose role to check.
	 * @return true if the role is a manager, false otherwise.
	 */
	boolean isManager(int emp);



	/**
	 * Checks if a role is a leader.
	 * 
	 * @param id The ID of the role to check.
	 * @return true if the role is a leader, false otherwise.
	 */
	String role(int id);}
