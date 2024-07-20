package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a task entity with various attributes such as task ID, name,
 * description, pending status, status ID, and creation date.
 */
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int taskId;
	private String taskName;
	private String taskDescription;
	private int pending;
	private int statusId;
	private Date creationDate;

	// Getters and Setters
	/**
	 * Retrieves the task ID.
	 * 
	 * @return The task ID.
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * Sets the task ID.
	 * 
	 * @param taskId The task ID to set.
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * Retrieves the task name.
	 * 
	 * @return The task name.
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the task name.
	 * 
	 * @param taskName The task name to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Retrieves the task description.
	 * 
	 * @return The task description.
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * Sets the task description.
	 * 
	 * @param taskDescription The task description to set.
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/**
	 * Checks if the task is pending.
	 * 
	 * @return True if the task is pending, false otherwise.
	 */
	public int getPending() {
		return pending;
	}

	/**
	 * Sets the pending status of the task.
	 * 
	 * @param pending True if the task is pending, false otherwise.
	 */
	public void setPending(int pending) {
		this.pending = pending;
	}

	/**
	 * Retrieves the status ID of the task.
	 * 
	 * @return The status ID of the task.
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * Sets the status ID of the task.
	 * 
	 * @param statusId The status ID to set.
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/**
	 * Retrieves the creation date of the task.
	 * 
	 * @return The creation date of the task.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date of the task.
	 * 
	 * @param creationDate The creation date to set.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}