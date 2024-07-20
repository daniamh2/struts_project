package actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import beans.Task;
import model.EmployeeTaskDao;
import model.EmployeeTaskDaoImpl;
import model.TaskDao;
import model.TaskDaoImpl;

/**
 * Action class to handle pending tasks.
 */
public class PendingTasks extends ActionSupport implements SessionAware {
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	// Session map to store session attributes
	private SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
	private TaskDao taskDao;
	private EmployeeTaskDao employeeTaskDao;
	private String action;
	private String taskId;
	// Session attributes
	private Employee emp;

	private String sortBy;
	private String order;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	private ArrayList<String> orders;

	// Getter method for orders
	public ArrayList<String> getOrders() {
		return orders;
	}

	// Setter method for orders
	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}

	/**
	 * Executes the action to retrieve pending tasks.
	 */
	@Override
	public String execute() throws Exception {
		emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		// Initialize DAO objects
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
		taskDao = TaskDaoImpl.getInstance();
		// Initialize variables
		boolean approved = false;
		Task confirmed = (Task) session.get("confirmed");
		Task rejected = (Task) session.get("rejected");

		int employeeId = emp.getEmployeeId();
		List<Integer> pendingTasks = employeeTaskDao.getPendingTasks(employeeId, emp.getTeamId(),
				role.equals("manager"));
		List<Task> fullTasks = new ArrayList<>();
		taskDao.getAll();
		Task newRejected = null;
		Task newConfirmed = null;

		if (rejected != null) {
			newRejected = (Task) taskDao.getById(rejected.getTaskId());
		}
		if (confirmed != null) {
			newConfirmed = (Task) taskDao.getById(confirmed.getTaskId());
		}

		// Check if task was approved
		if (approved == true) {
			addActionMessage("Task " + taskId + " approved."); // Add success message
		}

		// Add confirmed task to fullTasks list
		if (newConfirmed != null && newConfirmed.getPending() == 0) {
			fullTasks.add(confirmed);
		}
		if (newRejected != null && newRejected.getPending() == 2) {
			fullTasks.add(rejected);
		}

		// Convert rejected task IDs to Task objects
		for (int task : pendingTasks) {
			Task fullTask = taskDao.getById(task);
			fullTasks.add(fullTask);
		}

		if (order != null) {
			for (int i = 0; i < orders.size(); i++) {
				String element = orders.get(i);
				// Remove outer brackets if they exist
				if (element.startsWith("[") && element.endsWith("]")) {
					element = element.substring(1, element.length() - 1);
				}
				orders.set(i, element);
			}

			String newOrder = sortBy.concat("_").concat(order);
			String prefix = newOrder.split("_")[0]; // Extract the string before "_"
			for (int i = 0; i < orders.size(); i++) {
				System.out.println("i : " + i + orders.get(i)); // Print the length of the parts array
				String element = orders.get(i);
				String[] orderParts = element.split(",");
				for (int j = 0; j < orderParts.length; j++) {
					System.out.println("parts j: " + j + orderParts[j]); // Print the length of the parts array

					String part = orderParts[j];
					String oldPrefix = part.split("_")[0].trim(); // Extract the string before "_" in each part
					System.out.println("part.split(\"_\")[0]: " + part.split("_")[0]); // Print the length of the
																						// parts array
					System.out.println("old: " + oldPrefix); // Print the length of the parts array

					if (oldPrefix.equals(prefix)) {

						// If a matching prefix is found, remove the part
						orderParts[j] = ""; // Empty string
						orders.set(i, String.join(",", orderParts));
						break; // Exit the loop after removing the first occurrence
					}
				}
			}
			orders.add(newOrder);

			Map<String, String> ordersMap = new LinkedHashMap<>(); // Use LinkedHashMap to preserve insertion order
			for (String o : orders) {
				String[] orderParts = o.split(",");

				for (String part : orderParts) {
					// Split each part by underscore
					String[] parts = part.split("_", 2); // Split into 2 parts maximum
					System.out.println("Parts length: " + parts.length); // Print the length of the parts array

					if (parts.length == 2) {
						if (parts[1] != "") {

							ordersMap.put(parts[0], parts[1]);
						}
					}
				}
			}

			System.out.println("Orders : " + orders.toString());
			System.out.println("Orders map: " + ordersMap.toString());
			// Sorting tasks if sortBy parameter is provided
			if (ordersMap != null && !(ordersMap.isEmpty())) {
				sortTasks(fullTasks, ordersMap);
			}
		}
		ServletActionContext.getRequest().setAttribute("orders", orders);
		ServletActionContext.getRequest().setAttribute("order", order);
		// Set tasks attribute in request scope
		ServletActionContext.getRequest().setAttribute("tasks", fullTasks);
		ServletActionContext.getRequest().setAttribute("title", "Pending");
		ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);

		return SUCCESS;

	}

	/**
	 * view rejected classes
	 */
	public String rejected() throws Exception {
		emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		// Initialize DAO objects
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
		taskDao = TaskDaoImpl.getInstance();
		// Initialize variables
		boolean approved = false;
		Task confirmed = (Task) session.get("confirmed");
		Task rejected = (Task) session.get("rejected");
		// Get employee's pending tasks
		int employeeId = emp.getEmployeeId();
		List<Integer> pendingTasks = employeeTaskDao.getRjectedTasks(employeeId, emp.getTeamId(),
				role.equals("manager"));
		List<Task> fullTasks = new ArrayList<>();
		taskDao.getAll();
		Task newRejected = null;
		Task newConfirmed = null;

		if (rejected != null) {
			newRejected = (Task) taskDao.getById(rejected.getTaskId());
		}
		if (confirmed != null) {
			newConfirmed = (Task) taskDao.getById(confirmed.getTaskId());
		}

		// Check if task was approved
		if (approved == true) {
			addActionMessage("Task " + taskId + " approved."); // Add success message
		}

		// Add confirmed task to fullTasks list
		if (newConfirmed != null && newConfirmed.getPending() == 0) {
			fullTasks.add(confirmed);
		}
		if (newRejected != null && newRejected.getPending() == 2) {
			fullTasks.add(rejected);
			pendingTasks.remove(Integer.valueOf(newRejected.getTaskId()));

		}
		// Convert rejected task IDs to Task objects
		for (int task : pendingTasks) {
			Task fullTask = taskDao.getById(task);
			fullTasks.add(fullTask);
		}

		// Set tasks attribute in request scope
		ServletActionContext.getRequest().setAttribute("tasks", fullTasks);
		ServletActionContext.getRequest().setAttribute("title", "Rejected");
		ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);

		return SUCCESS;
	}

	/**
	 * Approves a task.
	 */
	public String approveTask() throws Exception {
		taskDao = TaskDaoImpl.getInstance();
		Task existingTask = taskDao.getById(Integer.parseInt(taskId));
		// Update pending status
		existingTask.setPending(0);
		taskDao.update(existingTask);
		// Set confirmed task in session
		session.put("confirmed", existingTask);
		return SUCCESS;
	}

	/**
	 * rejects a task.
	 */
	public String reject() {
		try {
			taskDao = TaskDaoImpl.getInstance();
			Task existingTask = taskDao.getById(Integer.parseInt(taskId));
			existingTask.setPending(2);
			taskDao.update(existingTask);
			session.put("rejected", existingTask);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

	// Getters and setters
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	private void sortTasks(List<Task> tasks, Map<String, String> orders) {
		// Get the last column name for the main sorting
		String mainColumnName = null;
		String mainOrder = null;
		for (Map.Entry<String, String> entry : orders.entrySet()) {
			mainColumnName = entry.getKey();
			mainOrder = entry.getValue();
		}
		System.out.println(mainColumnName);
		System.out.println(mainOrder);

		// Create comparator based on the last column (main sorting)
		Comparator<Task> comparator = getComparator(mainColumnName, mainOrder);
		Comparator<Task> thenComparator;
		// Apply sorting
		// If there are more columns, create comparators for additional columns
		// (thenComparing)
		if (orders.size() > 1) {
			// Get the entries except for the last one
			List<Map.Entry<String, String>> entries = new ArrayList<>(orders.entrySet());
			entries.remove(entries.size() - 1);

			// Iterate over entries in reverse order starting from the second last
			for (int i = entries.size() - 1; i >= 0; i--) {
				Map.Entry<String, String> entry = entries.get(i);
				if (entry.getKey() != null) {

					System.out.println("Processing entry: " + entry.getKey() + "=" + entry.getValue());
					String columnName = entry.getKey();
					String order = entry.getValue();
					thenComparator = getComparator(columnName, order);
					comparator = comparator.thenComparing(thenComparator);
				}
			}
		}
		Collections.sort(tasks, comparator);

	}

	@SuppressWarnings("null")
	private Comparator<Task> getComparator(String columnName, String order) {
		Comparator<Task> comparator;
		if (columnName != null) {

			switch (columnName) {
			case "taskId":
				comparator = Comparator.comparing(Task::getTaskId);
				System.out.println(comparator);
				break;
			case "taskName":
				comparator = Comparator.comparing(Task::getTaskName);
				System.out.println("name");
				System.out.println(comparator);
				break;
			case "pending":
				comparator = Comparator.comparing(Task::getPending);
				System.out.println("name");
				System.out.println(comparator);
				break;
			case "creationDate":
				comparator = Comparator.comparing(Task::getCreationDate);
				System.out.println("name");
				System.out.println(comparator);
				break;
			case "statusId":
				comparator = Comparator.comparing(Task::getStatusId);
				comparator = comparator.reversed();
				System.out.println("name");
				System.out.println(comparator);
				break;

			// Add more cases for additional columns
			default:
				comparator = Comparator.comparing(Task::getTaskId); // Default comparator
			}
			if (order.equals("desc")) {
				comparator = comparator.reversed();
				System.out.println(comparator);
			}
			return comparator;
		}
		return Comparator.comparing(Task::getTaskId);
	}

}
