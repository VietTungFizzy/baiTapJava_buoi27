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
import crm09.services.ProjectServices;

@WebServlet(name = "ProjectController", urlPatterns = {"/projects", "/project-add", "/project-change", "/project-delete"})
public class ProjectController extends HttpServlet {
	private ProjectServices projectServices = new ProjectServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		switch (servletPath) {
		case "/projects": {
			List<Project> listProject = projectServices.getAllProjects();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			req.setAttribute("formatter", sdf);
			req.setAttribute("listProject", listProject);
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
			break;
		}
		case "/project-add": {
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			break;
		}
		case "/project-change": {
			int id = Integer.parseInt(req.getParameter("id"));
			Project project = projectServices.getProject(id);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			req.setAttribute("formatter", sdf);
			req.setAttribute("project", project);
			req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
			break;
		}
		case "/project-delete": {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = projectServices.deleteProject(id);
			
			resp.sendRedirect("/crm09/projects");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isSuccess = false;
		String servletPath = req.getServletPath();
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		switch (servletPath) {
		case "/project-add": {
			isSuccess = projectServices.insertProject(name, startDate, endDate);
			break;
		}
		case "/project-change": {
			int projectId = Integer.parseInt(req.getParameter("id"));
			isSuccess = projectServices.updateProject(projectId, name, startDate, endDate);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + servletPath);
		}
		
		resp.sendRedirect("/crm09/projects");
	}
}
