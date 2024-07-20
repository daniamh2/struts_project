package actions;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import model.EmployeeDao;
import model.EmployeeDaoImpl;

public class DeleteEmployee extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteEmployee() {
		super();
		employeeDao = EmployeeDaoImpl.getInstance();
	}

	private EmployeeDao employeeDao;
	private int employeeId;

	// Getter and Setter for employeeId

	@Override
	public String execute() throws Exception {
		try {
			employeeDao.delete(employeeId);
			ServletActionContext.getRequest().setAttribute("msg", "deleted successfully");

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
