package actions;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.*;

public class DeleteTeam extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
 	private TeamDao teamDao;
	private int teamId;

	// Constructor
	public DeleteTeam() {
		super();
		teamDao = TeamDaoImpl.getInstance();
	}

	// Getter and Setter for teamId
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Override
	public String execute() throws Exception {
		try {
			SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();

			teamDao.delete(teamId);
			session.put("teams", teamDao.getAll());
			ServletActionContext.getRequest().setAttribute("msg", "deleted successfully");

			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
