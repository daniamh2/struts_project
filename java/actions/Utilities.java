package actions;

import java.util.List;

import beans.Employee;
import beans.Role;
import beans.Task;
import beans.TaskStatus;
import beans.Team;
import model.EmployeeDao;
import model.EmployeeDaoImpl;
import model.EmployeeTaskDao;
import model.EmployeeTaskDaoImpl;
import model.RoleDao;
import model.RoleDaoImpl;
import model.TaskDao;
import model.TaskDaoImpl;
import model.TaskStatusDao;
import model.TaskStatusDaoImpl;
import model.TeamDao;
import model.TeamDaoImpl;

/**
 * This Action class handles user profile-related functionality.
 *
 * @version 1.0 10 Mar 2024
 */
public class Utilities  {

	/**
	 * 
	 */
	/** DAO instance */
	private static TeamDao teamDao;
	private static EmployeeTaskDao employeeTaskDao;
	private static EmployeeDao employeeDao;
	private static RoleDao roleDao;
	private static TaskStatusDao taskStatusDao;
	private static TaskDao taskDao;

	public Utilities() {
		super();
	}

	public static List<Integer> getTasks(Employee employee) {
		employeeTaskDao = EmployeeTaskDaoImpl.getInstance();

		List<Integer> tasks = employeeTaskDao.getTasksForEmployee(employee.getEmployeeId());
		return tasks;
	}

	public static List<Task> getTasks() {
		taskDao = TaskDaoImpl.getInstance();

		List<Task> tasks = taskDao.getAll();
		return tasks;
	}
	
	public static List<Employee> getEmployee() {
		employeeDao = EmployeeDaoImpl.getInstance();

		List<Employee> employees = employeeDao.getAll();
		return employees;
	}	
	
	public static Employee getEmployee(int id) {
		employeeDao = EmployeeDaoImpl.getInstance();

		Employee employee = employeeDao.getById(id);
		return employee;
	}

	public static List<Role> getRoles() {
		roleDao = RoleDaoImpl.getInstance();

		List<Role> roles = roleDao.getAll();
		return roles;
	}

	public static List<Team> getTeams() {
		teamDao = TeamDaoImpl.getInstance();

		List<Team> teams = teamDao.getAll();
		return teams;
	}

	public static List<TaskStatus> getStatuses() {
		taskStatusDao = TaskStatusDaoImpl.getInstance();

		List<TaskStatus> status = taskStatusDao.getAll();
		return status;
	}

	public static String getRole(Employee employee) {
		roleDao = RoleDaoImpl.getInstance();

		String role = roleDao.role(employee.getRoleId());

		return role;
		
	}	public static String getRolei(int employee) {
		roleDao = RoleDaoImpl.getInstance();

		String role = roleDao.role(employee);
	
		return role;
		
	}

	public static boolean canApproveTasks(Employee employee) {
		roleDao = RoleDaoImpl.getInstance();

		boolean canApproveTasks = (employee.getRoleId() == roleDao.getByNmae("leader")
				|| employee.getRoleId() == roleDao.getByNmae("manager")); // Check if the user is a developer or manager
		return canApproveTasks; // return role;
	}

	public static boolean canViewTeams(Employee employee) {		
		roleDao = RoleDaoImpl.getInstance();

		boolean canViewTeams = (employee.getRoleId() == roleDao.getByNmae("manager")); // Check if the user is a
		return canViewTeams;
	}
}
