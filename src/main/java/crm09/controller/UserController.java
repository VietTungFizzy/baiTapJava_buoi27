package crm09.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Role;
import crm09.entity.Status;
import crm09.entity.Task;
import crm09.entity.User;
import crm09.services.StatusServices;
import crm09.services.TaskServices;
import crm09.services.UserServices;
import crm09.utils.MD5Helper;

@WebServlet(name="UserController", urlPatterns = {"/user-add", "/user", "/user-change", "/user-delete", "/user-details", "/profile"})
public class UserController extends HttpServlet {
	private UserServices userServices = new UserServices();
	private TaskServices taskServices = new TaskServices();
	private StatusServices statusServices = new StatusServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		
		int currentUserId = -1;
		Cookie[] cookies = req.getCookies();
		if(req.getCookies() != null) {
			cookies = req.getCookies();
			
			for(Cookie cookie: cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				
				if(name.equals("id")) {
					currentUserId = Integer.parseInt(value);
				}
			}
		}
		req.setAttribute("currentUserId", currentUserId);
		
		if(servletPath.equals("/user-add")) {
			List<Role> listRole = userServices.getAllRole();
			req.setAttribute("listRoles", listRole);
			
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		} else if(servletPath.equals("/user")) {
			List<User> listUser = userServices.getAllUser();
			req.setAttribute("listUsers", listUser);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		} else if(servletPath.equals("/user-change")) {
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userServices.getUser(id);
			
			List<Role> listRole = userServices.getAllRole();
			req.setAttribute("listRoles", listRole);
			
			req.setAttribute("user", user);
			req.getRequestDispatcher("user-update.jsp").forward(req, resp);
		} else if(servletPath.equals("/user-delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			
			boolean isSuccess = userServices.deleteUser(id);
			resp.sendRedirect("/crm09/user");
		} else if(servletPath.equals("/user-details")) {
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userServices.getUser(id);
			Map<Integer, List<Task>> tasksGroupByStatus = taskServices.groupTasksByStatus(id);
			List<Status> listStatus = statusServices.getAllStatus();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			int totalTasks = taskServices.totalTasks(tasksGroupByStatus);

			req.setAttribute("formatter", sdf);
			req.setAttribute("user", user);
			req.setAttribute("tasksGroupByStatus", tasksGroupByStatus);
			req.setAttribute("listStatus", listStatus);
			req.setAttribute("totalTasks", totalTasks);
			
			req.getRequestDispatcher("user-details.jsp").forward(req, resp);
		} else if(servletPath.equals("/profile")) {
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userServices.getUser(id);
			List<Task> listTask = taskServices.getAllByUser(id);
			Map<Integer, List<Task>> tasksGroupByStatus = taskServices.groupTasksByStatus(id);
			List<Status> listStatus = statusServices.getAllStatus();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			int totalTasks = taskServices.totalTasks(tasksGroupByStatus);

			req.setAttribute("formatter", sdf);
			req.setAttribute("user", user);
			req.setAttribute("tasksGroupByStatus", tasksGroupByStatus);
			req.setAttribute("listStatus", listStatus);
			req.setAttribute("totalTasks", listTask.size());
			req.setAttribute("listTask", listTask);
			
			req.getRequestDispatcher("profile.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullName = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		int roleId = Integer.parseInt(req.getParameter("roleId"));
		boolean isSuccess = false;
		
		String servletPath = req.getServletPath();
		
		if(servletPath.equals("/user-add")) {
			isSuccess = userServices.insertUser(email, password, roleId, fullName, phone);
		} else if(servletPath.equals("/user-change")) {
			int id = Integer.parseInt(req.getParameter("id"));
			if(password.isBlank()) {
				User user = userServices.getUser(id);
				password = user.getPassword();
			} else {
				password = MD5Helper.getMd5(password);
			}
			
			isSuccess = userServices.updateUser(id, email, password, roleId, fullName, phone);
		}
		
		resp.sendRedirect("/crm09/user");
	}
}
