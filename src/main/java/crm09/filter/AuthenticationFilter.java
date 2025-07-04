package crm09.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName= "authentication", urlPatterns= {"/user-add"})
public class AuthenticationFilter extends HttpFilter {
	/**
	 * Bài tập về nhà:
	 * 	- /user-add: Phải có quyền ADMIN mới truy cập được 
	 * 	- /user: Chỉ cần đăng nhập là truy cập được
	 * 
	 * 	- Phải xài filter
	 */
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// Code chạy ở đây
		
		Cookie[] cookies = req.getCookies();
		String role = "";
		
		for(Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			if(name.equals("role")) {
				role = value;
			}
		}
		
		if(role.equals("ADMIN")) {
			// Cho phép đi tiếp
			chain.doFilter(req, res);
		} else {
			res.sendRedirect("login");
		}
	}
}
