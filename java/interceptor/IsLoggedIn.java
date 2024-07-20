package interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import actions.Utilities;
import beans.Employee;

public class IsLoggedIn extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
	try {
		 SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
	        HttpServletRequest httpRequest = ServletActionContext.getRequest();
	        HttpServletResponse response = ServletActionContext.getResponse();


		
	        if(response!=null){
	            response.setHeader("Cache-control", "no-cache, no-store");
	            response.setHeader("Pragme", "no-cache");
	            response.setHeader("Expires", "-1");

	        }
			if (session == null || session.get("user") == null) {
		       session.invalidate();

		        // Remove specific request attributes
		        httpRequest.removeAttribute("msg");
				ServletActionContext.getRequest().setAttribute("msg", "  please login first");
				return "login"; // Return the result name corresponding to the login action
			}

			Employee loggedInUser = (Employee) session.get("user");

			if (Objects.isNull(loggedInUser)) {
 			       session.invalidate();

				ServletActionContext.getRequest().setAttribute("msg", "  please login first");
				return "login"; // Return the result name corresponding to the login action
			}

			// Proceed with the action execution
			Employee emp =(Employee) session.get("user");
			ServletActionContext.getRequest().setAttribute("canApproveTasks", Utilities.canApproveTasks(emp));
			ServletActionContext.getRequest().setAttribute("canViewTeams", Utilities.canViewTeams(emp));
			String role = Utilities.getRole(emp);
			ServletActionContext.getRequest().setAttribute("role", role);

			return invocation.invoke();
		} catch (Exception e) {
			// Handle the error exception
			e.printStackTrace(); // Log the exception
			ServletActionContext.getRequest().setAttribute("msg", "  unauthorized access");
			return "error"; // Return the result name corresponding to the login action
		}
	}
}
