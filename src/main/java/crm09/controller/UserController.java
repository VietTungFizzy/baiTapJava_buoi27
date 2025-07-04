package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Role;
import crm09.services.UserServices;

@WebServlet(name="UserController", urlPatterns = {"/user-add", "/user"})
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
			req.getRequestDispatcher("user-table.html").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullName = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		int roleId = Integer.parseInt(req.getParameter("roleId"));
		System.out.println("Kiem tra " + roleId);
		
		boolean isSuccess = userServices.insertUser(email, password, roleId, fullName, phone);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
}
