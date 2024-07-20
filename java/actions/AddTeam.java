package actions;

import java.sql.SQLException;
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

public class AddTeam extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8728276568941155700L;
	private SessionMap<String, Object> session;
	private EmployeeDao employeeDao;
	private TeamDao teamDao;
	private String[] developer;
	private String teamName;
	private int leader;
	List<Employee> leaders;
	boolean isManager;

	public List<Employee> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<Employee> leaders) {
		this.leaders = leaders;
	}

	public AddTeam() {
		super();
		employeeDao = EmployeeDaoImpl.getInstance();
		teamDao = TeamDaoImpl.getInstance();
	}

	public String execute() throws Exception {
		try {
			session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
			Employee employee = (Employee) session.get("user");
			String role = Utilities.getRole(employee);
			if (role.equals("manager")) {
				showAddTeamForm();
				int leaderIdInt = leader;
				// Create a new team
				Team newTeam = new Team();
				newTeam.setTeamName(teamName);
				newTeam.setLeaderId(leaderIdInt);

				int newteamId = teamDao.add(newTeam);
				if (developer != null) {
					for (String empId : developer) {
						Employee selectedEmployee = employeeDao.getById(Integer.parseInt(empId)); // retrieve selected
																									// employees
						// of this team

						selectedEmployee.setTeamId(newteamId);
						employeeDao.update(selectedEmployee);
					}
				}

				Employee selectedEmployee = employeeDao.getById(leaderIdInt); // retrieve leader id

				selectedEmployee.setTeamId(newteamId);
				employeeDao.update(selectedEmployee);
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("teams", teamDao.getAll());
				ServletActionContext.getRequest().setAttribute("teams", teamDao.getAll());
				ServletActionContext.getRequest().setAttribute("msg", "added successfully");

				return SUCCESS;
			}
			return ERROR;
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}

	public String[] getDeveloper() {
		return developer;
	}

	public void setDeveloper(String[] developer) {
		this.developer = developer;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}

	public String showAddTeamForm() throws SQLException, Exception {
		leaders = employeeDao.getByRoleNames("leader");
		List<Employee> developers = employeeDao.getByRoleNames("developer");

		ServletActionContext.getRequest().setAttribute("developers", developers);
		ServletActionContext.getRequest().setAttribute("leaders", leaders);

		return SUCCESS;
	}

	// Helper methods

	@Override
	public void setSession(Map<String, Object> arg0) {

	}
}