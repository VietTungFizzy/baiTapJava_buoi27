package crm09.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Project;
import crm09.entity.Task;
import crm09.entity.User;
import crm09.services.ProjectServices;
import crm09.services.TaskServices;
import crm09.services.UserServices;

@WebServlet(name="TaskController ", urlPatterns = {"/tasks", "/task-add", "/task-change", "/task-delete"})
public class TaskController extends HttpServlet {
	private TaskServices taskServices = new TaskServices();
	private ProjectServices projectServices = new ProjectServices();
	private UserServices userServices = new UserServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
				
				req.setAttribute("listProjects", listProjects);
				req.setAttribute("listUsers", listUsers);
				
				req.getRequestDispatcher("task-add.jsp").forward(req, resp);
				break;
			}
			case "/task-change": {
				int id = Integer.parseInt(req.getParameter("id"));
				Task task = taskServices.getTask(id);
				List<Project> listProjects = projectServices.getAllProjects();
				List<User> listUsers = userServices.getAllUser();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				req.setAttribute("formatter", sdf);
				req.setAttribute("listProjects", listProjects);
				req.setAttribute("listUsers", listUsers);
				req.setAttribute("task", task);
				
				req.getRequestDispatcher("task-update.jsp").forward(req, resp);
				break;
			}
			case "/task-delete":
				int id = Integer.parseInt(req.getParameter("id"));
				boolean isSuccess = taskServices.deleteTask(id);
				
				resp.sendRedirect("/crm09/tasks");
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isSuccess = false;
		String servletPath = req.getServletPath();
		int projectId = Integer.parseInt(req.getParameter("project"));
		int userId = Integer.parseInt(req.getParameter("user"));
		String name = req.getParameter("nameTask");
		String startDate = req.getParameter("startTask");
		String endDate = req.getParameter("endTask");
		
		switch (servletPath) {
		case "/task-add": {
			isSuccess = taskServices.insertTask(name, startDate, endDate, "Chưa hoàn thành", projectId, userId);
			break;
		}
		case "/task-change": {
			int id = Integer.parseInt(req.getParameter("id"));
			String status = taskServices.getTask(id).getStatus();
			isSuccess = taskServices.updateTask(id, name, startDate, endDate, status, projectId, userId);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
		
		resp.sendRedirect("/crm09/tasks");
	}
}
