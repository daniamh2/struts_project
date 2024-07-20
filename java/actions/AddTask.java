package actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import beans.Task;
import model.EmployeeDao;
import model.EmployeeDaoImpl;
import model.EmployeeTaskDao;
import model.EmployeeTaskDaoImpl;
import model.TaskDao;
import model.TaskDaoImpl;

public class AddTask extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;
	private TaskDao taskDao;
	private EmployeeTaskDao employeeTaskDao;
	private String taskId;
	private String id;
	private Employee emp;
	private String taskName;
	private String newTask;

	public String getNewTask() {
		return newTask;
	}

	public void setNewTask(String newTask) {
		this.newTask = newTask;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}

	private String[] employees;
	private String taskDescription;
	private int pending;
	private int statusId;

	public AddTask() {
		super();
		employeeDao = EmployeeDaoImpl.getInstance();
		taskDao = TaskDaoImpl.getInstance();
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
	}

	public String execute() throws Exception {
		try {
			session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
			Employee emp = (Employee) session.get("user");
			String role = Utilities.getRole(emp);

			Task task = new Task();
			task.setTaskName(taskName);
			task.setTaskDescription(taskDescription);
			task.setCreationDate(new java.util.Date());
			task.setStatusId(3); // Set status to "assigned"
			String[] assignedEmployees = employees;
			int newId = 0;

		
			if (role.equals("developer")) {
				task.setPending(1);
				newId = taskDao.add(task); // retrieve new task id
				if (newId != -1) {
					task.setPending(1);

					employeeTaskDao.assignTask(emp.getEmployeeId(), newId); // assign task to selected employees
				}

			} else if (role.equals("leader") && assignedEmployees == null) {

				task.setPending(1);
				newId = taskDao.add(task);

				if (newId != -1) {
					employeeTaskDao.assignTask(emp.getEmployeeId(), newId);
				}
			} else if (role.equals("leader") && assignedEmployees[0].equals(String.valueOf(emp.getEmployeeId()))
					&& assignedEmployees.length == 1) {

				task.setPending(1);
				newId = taskDao.add(task);

				if (newId != -1) {
					employeeTaskDao.assignTask(Integer.parseInt(assignedEmployees[0]), newId);
				}

			} else {

				task.setPending(0);
				if (assignedEmployees != null) {
					newId = taskDao.add(task);
					if (newId != -1) {
						for (String employeeId : assignedEmployees) {
							employeeTaskDao.assignTask(Integer.parseInt(employeeId), newId);
						}
					}
				}
			}
		
			ServletActionContext.getRequest().setAttribute("msg", "added successfully");

			Gson gson = new Gson();
			 newTask = gson.toJson(taskDao.getById(newId));
			ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
			ServletActionContext.getRequest().setAttribute("newTask", newTask);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			return ERROR;
		}
	}

	public String showAddTaskForm() throws SQLException {
		List<Employee> employees = new ArrayList<>();
		Map<String, Object> session = getSession();
		Employee emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		if (emp != null) {

			if (role.equals("manager")) {
				employees = employeeDao.getAll();
				ServletActionContext.getRequest().setAttribute("pending", 0);

			} else if (role.equals("leader")) {

				employees = employeeDao.getByfTeam(emp.getTeamId());

			} else {
				ServletActionContext.getRequest().setAttribute("pending", 1);
			}
		} else {
			return INPUT;
		}
		ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);
		addActionContext("employees", employees);
		addActionContext("statusId", 3);
		return SUCCESS;
	}

	// Helper methods

	private Map<String, Object> getSession() {
		return org.apache.struts2.ServletActionContext.getContext().getSession();
	}

	private void addActionContext(String key, Object value) {
		org.apache.struts2.ServletActionContext.getContext().put(key, value);
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;

	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

}