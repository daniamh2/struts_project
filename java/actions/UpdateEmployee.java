package actions;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import model.EmployeeDao;
import model.EmployeeDaoImpl;

public class UpdateEmployee extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;
	private String taskId;
	private String id;
	private boolean isManager;
	private Employee emp;
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String roleId;
	private String teamId;

	@Override
	public String execute() throws Exception {
		employeeDao = EmployeeDaoImpl.getInstance();
		session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
		emp = (Employee) session.get("user");
		String role = Utilities.getRole(emp);
		isManager = role.equals("manager");
		if (isManager == true) { // if user is manager then view update employees
			Employee employee = employeeDao.getById(Integer.parseInt(employeeId));
			ServletActionContext.getRequest().setAttribute("updatedEmployee", employee);
			ServletActionContext.getRequest().setAttribute("form", "update");
			ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
			ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
			ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
			ServletActionContext.getRequest().setAttribute("role", role);

			return SUCCESS;
		}
		ServletActionContext.getRequest().setAttribute("msg", "please login");
		System.out.println("in up err");
		return ERROR;
	}

	public String saveEmp() throws Exception {

		employeeDao = EmployeeDaoImpl.getInstance();

		// Get the existing employee from the DAO
		Employee existingEmployee = employeeDao.getById(Integer.parseInt(employeeId));
		System.out.println(employeeId);
		try {
			// Update the employee attributes
			existingEmployee.setFirstName(firstName);
			existingEmployee.setLastName(lastName);
			existingEmployee.setEmail(email);
			existingEmployee.setAddress(address);
			existingEmployee.setRoleId(Integer.parseInt(roleId));
			existingEmployee.setTeamId(Integer.parseInt(teamId));

			// Update the employee using the DAO
			employeeDao.update(existingEmployee);
			addActionMessage("Employee updated successfully."); // Add success message
			ServletActionContext.getRequest().setAttribute("form", "update");
			ServletActionContext.getRequest().setAttribute("msg", "Updated successfully");
			ServletActionContext.getRequest().setAttribute("updatedEmployee", existingEmployee);
			ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
			ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
			ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}
