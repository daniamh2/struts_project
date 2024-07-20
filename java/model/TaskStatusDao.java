package model;

import beans.TaskStatus;
import java.util.List;

/**
 * The TaskStatusDao interface provides methods for managing task statuses in
 * the system. Implementations of this interface handle operations such as
 * retrieving task statuses by ID, retrieving all task statuses, adding,
 * updating, and deleting task statuses.
 */
public interface TaskStatusDao {

	/**
	 * Retrieves a task status by its ID.
	 * 
	 * @param statusId The ID of the task status.
	 * @return The TaskStatus object corresponding to the given ID, or null if not
	 *         found.
	 */
	TaskStatus getById(int statusId);

	/**
	 * Retrieves a list of all task statuses in the system.
	 * 
	 * @return A list containing all task statuses.
	 */
	List<TaskStatus> getAll();

	/**
	 * Adds a new task status to the system.
	 * 
	 * @param status The TaskStatus object to add.
	 */
	void add(TaskStatus status);

	/**
	 * Updates an existing task status in the system.
	 * 
	 * @param status The TaskStatus object to update.
	 */
	void update(TaskStatus status);

	/**
	 * Deletes a task status from the system by its ID.
	 * 
	 * @param statusId The ID of the task status to delete.
	 */
	void delete(int statusId);
}
