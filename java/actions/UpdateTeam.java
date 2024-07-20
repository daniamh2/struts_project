package actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import beans.Employee;
import beans.Team;
import model.EmployeeDao;
import model.EmployeeDaoImpl;
import model.TeamDao;
import model.TeamDaoImpl;

public class UpdateTeam extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;
	private TeamDao teamDao;
	private String teamId;
	private String teamName;
	private String[] developer;
	private int leader;

	@Override
	public String execute() throws Exception {

		System.out.println(session + "sess");
		System.out.println(ActionContext.getContext().getSession() + "sess 1");
		if (ActionContext.getContext().getSession() == null) {
			return LOGIN;
		}
		employeeDao = EmployeeDaoImpl.getInstance();
		teamDao = TeamDaoImpl.getInstance();
		Employee employee = (Employee) session.get("user");
		String role = Utilities.getRole(employee);

		session = (SessionMap<String, Object>) ActionContext.getContext().getSession();

		List<Employee> leaders = employeeDao.getByRoleNames("leader");
		List<Employee> developers = employeeDao.getByRoleNames("developer");
		List<Employee> teamDev = employeeDao.getByRoleNameOfTeam("developer", Integer.parseInt(teamId));
		Map<Integer, Employee> teamDevMap = new HashMap<>();
		for (Employee empl : teamDev) {
			teamDevMap.put(empl.getEmployeeId(), empl);
		}

		ServletActionContext.getRequest().setAttribute("teamDev", teamDevMap);
		ServletActionContext.getRequest().setAttribute("developers", developers);
		ServletActionContext.getRequest().setAttribute("leaders", leaders);
		ServletActionContext.getRequest().setAttribute("roles", Utilities.getRoles());
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		ServletActionContext.getRequest().setAttribute("role", role);

		Team team = teamDao.getById(Integer.parseInt(teamId));

		ServletActionContext.getRequest().setAttribute("team", team);
		ServletActionContext.getRequest().setAttribute("form", "update");

		return SUCCESS;
	}

	public String save() throws Exception {

		employeeDao = EmployeeDaoImpl.getInstance();
		teamDao = TeamDaoImpl.getInstance();

		Team existingTeam = teamDao.getById(Integer.parseInt(teamId));
		ServletActionContext.getRequest().setAttribute("teamId", teamId);

		String[] empIds = developer;
		int leaderId = leader;

		existingTeam.setTeamName(teamName);
		if (leaderId != existingTeam.getLeaderId()) {
			Employee oldEmp = employeeDao.getById(existingTeam.getLeaderId());
			oldEmp.setTeamId(null);
			employeeDao.update(oldEmp);
		}

		existingTeam.setLeaderId(leaderId);
		teamDao.update(existingTeam);

		List<Employee> oldEmp = employeeDao.getByRoleNameOfTeam("developer", Integer.parseInt(teamId));

		if (empIds != null) {
			for (String empId : empIds) {
				Employee existingEmployee = employeeDao.getById(Integer.parseInt(empId));

				existingEmployee.setTeamId(Integer.parseInt(teamId));
				employeeDao.update(existingEmployee);// update team's employees

			}
			for (Employee oldEmployee : oldEmp) {
				if (!Arrays.asList(empIds).contains(String.valueOf(oldEmployee.getEmployeeId()))) {// check
																									// if old employees
																									// selected
					oldEmployee.setTeamId(null);
					employeeDao.update(oldEmployee);
				}

			}
		}
		execute();
		Employee existingEmployee = employeeDao.getById(leaderId);
		existingEmployee.setTeamId(Integer.parseInt(teamId));
		employeeDao.update(existingEmployee);
		ServletActionContext.getRequest().setAttribute("msg", "team updated successfully."); // Add success message
		ServletActionContext.getRequest().setAttribute("teams", Utilities.getTeams());
		return SUCCESS;
	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String[] getDeveloper() {
		return developer;
	}

	public void setDeveloper(String[] developer) {
		this.developer = developer;
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}
}
