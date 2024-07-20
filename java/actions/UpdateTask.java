package actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

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

public class UpdateTask extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;
	private TaskDao taskDao;
	private EmployeeTaskDao employeeTaskDao;
	private String taskId;
	private String id;
	private Employee emp;
	private String taskName;

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

	@Override
	public String execute() throws Exception {
		session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
		employeeDao = EmployeeDaoImpl.getInstance();
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
		taskDao = TaskDaoImpl.getInstance();

		emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		System.out.println(taskId);
		int taskIdInt = Integer.parseInt(taskId);
		Task task = taskDao.getById(taskIdInt);
		ServletActionContext.getRequest().setAttribute("task", task);
		List<Integer> empIds = new ArrayList<>();

		List<Employee> empTask = employeeTaskDao.getEmployeesForTask(Integer.parseInt(taskId));
		List<Employee> allEmp = new ArrayList<>();

		for (Employee empId : empTask) {
			if (empId != null) {
				empIds.add(empId.getEmployeeId());
			}
		}

		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);
		ServletActionContext.getRequest().setAttribute("empTask", employees);
		ServletActionContext.getRequest().setAttribute("empIds", empIds);
		ServletActionContext.getRequest().setAttribute("task", task);
		if (emp != null) {
			String ids = id;

			if (ids != null) {
				session.put("id", ids);
			}

			if (role.equals("manager")) {// all employees
				allEmp = employeeDao.getAll();

				ServletActionContext.getRequest().setAttribute("employees", allEmp);

				return SUCCESS;

			} else if (role.equals("leader")) {// team employees

				allEmp = employeeDao.getByfTeam(emp.getTeamId());

				ServletActionContext.getRequest().setAttribute("employees", allEmp);

				return SUCCESS;
			} else {
				Employee employee = (Employee) session.get("user");
				
				ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
				ServletActionContext.getRequest().setAttribute("role", role);
				ServletActionContext.getRequest().setAttribute("employees", employee);
				return SUCCESS;

			}
		}
		return INPUT;
	}

	public String saveT() throws Exception,SQLException {
try {
		employeeDao = EmployeeDaoImpl.getInstance();
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();
		taskDao = TaskDaoImpl.getInstance();
		emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);

		List<Employee> oldEmp = employeeTaskDao.getEmployeesForTask(Integer.parseInt(taskId)); // get old employees for
		Task confirmed = (Task) session.get("confirmed");

		if (confirmed != null && (confirmed.getTaskId() == Integer.parseInt(taskId))) {
			session.put("confirmed", null);
		}
		Task existingTask = taskDao.getById(Integer.parseInt(taskId)); // GET UPDATED TASK ID

		existingTask.setTaskName(taskName); // SET NEW VALUES
		existingTask.setTaskDescription(taskDescription);
		existingTask.setPending(pending);
		existingTask.setStatusId(statusId);

		String[] assignedEmployees = employees;
		taskDao.update(existingTask);
		System.out.println("assignedEmployees " + assignedEmployees + employees);

		if (assignedEmployees != null) {
			for (String employeeId : assignedEmployees) {
				employeeTaskDao.assignTask(Integer.parseInt(employeeId), (Integer.parseInt(taskId)));
			}
			for (Employee oldEmployee : oldEmp) { // CHECK IF OLD EMPLOYEE STILL CHECKED
				if (!Arrays.asList(assignedEmployees).contains(String.valueOf(oldEmployee.getEmployeeId()))) {
					Employee old = (oldEmployee);
					employeeTaskDao.delete(old.getEmployeeId(), (Integer.parseInt(taskId)));
				}
			}
		}
		ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
		ServletActionContext.getRequest().setAttribute("role", role);
		ServletActionContext.getRequest().setAttribute("msg", "task updated successfully."); // Add success message
		ServletActionContext.getRequest().setAttribute("task", existingTask);
		execute();
		return SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		addActionError("An error occurred: " + e.getMessage());
		return ERROR;
	
	}}

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
