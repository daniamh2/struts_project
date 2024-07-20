package beans;

import java.io.Serializable;

/**
 * Represents the status of a task.
 */
public class TaskStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int statusId;
	private String status;

	// Getters and Setters

	/**
	 * Retrieves the ID of the task status.
	 * 
	 * @return The status ID.
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * Sets the ID of the task status.
	 * 
	 * @param statusId The status ID to set.
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/**
	 * Retrieves the name of the task status.
	 * 
	 * @return The status name.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the name of the task status.
	 * 
	 * @param status The status name to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
