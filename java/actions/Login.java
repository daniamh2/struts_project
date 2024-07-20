package actions;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import model.EmployeeDao;
import model.EmployeeDaoImpl;

public class Login extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;

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

	private String email;
	private String password;
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;

	public String execute() throws Exception {

		employeeDao = EmployeeDaoImpl.getInstance();

		Employee employee = employeeDao.checkLogin(email, password);

		if (employee != null) {

			session.put("user", employee);
			String role = Utilities.getRole(employee);
			System.out.println("in Logged in successfully");
			ServletActionContext.getRequest().setAttribute("role", role);
			addActionMessage("Logged in successfully."); // Add success message
			return SUCCESS;
		} else {
			addActionError("Invalid email or password.");
			return ERROR;
		}
	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

	public String logout() {
		if (session != null) {
			session.invalidate();
		}
		addActionMessage("Logged out successfully."); // Add success message
		return "success";
	}
}