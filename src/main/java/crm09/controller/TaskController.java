package crm09.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Project;
import crm09.entity.Status;
import crm09.entity.Task;
import crm09.entity.User;
import crm09.services.ProjectServices;
import crm09.services.StatusServices;
import crm09.services.TaskServices;
import crm09.services.UserServices;

@WebServlet(name="TaskController ", urlPatterns = {"/tasks", "/task-add", "/task-change", "/task-delete", "/profile-edit"})
public class TaskController extends HttpServlet {
	private TaskServices taskServices = new TaskServices();
	private ProjectServices projectServices = new ProjectServices();
	private UserServices userServices = new UserServices();
	private StatusServices statusServices = new StatusServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		String servletPath = req.getServletPath();
		switch(servletPath) {
			case "/tasks": {
				List<Task> listTasks = taskServices.getAllTasks();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				req.setAttribute("formatter", sdf);
				req.setAttribute("listTasks", listTasks);
				
				req.getRequestDispatcher("task.jsp").forward(req, resp);
				break;
			}
			case "/task-add": {
				List<Project> listProjects = projectServices.getAllProjects();
				List<User> listUsers = userServices.getAllUser();
				List<Status> listStatus = statusServices.getAllStatus();
				
				req.setAttribute("listProjects", listProjects);
				req.setAttribute("listUsers", listUsers);
				req.setAttribute("listStatus", listStatus);
				
				req.getRequestDispatcher("task-add.jsp").forward(req, resp);
				break;
			}
			case "/task-change": {
				int id = Integer.parseInt(req.getParameter("id"));
				Task task = taskServices.getTask(id);
				List<Project> listProjects = projectServices.getAllProjects();
				List<User> listUsers = userServices.getAllUser();
				List<Status> listStatus = statusServices.getAllStatus();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				req.setAttribute("formatter", sdf);
				req.setAttribute("listProjects", listProjects);
				req.setAttribute("listUsers", listUsers);
				req.setAttribute("listStatus", listStatus);
				req.setAttribute("task", task);
				
				req.getRequestDispatcher("task-update.jsp").forward(req, resp);
				break;
			}
			case "/task-delete": {
				int id = Integer.parseInt(req.getParameter("id"));
				boolean isSuccess = taskServices.deleteTask(id);
				
				resp.sendRedirect("/crm09/tasks");
				break;
			}
			case "/profile-edit": {
				int id = Integer.parseInt(req.getParameter("id"));
				Task task = taskServices.getTask(id);
				List<Status> listStatus = statusServices.getAllStatus();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				req.setAttribute("formatter", sdf);
				req.setAttribute("listStatus", listStatus);
				req.setAttribute("task", task);
				
				req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		boolean isSuccess = false;
		String servletPath = req.getServletPath();
		int projectId = Integer.parseInt(req.getParameter("project"));
		int userId = Integer.parseInt(req.getParameter("user"));
		String name = req.getParameter("nameTask");
		String startDate = req.getParameter("startTask");
		String endDate = req.getParameter("endTask");
		String redirectUrl = "/crm09/tasks";

		switch (servletPath) {
		case "/task-add": {
			isSuccess = taskServices.insertTask(name, startDate, endDate, 1, projectId, userId);
			break;
		}
		case "/task-change": {
			int id = Integer.parseInt(req.getParameter("id"));
			int status = taskServices.getTask(id).getStatus().getId();
			isSuccess = taskServices.updateTask(id, name, startDate, endDate, status, projectId, userId);
			break;
		}
		case "/profile-edit": {
			int id = Integer.parseInt(req.getParameter("id"));
			int status = Integer.parseInt(req.getParameter("status"));
			isSuccess = taskServices.updateTask(id, name, startDate, endDate, status, projectId, userId);
			redirectUrl = "/crm09/profile?id=" + currentUserId;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
		
		resp.sendRedirect(redirectUrl);
	}
}
