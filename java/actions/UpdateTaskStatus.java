package actions;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.Task;
import model.TaskDao;
import model.TaskDaoImpl;

public class UpdateTaskStatus extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionMap<String, Object> session;
	private TaskDao taskDao;
	private String taskId;
	private String statusId;
	private int pending;

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	@Override
	public String execute() throws Exception {
		try {
			addActionMessage("Task status updated successfully"); // Add this line to set the success message

			taskDao = TaskDaoImpl.getInstance();

			int taskIdInt = Integer.parseInt(taskId);
			int statusIdInt = Integer.parseInt(statusId);
			System.out.println(taskId);
			System.out.println(statusId);
			// Update the task's status
			Task task = taskDao.getById(taskIdInt);
			System.out.println(task.getTaskName());
			task.setStatusId(statusIdInt);
			taskDao.update(task);
			ServletActionContext.getRequest().setAttribute("msg","task status updated successfully."); // Add success message

			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();

			return INPUT;

		}
	}

	// Implementing the SessionAware interface method
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public SessionMap<String, Object> getSession() {
		return session;
	}

	public void setSession(SessionMap<String, Object> session) {
		this.session = session;
	}
}
