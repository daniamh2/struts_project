package model;

import java.util.List;

import beans.Task;

/**
 * The TaskDao interface provides methods for managing tasks in the system.
 * Implementations of this interface handle operations such as retrieving tasks
 * by ID, retrieving all tasks, adding, updating, and deleting tasks.
 */
public interface TaskDao {

	/**
	 * Retrieves a task by its ID.
	 * 
	 * @param taskId The ID of the task.
	 * @return The Task object corresponding to the given ID, or null if not found.
	 */
	Task getById(int taskId);

	/**
	 * Retrieves a list of all tasks in the system.
	 * 
	 * @return A list containing all tasks.
	 */
	List<Task> getAll();

	/**
	 * Adds a new task to the system.
	 * 
	 * @param task The Task object to add.
	 * @return The ID of the newly added task.
	 */
	int add(Task task);

	/**
	 * Updates an existing task in the system.
	 * 
	 * @param task The Task object to update.
	 */
	void update(Task task);

	/**
	 * Deletes a task from the system by its ID.
	 * 
	 * @param taskId The ID of the task to delete.
	 */
	void delete(int taskId);
}
