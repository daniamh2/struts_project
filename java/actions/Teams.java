package actions;

import java.util.ArrayList;
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

public class Teams extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamDao teamDao;
	private SessionMap<String, Object> session;

	public Teams() {
		super();
		teamDao = TeamDaoImpl.getInstance();
	}

	@Override
	public String execute() throws Exception {
		try {
			session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
			Employee emp = (Employee) session.get("user");
			String role = Utilities.getRole(emp);

			if (emp != null) {
				List<Employee> employees = new ArrayList<>();

				if (role.equals("manager")) {
					List<Team> teams = teamDao.getAll();
					// Map to store teamId -> list of employees for each team
					java.util.Map<Integer, List<Employee>> teamEmployees = new java.util.HashMap<>();

					for (Team team : teams) {
						employees = teamDao.getEmployeesByTeamId(team.getTeamId());// get employees
						// for each team
						teamEmployees.put(team.getTeamId(), employees); // map team with employees
					}
					ServletActionContext.getRequest().setAttribute("teamEmployees", teamEmployees);
					ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
					ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
					ServletActionContext.getRequest().setAttribute("statusIds", Utilities.getStatuses());
					ServletActionContext.getRequest().setAttribute("role", role);
					return SUCCESS;
				} else {
					addActionError("You do not have permission to access this page.");
					return ERROR;
				}
			} else {
				return INPUT;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

}
