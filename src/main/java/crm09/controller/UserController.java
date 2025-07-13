package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Role;
import crm09.entity.User;
import crm09.services.UserServices;
import crm09.utils.MD5Helper;

@WebServlet(name="UserController", urlPatterns = {"/user-add", "/user", "/user-change", "/user-delete"})
public class UserController extends HttpServlet {
	private UserServices userServices = new UserServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
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
