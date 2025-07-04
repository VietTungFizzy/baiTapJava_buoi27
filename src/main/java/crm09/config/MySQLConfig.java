package crm09.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConfig {
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			String url = "jdbc:mysql://localhost:3307/crmapp";
			String userName = "root";
			String password = "admin123";
			
			// Khai báo driver sử dụng
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, userName, password);
		} catch(Exception e) {
			System.out.println("Lỗi kết nối CSDL " + e.getMessage());
		}
		
		return connection;
	}
}
