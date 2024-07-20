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

public class AddEmployee extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8728276568941155700L;
	private SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
	private EmployeeDao employeeDao;
	private String roleId;
	private String teamId;
	private String address;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	public String execute() throws Exception {
		try {
			Employee emp = (Employee) session.get("user");

			if (emp != null) {
				String role = Utilities.getRole(emp);
				if (role.equals("manager")) {
					
					ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
					ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
					return SUCCESS;
				} else {
					addActionError("You do not have permission to access this page.");
					return ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("An error occurred: " + e.getMessage());

			return ERROR;
		}

		return INPUT;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String save() throws Exception {
		try {
			employeeDao = EmployeeDaoImpl.getInstance();
			Employee newEmployee = new Employee();
			int roleIdInt = Integer.parseInt(roleId);
			int teamIdInt = Integer.parseInt(teamId);

			System.out.println(roleId);
			System.out.println(teamId);

			newEmployee.setRoleId(roleIdInt);
			newEmployee.setTeamId(teamIdInt);
			newEmployee.setAddress(address);
			newEmployee.setFirstName(firstName);
			newEmployee.setLastName(lastName);
			newEmployee.setEmail(email);
			newEmployee.setPassword(password);
			ServletActionContext.getRequest().setAttribute("msg", "added successfully");
			employeeDao.add(newEmployee);

			ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
			ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("An error occurred: " + e.getMessage());

			return ERROR;
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;

	}
}