package beans;

import java.io.Serializable;

/**
 * Represents a team in the organization.
 */
public class Team implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int teamId; // The ID of the team
	private String teamName; // The name of the team
	private int leaderId; // The ID of the team leader

	// Getters and Setters

	/**
	 * Retrieves the ID of the team.
	 * 
	 * @return The team ID.
	 */
	public int getTeamId() {
		return teamId;
	}

	/**
	 * Sets the ID of the team.
	 * 
	 * @param teamId The team ID to set.
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	/**
	 * Retrieves the name of the team.
	 * 
	 * @return The team name.
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Sets the name of the team.
	 * 
	 * @param teamName The team name to set.
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Retrieves the ID of the team leader.
	 * 
	 * @return The leader ID.
	 */
	public int getLeaderId() {
		return leaderId;
	}

	/**
	 * Sets the ID of the team leader.
	 * 
	 * @param leaderId The leader ID to set.
	 */
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
}
