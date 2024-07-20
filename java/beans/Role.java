package beans;

import java.io.Serializable;

/**
 * Represents a role entity with attributes such as role ID and role name.
 */
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int roleId;
	private String roleName;

	// Getters and Setters

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
	 * Retrieves the role name.
	 * 
	 * @return The role name.
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the role name.
	 * 
	 * @param roleName The role name to set.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}