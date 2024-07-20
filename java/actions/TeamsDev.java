package actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import beans.Team;
import model.TeamDao;
import model.TeamDaoImpl;

public class TeamsDev extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
	private Employee emp = (Employee) session.get("user");
	private TeamDao teamDao; 

	@Override
	public String execute() throws Exception {

		teamDao = TeamDaoImpl.getInstance();

		try {
			if (emp != null) {
				Team team = null;
				team = teamDao.getById(emp.getTeamId());
				// Map to store teamId -> list of employees for each team

				List<Employee> employees = teamDao.getEmployeesByTeamId(team.getTeamId());// get employees
				System.out.println(employees.size());
				System.out.println("size");
				ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
				ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
				ServletActionContext.getRequest().setAttribute("teamEmployees", employees);
				ServletActionContext.getRequest().setAttribute("team", team);
				System.out.print(team.getTeamName());

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
