package actions;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import model.*;

public class DeleteTask extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
	private int taskId;

	// Constructor
	public DeleteTask() {
		super();
		taskDao = TaskDaoImpl.getInstance();
	}

	// Getter and Setter for taskId
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public String execute() throws Exception {
		try {
			taskDao.delete(taskId);
			addActionMessage("Task\"+taskId+\" deleted successfully.");
			String message = "Task" + taskId + " deleted successfully.";
			ServletActionContext.getRequest().setAttribute("message", message);
			ServletActionContext.getRequest().setAttribute("msg", "deleted successfully");

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
