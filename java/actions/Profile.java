package actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.*;
import model.*;

/**
 * This Action class handles user profile-related functionality.
 *
 * @version 1.0 10 Mar 2024
 */
public class Profile extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Session attributes */
	private SessionMap<String, Object> session;

	/** DAO instance */
	private TeamDao teamDao;
	private TaskDao taskDao;

	private String sortBy;
	private String order;
	private String newTask;

	public String getNewTask() {
		return newTask;
	}

	public void setNewTask(String newTask) {
		this.newTask = newTask;
	}

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
	 * Executes the action to handle user profile-related functionality.
	 *
	 * @return SUCCESS if the action is executed successfully, otherwise an error
	 *         code.
	 * @throws Exception if an error occurs during execution.
	 */
	@Override
	public String execute() throws Exception, SQLException {
		HttpSession session1 = ServletActionContext.getRequest().getSession(false);
		if (session1 == null || session1.getAttribute("user") == null) {
			return "login";
		}
		try {
			teamDao = TeamDaoImpl.getInstance();
			taskDao = TaskDaoImpl.getInstance();
			session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
			List<Team> teams = Utilities.getTeams();

			Employee employee = (Employee) session.get("user");
			String role = Utilities.getRole(employee);

			List<Task> tasks = new ArrayList<>();
			java.util.Map<Integer, List<Employee>> teamEmployees = new java.util.HashMap<>(); // get team's

			if (role.equals("leader")) {

				int leaderTeam = employee.getTeamId();
				List<Employee> employees = teamDao.getEmployeesByTeamId(leaderTeam);
				teamEmployees.put(leaderTeam, employees);

				ServletActionContext.getRequest().setAttribute("teamEmployees", teamEmployees);
			} else if (role.equals("manager")) {

				for (Team team : teams) {
					List<Employee> employees = teamDao.getEmployeesByTeamId(team.getTeamId());
					teamEmployees.put(team.getTeamId(), employees);
				}
				ServletActionContext.getRequest().setAttribute("teamEmployees", teamEmployees);
			}

			// Retrieve tasks for the employee
			List<Integer> taskIds = Utilities.getTasks(employee);

			for (int taskId : taskIds) {
				Task empTask = taskDao.getById(taskId);
				if (empTask != null) {
					tasks.add(empTask);
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
						System.out.println("i : " + i+orders.get(i)); // Print the length of the parts array
					    String element = orders.get(i);
					    String[] orderParts = element.split(",");
					    for (int j = 0; j < orderParts.length; j++) {
							System.out.println("parts j: " + j+ orderParts[j]); // Print the length of the parts array
							
					        String part = orderParts[j];
					        String oldPrefix = part.split("_")[0].trim(); // Extract the string before "_" in each part
							System.out.println("part.split(\"_\")[0]: " + part.split("_")[0]); // Print the length of the parts array
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

				Map<String, String> ordersMap = new LinkedHashMap<>(); 
				for (String o : orders) {
				    String[] orderParts = o.split(",");

				    for (String part : orderParts) {
				        // Split each part by underscore
				        String[] parts = part.split("_", 2); // Split into 2 parts maximum
				        System.out.println("Parts length: " + parts.length); // Print the length of the parts array

				        if (parts.length == 2) {
				        	if (parts[1]!="") {
				            ordersMap.put(parts[0], parts[1]);}
				        }
				    }
				}

				System.out.println("Orders : " + orders.toString());
				System.out.println("Orders map: " + ordersMap.toString());
				// Sorting tasks if sortBy parameter is provided
				if (ordersMap != null && !(ordersMap.isEmpty())) {
					sortTasks(tasks, ordersMap);
				}
			}
			ServletActionContext.getRequest().setAttribute("orders", orders);
			ServletActionContext.getRequest().setAttribute("order", order);
			ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
			ServletActionContext.getRequest().setAttribute("teams", teams);
			ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
			ServletActionContext.getRequest().setAttribute("role", role);
			ServletActionContext.getRequest().setAttribute("tasks", tasks);
			return SUCCESS;
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("msg", e.getMessage());

			e.printStackTrace();
			return ERROR;
		}

	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
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
					comparator=comparator.thenComparing(thenComparator);
				}
			}
		}		Collections.sort(tasks, comparator);


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