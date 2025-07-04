package crm09.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.config.MySQLConfig;
import crm09.entity.*;
import crm09.utils.MD5Helper;

@WebServlet(name="loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies;
		String username = "";
		String password = "";
		
		if(req.getCookies() != null) {
			cookies = req.getCookies();
			
			for(Cookie cookie: cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				
				if(name.equals("sUsername")) {
					username = value;
				}
				
				if(name.equals("sPassword")) {
					password = value;
				}
			}
		}
		
		req.setAttribute("email", username);
		req.setAttribute("password", password);
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	/**
	 * Bước 1: Phải suy nghĩ được logic (nghiệp vụ) của tính năng là đang muốn làm gì
	 * Bước 2: Xác định câu truy vấn để đáp ứng cho nghiệp vụ đó.
	 * Bước 3: Tiến hành code theo logic nghiệp vụ theo yêu cầu
	 */
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Bước 1: Lấy tham số và password bên UI login.jsp khi người dùng click button đăng nhập
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		// Bước 2: Chuẩn bị câu truy vấn
		String query = "SELECT * \n"
				+ "FROM users u \n"
				+ "JOIN roles r ON u.id_role = r.id \n"
				+ "WHERE u.email = ? AND u.password = ?";
		
		// Bước 3: Mở kết nối CSDL
		Connection connection = MySQLConfig.getConnection();
		
		// Bước 4: Truyền câu query vào connection mới vừa mở để truyền xuống CSDL
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			// Truyền tham số vào dấu ? ở câu truy vấn
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, MD5Helper.getMd5(password));
			/*
			 * executeQuery : Khi câu truy vấn là SELECT
			 * executeUpdate : Không phải là câu SELECT
			 */
			// Thực thi câu truy vấn
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<User> listUsers = new ArrayList<User>();
			
			// Map từng dòng dữ liệu của resultSet thành giá trị của List
			while(resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				
				user.setRoles(role);
				
				listUsers.add(user);
			}
			
			if(listUsers.size() > 0) {
				// Tạo Cookie
				Cookie cookie = new Cookie("sUsername", email);
				cookie.setMaxAge(5 * 60); // Seconds
				
				Cookie cookiePassword = new Cookie("sPassword", password);
				
				String roleName = listUsers.get(0).getRoles().getName();
				
				Cookie cookieRole = new Cookie("role", roleName);
				
				resp.addCookie(cookieRole);
				resp.addCookie(cookie);
				resp.addCookie(cookiePassword);
				
				System.out.println("Đăng nhập thành công");
			} else {
				System.out.println("Đăng nhập thất bại");
			}
		} catch(Exception e) {
			System.out.println("Lỗi truyền query xuống database " + e.getMessage());
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
}
