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
import beans.Task;
import model.EmployeeTaskDao;
import model.TaskDao;

public class AssignedEmployees extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
	private TaskDao taskDao;
	private EmployeeTaskDao employeeTaskDao;
	private String taskId;
	private boolean isLeader = (boolean) session.get("isLeader");
	private boolean isManager = (boolean) session.get("isManager");

	@Override
	public String execute() throws Exception {
		if (isManager == true || isLeader == true) {

			try {
				int taskIdInt = Integer.parseInt(taskId);
				List<Employee> empIds = employeeTaskDao.getEmployeesForTask(taskIdInt);
				List<Employee> employees = new ArrayList<>();

				for (Employee empId : empIds) {
					Employee employee = (empId); // employee details by ID

					if (employee != null) {
						employees.add(employee);
					}
				}
				Task tasks = taskDao.getById(taskIdInt);

				ServletActionContext.getRequest().setAttribute("emp", employees);
				ServletActionContext.getRequest().setAttribute("task", tasks);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return INPUT;
			}
		}
		return INPUT;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;

	}

}