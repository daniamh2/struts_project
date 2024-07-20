package actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import model.EmployeeDao;
import model.EmployeeDaoImpl;

public class Employees extends ActionSupport implements SessionAware {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session;
	private Employee emp;
	private EmployeeDao employeeDao;
	private String msg;

	// Getter and setter for msg
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String execute() throws Exception {

		employeeDao = EmployeeDaoImpl.getInstance();

		try {
			session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
			emp = (Employee) session.get("user");

			if (emp != null) {
				// Map to store teamId -> list of employees for each team
 
				List<Employee> employees = employeeDao.getAll();

				ServletActionContext.getRequest().setAttribute("employeeList", employees);
				ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
				ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
				return SUCCESS;
			} else {
				return LOGIN;

			}
		} catch (

		Exception e) {
			e.printStackTrace();
			return INPUT;
		}

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

}
