package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Role;
import crm09.services.UserServices;

@WebServlet(name="RoleController", urlPatterns = {"/roles", "/role-add", "/role-change", "/role-delete"})
public class RoleController extends HttpServlet {
	private UserServices userService = new UserServices();
	
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
		if(servletPath.equals("/roles")) {
			List<Role> roles = userService.getAllRole();
			req.setAttribute("roles", roles);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		} else if(servletPath.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		} else if(servletPath.equals("/role-change")) { 
			int id = Integer.parseInt(req.getParameter("id"));
			Role role = userService.getRole(id);
			
			req.setAttribute("role", role);
			
			req.getRequestDispatcher("role-update.jsp").forward(req, resp);
		} else if(servletPath.equals("/role-delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			
			boolean isSuccess = userService.deleteRole(id);
			resp.sendRedirect("/crm09/roles");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		
		boolean isSuccess = false;
		
		String servletPath = req.getServletPath();
		
		if(servletPath.equals("/role-add")) {
			isSuccess = userService.insertRole(name, description);
		} else if(servletPath.equals("/role-change")) {
			int id = Integer.parseInt(req.getParameter("id"));
			isSuccess = userService.updateRole(id, name, description);
		}
		
		resp.sendRedirect("/crm09/roles");
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect("/crm09/roles");
	}
}
