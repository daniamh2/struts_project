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

public class Tasks extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
	private EmployeeTaskDao employeeTaskDao;
	private Map<String, Object> session;
	private String msg;

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

	// Getter and setter for msg
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String execute() throws Exception {
		taskDao = TaskDaoImpl.getInstance();
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
		session = ActionContext.getContext().getSession();

		Employee emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		if (emp != null) {
			List<Task> teamTasks = new ArrayList<>();
			List<Task> devTasks = new ArrayList<>();
			List<Task> allTasks;
			Map<Task, List<Employee>> taskMap;

			if (role.equals("manager")) {

				allTasks = taskDao.getAll();
				Collections.sort(allTasks, Comparator.comparing(Task::getTaskId)); // Sort tasks by taskId
				taskMap = new LinkedHashMap<>();

				for (Task task : allTasks) {
					List<Employee> empIds = employeeTaskDao.getEmployeesForTask(task.getTaskId());
					taskMap.put(task, empIds); // Add task and its associated employees to the map
				}
				ServletActionContext.getRequest().setAttribute("taskMap", taskMap); // Put the task map into session
			} else if (role.equals("leader")) {
				allTasks = employeeTaskDao.getTsksForTeams(emp.getTeamId());
				taskMap = new LinkedHashMap<>();
				for (Task task : allTasks) {
					List<Employee> empIds = employeeTaskDao.getEmployeesForTask(task.getTaskId());
					taskMap.put(task, empIds); // Add task and its associated employees to the map
				}
				List<Integer> taskIds = Utilities.getTasks(emp);

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
						String element = orders.get(i);
						String[] orderParts = element.split(",");
						for (int j = 0; j < orderParts.length; j++) {

							String part = orderParts[j];
							String oldPrefix = part.split("_")[0].trim(); // Extract the string before "_" in each part

							if (oldPrefix.equals(prefix)) {

								// If a matching prefix is found, remove the part
								orderParts[j] = ""; // Empty string
								orders.set(i, String.join(",", orderParts));
								break; // Exit the loop after removing the first occurrence
							}
						}
					}
					orders.add(newOrder);
					System.out.println(orders.toString());
					;
					Map<String, String> ordersMap = new LinkedHashMap<>();
					for (String o : orders) {
						String[] orderParts = o.split(",");

						for (String part : orderParts) {
							// Split each part by underscore
							String[] parts = part.split("_", 2); // Split into 2 parts maximum

							if (parts.length == 2) {
								if (parts[1] != "") {

									ordersMap.put(parts[0], parts[1]);
								}
							}
						}
					}

					// Sorting tasks if sortBy parameter is provided
					if (ordersMap != null && !(ordersMap.isEmpty())) {
						System.out.println(ordersMap.toString());
						taskMap = sortTaskMap(taskMap, ordersMap);
					}
				}
				ServletActionContext.getRequest().setAttribute("orders", orders);
				ServletActionContext.getRequest().setAttribute("order", order);
				ServletActionContext.getRequest().setAttribute("taskMap", taskMap);
				for (int taskId : taskIds) {
					Task empTask = taskDao.getById(taskId);
					if (empTask != null) {
						devTasks.add(empTask);
						ServletActionContext.getRequest().setAttribute("tasks", devTasks);

					}
				}
				ServletActionContext.getRequest().setAttribute("tasks", devTasks);
			} else if (role.equals("developer")) {

				// Retrieve tasks for the employee
				List<Integer> taskIds = employeeTaskDao.getTasksForEmployee(emp.getEmployeeId());
				for (int taskId : taskIds) {
					Task empTask = taskDao.getById(taskId);
					if (empTask != null) {
						devTasks.add(empTask);

					}
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
						String element = orders.get(i);
						String[] orderParts = element.split(",");
						for (int j = 0; j < orderParts.length; j++) {

							String part = orderParts[j];
							String oldPrefix = part.split("_")[0].trim(); // Extract the string before "_" in each part

							if (oldPrefix.equals(prefix)) {

								// If a matching prefix is found, remove the part
								orderParts[j] = ""; // Empty string
								orders.set(i, String.join(",", orderParts));
								break; // Exit the loop after removing the first occurrence
							}
						}
					}
					orders.add(newOrder);

					Map<String, String> ordersMap = new LinkedHashMap<>();
					for (String o : orders) {
						String[] orderParts = o.split(",");

						for (String part : orderParts) {
							// Split each part by underscore
							String[] parts = part.split("_", 2); // Split into 2 parts maximum

							if (parts.length == 2) {
								if (parts[1] != "") {

									ordersMap.put(parts[0], parts[1]);
								}
							}
						}
					}

					// Sorting tasks if sortBy parameter is provided
					if (ordersMap != null && !(ordersMap.isEmpty())) {
						sortTasks(devTasks, ordersMap);
					}
				}
				ServletActionContext.getRequest().setAttribute("orders", orders);
				ServletActionContext.getRequest().setAttribute("order", order);
				ServletActionContext.getRequest().setAttribute("tasks", devTasks);
				ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
				ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
				ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
				ServletActionContext.getRequest().setAttribute("role", role);
				return SUCCESS;

			}

			ServletActionContext.getRequest().setAttribute("tasks", teamTasks);

		}

		String message = (String) ServletActionContext.getRequest().getAttribute("message");
		ServletActionContext.getRequest().setAttribute("message", message);
		ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);

		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

	private Map<Task, List<Employee>> sortTaskMap(Map<Task, List<Employee>> taskMap, Map<String, String> orders) {

		List<Map.Entry<Task, List<Employee>>> entryList = new ArrayList<>(taskMap.entrySet());
		Comparator<Map.Entry<Task, List<Employee>>> entryComparator = (entry1, entry2) -> {
			int comparisonResult = 0;
			for (Map.Entry<String, String> orderEntry : orders.entrySet()) {
				String columnName = orderEntry.getKey();
				String sortOrder = orderEntry.getValue();
				if (sortOrder != null) {
					comparisonResult = compareTasksByColumn(entry1.getKey(), entry2.getKey(), columnName, sortOrder);
				}
			}
			List<String> columnNames = new ArrayList<>(orders.keySet());
			String mainColumn = columnNames.get(columnNames.size() - 1);
			int mainColumnIndex = columnNames.indexOf(mainColumn);
			if (comparisonResult == 0 && mainColumnIndex > 0) {
				String previousColumn = columnNames.get(mainColumnIndex - 1);
				comparisonResult = compareTasksByColumn(entry1.getKey(), entry2.getKey(), previousColumn,
						orders.get(previousColumn));
			}
			return comparisonResult;
		};
		entryList.sort(entryComparator);
		Map<Task, List<Employee>> sortedTaskMap = new LinkedHashMap<>();
		for (Map.Entry<Task, List<Employee>> entry : entryList) {
			sortedTaskMap.put(entry.getKey(), entry.getValue());
		}

		return sortedTaskMap;
	}

	// Method to compare Tasks based on the given column and order
	private int compareTasksByColumn(Task task1, Task task2, String columnName, String order) {
		switch (columnName) {
		case "taskId":
			return compare(task1.getTaskId(), task2.getTaskId(), order);
		case "taskName":
			return compare(task1.getTaskName(), task2.getTaskName(), order);
		case "pending":
			return compare(task1.getPending(), task2.getPending(), order);
		case "creationDate":
			return compare(task1.getCreationDate(), task2.getCreationDate(), order);
		case "statusId":
			return compare(task1.getStatusId(), task2.getStatusId(), order);
		case "taskDescription":
			return compare(task1.getTaskDescription(), task2.getTaskDescription(), order);

		default:
			return 0;
		}
	}

	/*
	 * private int compareEmployeesByColumn(Employee employee1, Employee employee2,
	 * String columnName, String order) { switch (columnName) { case "employeeId":
	 * return compare(employee1.getEmployeeId(), employee2.getEmployeeId(), order);
	 * case "firstName": return compare(employee1.getFirstName(),
	 * employee2.getFirstName(), order); case "roleName": return
	 * compare(employee1.getRoleId(), employee2.getRoleId(), order); default: return
	 * 0; } }
	 */
	
	// Method to compare two objects of generic type T based on the given order
	private <T extends Comparable<T>> int compare(T value1, T value2, String order) {
		int result = value1.compareTo(value2);
		return order.equals("asc") ? result : -result; // Reverse result if order is descending
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
				System.out.println("comp");
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